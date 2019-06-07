/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import faceRcognation.SearchFacesByImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author 64661
 */
public class Recognize extends HttpServlet {

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
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            String img = request.getParameter("img");
            System.out.println("img: "+img);
            String filePath = new File("").getAbsolutePath()+"/recognize";
            File fileDir = new File(filePath);
            if(!fileDir.exists() && !fileDir.isDirectory()){
                fileDir.mkdir();
            }
            String fileName = "newPic.jpg";

            BufferedOutputStream bos = null;
            java.io.FileOutputStream fos = null;
            
            try{
                byte[] bytes = Base64.getDecoder().decode(img);
                File file =new File(filePath+"/"+fileName);
                fos = new java.io.FileOutputStream(file);
                bos = new BufferedOutputStream(fos);
                bos.write(bytes);
                System.out.println("Save at: "+filePath+"/"+fileName);
//                out.print("ok");
            } catch(Exception e){}finally {
                if (bos != null) {
                    try {
                        bos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            
            SearchFacesByImage sfbi = new SearchFacesByImage();
            ArrayList<String> faceIds = sfbi.searchFacesByImage("testColl", filePath+"/"+fileName);
            int strangerNum = 0;
            String voice = "";
            for(String faceId : faceIds) {
                if(faceId.equals("")){
                    strangerNum ++ ;
                } else {
                    voice = voice + faceId + ", ";
                }
            }
            if(strangerNum != 0){
                if(!voice.equals("")){
                    voice += " and";
                }
                
                voice = voice + strangerNum;

                if(strangerNum == 1){
                    voice += " stranger";
                } else {
                    voice += " strangers";
                }
                
            }
            if(faceIds.isEmpty()) {
                voice = "No body is waiting";
            } else if(faceIds.size() == 1){
                voice = voice + " is waiting outside.";
            } else {
                voice = voice + " are waiting outside.";
            }
            
            boolean ok = true;
            try{            
                // delete guest here
                SimpleDateFormat df = new SimpleDateFormat(" HH:mm:ss MM-dd-yyyy");
                String time = df.format(new Date());
                
                Statement statement = (Statement) new JDBCConnector();
                if(!faceIds.isEmpty()){
                    for(String id : faceIds){
                        ResultSet resultSet = statement.executeQuery("select name from user where pid='"+id+"'");
                        while(resultSet.next()){
                            String name = resultSet.getString("name");
                            boolean result = statement.execute("INSERT INTO log (name, time) VALUES ('"+name+"', '"+time+"');");
                            ok = result;
                        }
                    }                
                }
            }catch(SQLException e){
            }
            
            if(ok){
                try {
                    String voiceStream = textToAudio.PollyDemo.runPolly(voice);
                    out.print(voiceStream);
                } catch (Exception e) {
                }
            }
            
            System.out.println(voice);
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