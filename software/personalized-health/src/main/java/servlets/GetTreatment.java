/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlets;

import database.tables.EditSimpleUserTable;
import database.tables.EditTreatmentTable;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mainClasses.SimpleUser;
import mainClasses.Treatment;

/**
 *
 * @author manos
 */
@WebServlet("/GetTreatment")
public class GetTreatment extends HttpServlet {

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
        processRequest(request, response);
        System.out.println("tosa");
        String amka=request.getParameter("amka");
        
        EditSimpleUserTable simpleuserTable=new EditSimpleUserTable();
        try {
            SimpleUser simpleuser= simpleuserTable.databaseToSimpleUserAmka(amka);
            int id=simpleuser.getUser_id();
            //System.out.println(simpleuser.getUser_id());
            
            ArrayList<Treatment> list = new ArrayList<>();
            EditTreatmentTable treatmentTable = new EditTreatmentTable();
            list=treatmentTable.allTreatments(id);
            PrintWriter out = response.getWriter();
            if(list.size()==0){
                System.out.println("No Treatments for your Tests");
                out.println("No Treatments for your Tests");
                response.setStatus(400);
            }else{
                String[] treatments=new String[list.size()];
                String responseMessage="";
                for(int i=0; i<list.size(); i++){
                    treatments[i]=treatmentTable.treatmentToJSON(list.get(i));
                    System.out.println(treatments[i]);
                    responseMessage=responseMessage+treatments[i]+"\n";
                }
                System.out.println("These are your treatments:\n"+responseMessage);
                out.println("These are your treatments:\n"+responseMessage);
                response.setStatus(200);
            }
            //System.out.println("List size= "+list.size());
            
            
        } catch (SQLException ex) {
            Logger.getLogger(GetTreatment.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GetTreatment.class.getName()).log(Level.SEVERE, null, ex);
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
