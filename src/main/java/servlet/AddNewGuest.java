/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import faceRcognation.IndexFaces;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import java.sql.*;
import java.util.ArrayList;


/**
 *
 * @author 64661
 */
public class AddNewGuest extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
//        make folder path
        String uploadPath = "upload";
        File uploadDir = new File(uploadPath);
        if(!uploadDir.exists()){
            uploadDir.mkdir();
        }
        String filePath = "";
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            if(!ServletFileUpload.isMultipartContent(request)){
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            DiskFileItemFactory factory = new DiskFileItemFactory();
            factory.setSizeThreshold(1024*1024*20);
            factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
            ServletFileUpload upload = new ServletFileUpload(factory);
            upload.setFileSizeMax(1024*1024*40);
            upload.setSizeMax(1024*1024*45);
            upload.setHeaderEncoding("UTF-8"); 
            
//            String uploadPath = "upload";
//            File uploadDir = new File(uploadPath);
//            if(!uploadDir.exists()){
//                uploadDir.mkdir();
//            }
            
            List<FileItem> formItems = upload.parseRequest(request);
            String name = "";
            if(formItems != null && formItems.size()>0){
                for(FileItem item : formItems){
                    if(!item.isFormField()){
                        String fileName = new File(item.getName()).getName();
                        filePath = uploadPath + File.separator + fileName;
                        System.out.println(filePath);
                        File storeFile = new File(filePath);
                        item.write(storeFile);
                                                
//                        out.print("good");
                    } else {
                        name = item.getString();
                        System.out.println("item: "+name);
                    }
                }
                
                IndexFaces indf = new IndexFaces();
                ArrayList<String> faceIds = indf.indexFaces("testColl", filePath);
                
//              connect db
                Statement statement = (new JDBCConnector()).newConnector();
                for(String id : faceIds){
                    if(!name.equals("")){
                        if(!statement.execute("SELECT name FROM user WHERE pid='"+id+"';")){
                            int result = statement.executeUpdate("INSERT INTO user (name, pid) VALUES ('"+name+"', '"+id+"');");
                            if(result > 0){
                                out.print("good");
                            }else{
                                out.print("bad");
                            }
                        } else {
                            out.print("This person has existed.");
                        }
                    }
                }
            }

//            out.println("good");
            
        }catch(Exception e){}finally{
//            File filePathDel = new File(filePath);
//            if(!filePath.equals("") && filePathDel.exists()){
//                if(filePathDel.delete()){                
//                    System.out.println("file: " + filePath + " deleted.");
//                }else{
//                    System.out.println("file: " + filePath + " failed.");
//                };
//            }
        }
    }
    


    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
