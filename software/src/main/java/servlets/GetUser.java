/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import database.tables.EditDoctorTable;
import database.tables.EditSimpleUserTable;

import javax.print.Doc;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import mainClasses.Doctor;
import mainClasses.SimpleUser;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author micha
 */
public class GetUser extends HttpServlet {

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
            throws ServletException, IOException, SQLException, ClassNotFoundException {
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

        response.setContentType("text/html;charset=UTF-8");

        System.out.println("asdfasdf");
        //create buffer for username email and amka







        String buffer =request.getParameter("val");
        int num=Integer.parseInt(buffer);

        System.out.println(num);

        //make bloodtype right


        //StringBuffer sb= new StringBuffer(buffer);

        String username=request.getParameter("username");
        String email=request.getParameter("email");
        String amka=request.getParameter("amka");





        //System.out.println(username);
        //System.out.println(password);
        //System.out.println(amka);
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            EditSimpleUserTable eut = new EditSimpleUserTable();
            EditDoctorTable doc = new EditDoctorTable();

            //users
            SimpleUser uname = eut.databaseToSimpleUser(username);
            SimpleUser mail = eut.databaseToSimpleUserEmail(email);
            SimpleUser aamka = eut.databaseToSimpleUserAmka(amka);

            //docs
            Doctor dname = doc.databaseToDoctor(username);
            Doctor dmail = doc.databaseToDoctorEmail(email);
            Doctor damka = doc.databaseToDoctorAmka(amka);

            if(num==1) {
                if (uname == null && dname==null) {
                    System.out.println("404");
                    response.setStatus(404);
                } else {
                    System.out.println("200");
                    String json = eut.simpleUserToJSON(uname);
                    out.println(json);
                    response.setStatus(200);
                }
            }else if(num==2){
                if (mail == null && dmail==null) {
                    System.out.println("404");
                    response.setStatus(404);
                } else {
                    System.out.println("200");
                    String json = eut.simpleUserToJSON(mail);
                    out.println(json);
                    response.setStatus(200);
                }
            }else if(num==3){
                if (aamka == null && damka==null){
                    System.out.println("404");
                    response.setStatus(404);
                } else {
                    System.out.println("200");
                    String json = eut.simpleUserToJSON(aamka);
                    out.println(json);
                    response.setStatus(200);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(GetUser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GetUser.class.getName()).log(Level.SEVERE, null, ex);
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

        //create buffer for username email and amka


        //StringBuffer sb= new StringBuffer(buffer);
        //String that=sb.getChars();

        /*type of user*/
        String type=request.getParameter("type_of_user");
        /* user/doctor attributes*/
        String username=request.getParameter("username");
        String email=request.getParameter("email");
        String password=request.getParameter("password");
        String firstname=request.getParameter("firstname");
        String lastname=request.getParameter("lastname");
        String birthdate=request.getParameter("birthdate");
        String gender=request.getParameter("gender");
        String amka=request.getParameter("amka");
        String country=request.getParameter("country");
        String city=request.getParameter("city");
        String address=request.getParameter("address");
        String lat=request.getParameter("lat");
        String lon=request.getParameter("lon");
        String telephone=request.getParameter("telephone");
        String height=request.getParameter("height");
        String weight=request.getParameter("weight");
        String blooddonor=request.getParameter("blooddonor");
        String bloodtype=request.getParameter("bloodtype");

        /*for doctor*/
        String info=request.getParameter("informations");
        String speciality=request.getParameter("selectiva");

        System.out.println(info);

        /*create user and doctor*/

        if(type.equals("user")){
            SimpleUser user=new SimpleUser();
            user.setUsername(username);
            user.setEmail(email);
            user.setPassword(password);
            user.setFirstname(firstname);
            user.setLastname(lastname);
            user.setBirthdate(birthdate);
            user.setGender(gender);
            user.setAmka(amka);
            user.setCountry(country);
            user.setCity(city);
            user.setAddress(address);
            user.setLat(Double.parseDouble(lat));
            user.setLon(Double.parseDouble(lon));
            user.setTelephone(telephone);
            user.setHeight(Integer.parseInt(height));
            user.setWeight(Double.parseDouble(weight));

            if(blooddonor.equals("NO")){
                user.setBloodDonor(0);
            }else{
                user.setBloodDonor(1);
            }
            user.setBloodtype(bloodtype);

            EditSimpleUserTable test = new EditSimpleUserTable();

            try {
                test.addNewSimpleUser(user);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }


        }else{
            Doctor doctor=new Doctor();
            doctor.setUsername(username);
            doctor.setEmail(email);
            doctor.setPassword(password);
            doctor.setFirstname(firstname);
            doctor.setLastname(lastname);
            doctor.setBirthdate(birthdate);
            doctor.setGender(gender);
            doctor.setAmka(amka);
            doctor.setCountry(country);
            doctor.setCity(city);
            doctor.setAddress(address);
            doctor.setLat(Double.parseDouble(lat));
            doctor.setLon(Double.parseDouble(lon));
            //doctor.setLat(25.234234234);
            //doctor.setLon(34.000142341);
            doctor.setTelephone(telephone);
            doctor.setHeight(Integer.parseInt(height));
            doctor.setWeight(Double.parseDouble(weight));

            if(blooddonor.equals("NO")){
                doctor.setBloodDonor(0);
            }else{
                doctor.setBloodDonor(1);
            }
            doctor.setBloodtype(bloodtype);
            doctor.setDoctor_info(info);
            doctor.setSpecialty(speciality);

            EditDoctorTable doctorTable=new EditDoctorTable();

            try {
                doctorTable.addNewDoctor(doctor);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

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
