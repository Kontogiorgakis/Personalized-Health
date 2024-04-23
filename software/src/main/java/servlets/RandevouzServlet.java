/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import com.google.gson.Gson;
import database.tables.EditRandevouzTable;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mainClasses.Randevouz;
import org.json.JSONObject;

/**
 *
 * @author ntigo
 */
@WebServlet("/RandevouzServlet")
public class RandevouzServlet extends HttpServlet {

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

    /* Get all randevouz */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("doctor_id"));
        String date = request.getParameter("randevou_date");
        EditRandevouzTable ert = new EditRandevouzTable();
        ArrayList<Randevouz> randevouz = new ArrayList<>();
        try {
            randevouz = ert.databaseToRandevouzList(id, date);
            if (!randevouz.isEmpty()) {
                response.setStatus(200);
                Gson gson = new Gson();
                response.getWriter().write(gson.toJson(randevouz));
                System.out.println(gson.toJson(randevouz));
            }else{
                response.setStatus(405);
                response.getWriter().write("You do not have a randevou that day");
                System.out.println("You do not have a randevou that day");
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(RandevouzServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /* Add randevou */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        EditRandevouzTable ert = new EditRandevouzTable();
        String randevouzJSON = ert.getJSONFromAjax(request.getReader());
        System.out.println(randevouzJSON);
        
        JSONObject json = new JSONObject(randevouzJSON);
        String dateToday = java.time.LocalDate.now().toString();
        String inputDate = json.getString("date_time").substring(0, 10);
        String hour = json.getString("date_time").substring(11, 13);
        String minutes = json.getString("date_time").substring(14, 16);
        
        System.out.println("Date: " + inputDate + " hour: "+ hour + " mins: "+minutes);
        int price = Integer.parseInt(json.getString("price"));
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date start = formatter.parse(dateToday);
            Date end = formatter.parse(inputDate);
            
            DateFormat timeFormatter = new SimpleDateFormat("HH:mm");
            Date time = timeFormatter.parse(hour + ":" + minutes);
            Date open = timeFormatter.parse("08:30");
            Date close = timeFormatter.parse("20:30");
            
            if(end.after(start) && time.after(open) && time.before(close) && price >= 10 && price <= 80){
                System.out.println("komple");
                Randevouz rand = new Randevouz();
                rand.setDoctor_id(Integer.parseInt(json.getString("id_doctor")));
                rand.setUser_id(Integer.parseInt(json.getString("id_user")));
                rand.setDate_time(inputDate + " " + hour + ":" + minutes + ":00");
                rand.setPrice(price);
                rand.setDoctor_info(json.getString("doc_info"));
                rand.setUser_info(json.getString("user_info"));
                rand.setStatus(json.getString("status"));
                
                ert.createNewRandevouz(rand);
                response.setStatus(200);
                response.getWriter().write(randevouzJSON);
            }else{
                response.setStatus(403);
                response.getWriter().write("Error! Can not create Randevou with these credentials");
            }
        } catch (ClassNotFoundException | ParseException ex) {
            Logger.getLogger(RandevouzServlet.class.getName()).log(Level.SEVERE, null, ex);
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