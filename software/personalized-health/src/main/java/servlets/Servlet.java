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
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
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
@WebServlet("/Servlet")
public class Servlet extends HttpServlet {

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

        String value=request.getParameter("val");
        int val=Integer.parseInt(value);

        if(val==1){

            String username=request.getParameter("naming");
            String email=request.getParameter("email");
            String password=request.getParameter("passwording");
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
                test.updateUser(user);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
           }
        }else if(val==2){
            String email=request.getParameter("email");

            try (PrintWriter out = response.getWriter()) {
                /* TODO output your page here. You may use following sample code. */
                EditSimpleUserTable eut = new EditSimpleUserTable();
                EditDoctorTable doc = new EditDoctorTable();

                //users
                SimpleUser mail = eut.databaseToSimpleUserEmail(email);
                Doctor dmail = doc.databaseToDoctorEmail(email);


                if (mail == null && dmail==null) {
                    System.out.println("404");
                    response.setStatus(404);
                } else {
                    System.out.println("200");
                    String json = eut.simpleUserToJSON(mail);
                    out.println(json);
                    response.setStatus(200);
                }

            }catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
        }else if(val==3){
            System.out.println("mamouni");
            HttpSession session=request.getSession();
            if(session.getAttribute("loggedIn")!=null){
                response.setStatus(200);
            }
            else{
                response.setStatus(403);
            }
        }else{
            //function seedoctors

            System.out.println("glossa mou kilaei sna nero ");

            String username = request.getParameter("username");
            String password = request.getParameter("password");

            //System.out.println(username + "   " + password);

            try (PrintWriter out = response.getWriter()) {
                // TODO output your page here. You may use following sample code.
                EditDoctorTable doc = new EditDoctorTable();
                EditDoctorTable doct=new EditDoctorTable();



                /*creating an arraylist of certified docs*/
                String allDocs;
                ArrayList<Doctor> list = new ArrayList<>();
                list = doct.certifiedDocs();

                allDocs="{";
                for(int i=0; i<list.size(); i++){
                    allDocs=allDocs+"\"firstname "+i+"\":\""+list.get(i).getFirstname()+"\", ";
                    allDocs=allDocs+"\"lastname "+i+"\":\""+list.get(i).getLastname()+"\", ";
                    allDocs=allDocs+"\"address "+i+"\":\""+list.get(i).getAddress()+"\", ";
                    allDocs=allDocs+"\"city "+i+"\":\""+list.get(i).getCity()+"\", ";
                    allDocs=allDocs+"\"informations "+i+"\":\""+list.get(i).getDoctor_info()+"\", ";
                    allDocs=allDocs+"\"speciality "+i+"\":\""+list.get(i).getSpecialty()+"\", ";
                    if(i+1==list.size()){
                        allDocs=allDocs+"\"telephone "+i+"\":\""+list.get(i).getTelephone()+"\"}";
                    }else{
                        allDocs=allDocs+"\"telephone "+i+"\":\""+list.get(i).getTelephone()+"\", ";
                    }
                }
                out.print(allDocs);


                //System.out.println(doc.certifiedDocs());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }


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

        String value = request.getParameter("val");
        int val = Integer.parseInt( value);

        if(val==1) {
            System.out.println("you could be my love");

            String username = request.getParameter("username");
            String password = request.getParameter("password");

            System.out.println(username + "   " + password);

            try (PrintWriter out = response.getWriter()) {
                /* TODO output your page here. You may use following sample code. */
                EditSimpleUserTable eut = new EditSimpleUserTable();

                //users
                SimpleUser uname = eut.databaseToSimpleUser(username);
                SimpleUser pword = eut.databaseToSimpleUserPassword(password);

                if (uname != null && pword != null) {
                    System.out.println("404");
                    response.setContentType("text/json");


                    out.print(eut.databaseUserToJSON(username, password));


                    //response.setContentType("text/json");
                    //response.getWriter().print(eut.databaseUserToJSON(username,password));
                    //String mes= eut.databaseUserToJSON(username,password);
                    //System.out.println(mes);

                    response.setStatus(404);
                } else {
                    System.out.println("200");
                    response.setStatus(200);
                }


            } catch (SQLException ex) {
                Logger.getLogger(GetUser.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(GetUser.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            HttpSession session=request.getSession();
            if(session.getAttribute("loggedIn")!=null){
                session.invalidate();
                response.setStatus(200);
            }
            else{
                response.setStatus(403);
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
