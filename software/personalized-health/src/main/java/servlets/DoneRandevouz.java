/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import database.tables.EditBloodTestTable;
import database.tables.EditRandevouzTable;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mainClasses.BloodTest;
import mainClasses.Randevouz;

/**
 *
 * @author ntigo
 */
public class DoneRandevouz extends HttpServlet {

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
        int id = Integer.parseInt(request.getParameter("randevouz_id"));
        EditRandevouzTable ert = new EditRandevouzTable();
        EditBloodTestTable ebtt = new EditBloodTestTable();
        try {
            Randevouz randevou = ert.databaseToRandevouz(id);
            if (randevou != null) {
                if (!randevou.getStatus().equals("done")) {
                    ert.statusRandevouz(id, "done");
                    String amka = ert.getAMKAfromRandevouzID(id);
                    System.out.println("AMKA:" + amka);

                    BloodTest bt = new BloodTest();
                    bt = ebtt.databaseToBloodTest(amka);
                    if (bt != null) {
                        String json = ebtt.bloodTestToJSON(bt);
                        System.out.println("BLOODTEST: " + json);
                        response.getWriter().write(json);
                        response.setStatus(200);
                    } else {
                        response.getWriter().write("User with Amka: " + amka + "does not have a Bloodtest");
                        response.setStatus(400);
                    }
                } else {
                    response.getWriter().write("This randevou is already done!");
                    response.setStatus(400);
                }
            } else {
                response.getWriter().write("Randevouz with this Id does not exist!");
                response.setStatus(400);
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(CancelRandevouz.class.getName()).log(Level.SEVERE, null, ex);
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
