/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import database.tables.EditRandevouzTable;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mainClasses.Randevouz;
import org.json.JSONObject;

/**
 *
 * @author ntigo
 */
public class UpdateRandevouz extends HttpServlet {

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
        processRequest(request, response);
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
        EditRandevouzTable ert = new EditRandevouzTable();
        String randevouzJSON = ert.getJSONFromAjax(request.getReader());
        JSONObject json = new JSONObject(randevouzJSON);
        System.out.println(randevouzJSON);
        
        int id = Integer.parseInt(json.getString("randevouz_idUpd"));
        String inputDate = json.getString("date_timeUpd").substring(0, 10);
        String hour = json.getString("date_timeUpd").substring(11, 13);
        String minutes = json.getString("date_timeUpd").substring(14, 16);
        int price = Integer.parseInt(json.getString("priceUpd"));
        
        try {
            Randevouz rand = new Randevouz();
            rand.setRandevouz_id(id);
            rand.setDate_time(inputDate + " " + hour + ":" + minutes + ":00");
            rand.setPrice(price);
            rand.setDoctor_info(json.getString("doc_infoUpd"));
            rand.setUser_info(json.getString("user_infoUpd"));
            
            ert.updateRandevouz(rand);
            response.setStatus(200);
            response.getWriter().write("Randevou updated!");
            System.out.println("Randevou updated!");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(RandevouzServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(UpdateRandevouz.class.getName()).log(Level.SEVERE, null, ex);
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
