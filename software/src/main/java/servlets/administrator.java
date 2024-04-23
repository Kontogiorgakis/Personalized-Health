/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlets;

import database.tables.EditDoctorTable;
import database.tables.EditSimpleUserTable;
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
import mainClasses.Doctor;
import mainClasses.SimpleUser;

/**
 *
 * @author manos
 */
@WebServlet("/administrator")
public class administrator extends HttpServlet {

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
        
        response.setContentType("text/html;charset=UTF-8");
           
        try (PrintWriter out = response.getWriter()) {
            
            EditDoctorTable doctor= new EditDoctorTable();
            
            
            /*creating an arraylist of ALL users*/
            String allDoctors;
            ArrayList<Doctor> list = new ArrayList<>();
            list = doctor.allDoctorsDatabase();
            
            allDoctors="{";
            for(int i=0; i<list.size(); i++){
                allDoctors=allDoctors+"\"username "+i+"\":\""+list.get(i).getUsername()+"\", ";
                allDoctors=allDoctors+"\"firstname "+i+"\":\""+list.get(i).getFirstname()+"\", ";
                allDoctors=allDoctors+"\"lastname "+i+"\":\""+list.get(i).getLastname()+"\", ";
                allDoctors=allDoctors+"\"certified "+i+"\":\""+list.get(i).getCertified()+"\", ";
                if(i+1==list.size()){
                    allDoctors=allDoctors+"\"birthdate "+i+"\":\""+list.get(i).getBirthdate()+"\"}";
                }else{
                    allDoctors=allDoctors+"\"birthdate "+i+"\":\""+list.get(i).getBirthdate()+"\", ";
                }
            }
            System.out.println(allDoctors);
            out.print(allDoctors);

        } catch (SQLException ex) {
            Logger.getLogger(administrator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(administrator.class.getName()).log(Level.SEVERE, null, ex);
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
        
        response.setContentType("text/html;charset=UTF-8");
        System.out.println("yaso");
        //function seedoctors

           
        try (PrintWriter out = response.getWriter()) {
            
            EditSimpleUserTable user= new EditSimpleUserTable();
            
            
            /*creating an arraylist of ALL users*/
            String allUsers;
            ArrayList<SimpleUser> list = new ArrayList<>();
            list = user.allUsersDatabase();
            
            allUsers="{";
            for(int i=0; i<list.size(); i++){
                allUsers=allUsers+"\"username "+i+"\":\""+list.get(i).getUsername()+"\", ";
                allUsers=allUsers+"\"firstname "+i+"\":\""+list.get(i).getFirstname()+"\", ";
                allUsers=allUsers+"\"lastname "+i+"\":\""+list.get(i).getLastname()+"\", ";
                if(i+1==list.size()){
                    allUsers=allUsers+"\"birthdate "+i+"\":\""+list.get(i).getBirthdate()+"\"}";
                }else{
                    allUsers=allUsers+"\"birthdate "+i+"\":\""+list.get(i).getBirthdate()+"\", ";
                }
            }
            out.print(allUsers);

        } catch (SQLException ex) {
            Logger.getLogger(administrator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(administrator.class.getName()).log(Level.SEVERE, null, ex);
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
