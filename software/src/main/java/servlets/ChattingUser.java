/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import com.google.gson.Gson;
import database.tables.EditMessageTable;
import database.tables.EditRandevouzTable;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mainClasses.Message;
import mainClasses.Randevouz;

/**
 *
 * @author ntigo
 */
public class ChattingUser extends HttpServlet {

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
        //
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

        int doctor_id = Integer.parseInt(request.getParameter("doctor_id"));
        int user_id = Integer.parseInt(request.getParameter("user_id"));

        EditRandevouzTable ert = new EditRandevouzTable();
        EditMessageTable emt = new EditMessageTable();
        ArrayList<Message> messages = new ArrayList<>();
        try {
            Randevouz r = ert.startConnectionDoctor(doctor_id);
            if (r != null) {
                /* Connect with user and show all previous messages */
                System.out.println("Connected with Doctor: " + r.getDoctor_id());

                messages = emt.databaseToMessagesList(doctor_id, user_id);
                if (messages!=null) {
                    Gson gson = new Gson();
                    response.getWriter().write(gson.toJson(messages));
                    //System.out.println(gson.toJson(messages));
                }
                response.setStatus(200);
            } else {
                System.out.println("Couldn't find Doctor");
                response.setStatus(400);
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(ChattingUser.class.getName()).log(Level.SEVERE, null, ex);
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
        int doctor_id = Integer.parseInt(request.getParameter("doctor_id"));
        int user_id = Integer.parseInt(request.getParameter("user_id"));
        String message = request.getParameter("message");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        EditMessageTable emt = new EditMessageTable();

        Message msg = new Message();
        msg.setDoctor_id(doctor_id);
        msg.setUser_id(user_id);
        msg.setMessage(message);
        msg.setSender("user");
        try {
            msg.setDate_time(sdf.format(timestamp));
            emt.createNewMessage(msg);
            response.setStatus(200);
        } catch (ClassNotFoundException ex) {
            response.setStatus(400);
            Logger.getLogger(ChattingUser.class.getName()).log(Level.SEVERE, null, ex);
        }
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
