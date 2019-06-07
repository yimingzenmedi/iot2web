/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;

/**
 *
 * @author 64661
 */
public class ReadVisitingInformation extends HttpServlet {

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
            JSONObject json = new JSONObject("{}");
            try{            
                // delete guest here
                Statement statement = (new JDBCConnector()).newConnector();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM log;");
//                String result = "";
                while(resultSet.next()){
                    String id = resultSet.getString("id");
                    String name = resultSet.getString("name");
                    String time = resultSet.getString("time");
                    json.put(id, time+"\n"+name);
                }
            }catch(SQLException e){
            }
            

//            json.put("3", "18:39 2019/6/6\nuser1");
//            json.put("4", "18:39 2019/6/7\nuser1");
//            json.put("5", "18:39 2019/7/6\nuser3");
//            json.put("2", "01:39 2019/6/6\nuser1");
//            json.put("1", "18:39 2009/6/6\nuser4");
//           
           
            out.print(json);
            
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
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
