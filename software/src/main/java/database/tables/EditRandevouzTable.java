/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database.tables;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import mainClasses.Randevouz;
import database.DB_Connection;
import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;

/**
 *
 * @author Mike
 */
public class EditRandevouzTable {

   
    public void addRandevouzFromJSON(String json) throws ClassNotFoundException{
         Randevouz r=jsonToRandevouz(json);
         createNewRandevouz(r);
    }
    
    
     public Randevouz databaseToRandevouz(int id) throws SQLException, ClassNotFoundException{
         Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        ResultSet rs;
        try {
            rs = stmt.executeQuery("SELECT * FROM randevouz WHERE randevouz_id= '" + id + "'");
            rs.next();
            String json=DB_Connection.getResultsToJSON(rs);
            Gson gson = new Gson();
            Randevouz bt = gson.fromJson(json, Randevouz.class);
            return bt;
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return null;
    }
    
    /* mpampis */
    public ArrayList<Randevouz> databaseToRandevouzList(int doctor_id, String date) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        ArrayList<Randevouz> randevouz = new ArrayList<>();
        ResultSet rs;
        try {
            rs = stmt.executeQuery("SELECT * FROM randevouz WHERE doctor_id='" + doctor_id + "' AND DATE(date_time)='" + date + "'");
            while (rs.next()) {
                String json = DB_Connection.getResultsToJSON(rs);
                Gson gson = new Gson();
                Randevouz doc = gson.fromJson(json, Randevouz.class);
                randevouz.add(doc);
            }
            return randevouz;

        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return null;
    }
    

      
     public Randevouz jsonToRandevouz(String json) {
        Gson gson = new Gson();
        Randevouz r = gson.fromJson(json, Randevouz.class);
        return r;
    }
     
         
      public String randevouzToJSON(Randevouz r) {
        Gson gson = new Gson();

        String json = gson.toJson(r, Randevouz.class);
        return json;
    }


    public void updateRandevouz(Randevouz rand) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        String updateQuery = "UPDATE randevouz SET date_time='" + rand.getDate_time() + "', price='" + rand.getPrice() + "', user_info='" + rand.getUser_info() + "', doctor_info='" + rand.getDoctor_info() + "' WHERE randevouz_id = '" + rand.getRandevouz_id() + "'";
        stmt.executeUpdate(updateQuery);
        stmt.close();
        con.close();
    }
    
    public void statusRandevouz(int randevouzID, String status) throws SQLException, ClassNotFoundException{
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        String updateQuery = "UPDATE randevouz SET status='" + status +"' WHERE randevouz_id = '" + randevouzID + "'";
        stmt.executeUpdate(updateQuery);
        stmt.close();
        con.close();
    }

    public void deleteRandevouz(int randevouzID) throws SQLException, ClassNotFoundException{
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        String deleteQuery = "DELETE FROM randevouz WHERE randevouz_id='" + randevouzID + "'";
        stmt.executeUpdate(deleteQuery);
        stmt.close();
        con.close();
    }

    public String getAMKAfromRandevouzID(int id) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        ResultSet rs, rs2;
        try {
            rs = stmt.executeQuery("SELECT user_id FROM randevouz WHERE randevouz_id = '" + id + "'");
            rs.next();

            String json = DB_Connection.getResultsToJSON(rs);
            JSONObject jsonObject = new JSONObject(json);
            String userId = (String) jsonObject.get("user_id");

            rs2 = stmt.executeQuery("SELECT amka FROM users WHERE user_id = '" + userId + "'");
            rs2.next();
            json = DB_Connection.getResultsToJSON(rs2);
            JSONObject jsonObject2 = new JSONObject(json);
            return (String) jsonObject2.get("amka");
        } catch (SQLException e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return null;
    }

    /**
     * Returns randevouz if any of user's randevouz is "done".
     */
    public Randevouz startConnection(int user_id) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        try {
            ResultSet rs = stmt.executeQuery("SELECT * FROM randevouz WHERE user_id= '" + user_id + "' AND status= 'done'");
            rs.next();
            String json = DB_Connection.getResultsToJSON(rs);
            Gson gson = new Gson();
            Randevouz r = gson.fromJson(json, Randevouz.class);
            return r;
        } catch (JsonSyntaxException | SQLException e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return null;
    }
    
    
    /**
     * Returns randevouz if any of user's randevouz is "done".
     */
    public Randevouz startConnectionDoctor(int doctor_id) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        try {
            ResultSet rs = stmt.executeQuery("SELECT * FROM randevouz WHERE doctor_id= '" + doctor_id + "' AND status= 'done'");
            rs.next();
            String json = DB_Connection.getResultsToJSON(rs);
            Gson gson = new Gson();
            Randevouz r = gson.fromJson(json, Randevouz.class);
            return r;
        } catch (JsonSyntaxException | SQLException e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return null;
    }
    
    public String getJSONFromAjax(BufferedReader reader) throws IOException {
        StringBuilder buffer = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }
        String data = buffer.toString();
        return data;
    }


    public void createRandevouzTable() throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        String sql = "CREATE TABLE randevouz "
                + "(randevouz_id INTEGER not NULL AUTO_INCREMENT, "
                + " doctor_id INTEGER not NULL, "
                + " user_id INTEGER not NULL, "
                + " date_time TIMESTAMP not NULL, "
                + " price INTEGER  not NULL, "
                + " doctor_info VARCHAR(500),"
                + " user_info VARCHAR(500),"
                + " status VARCHAR(15) not null,"
                + "FOREIGN KEY (doctor_id) REFERENCES doctors(doctor_id), "
                + " PRIMARY KEY ( randevouz_id  ))";
        stmt.execute(sql);
        stmt.close();
        con.close();

    }

    /**
     * Establish a database connection and add in the database.
     *
     * @throws ClassNotFoundException
     */
    public void createNewRandevouz(Randevouz rand) throws ClassNotFoundException {
        try {
            Connection con = DB_Connection.getConnection();

            Statement stmt = con.createStatement();

            String insertQuery = "INSERT INTO "
                    + " randevouz (doctor_id,user_id,date_time,price,doctor_info,user_info,status)"
                    + " VALUES ("
                    + "'" + rand.getDoctor_id() + "',"
                    + "'" + rand.getUser_id() + "',"
                    + "'" + rand.getDate_time() + "',"
                    + "'" + rand.getPrice() + "',"
                    + "'" + rand.getDoctor_info() + "',"
                    + "'" + rand.getUser_info() + "',"
                    + "'" + rand.getStatus() + "'"
                    + ")";
            //stmt.execute(table);

            stmt.executeUpdate(insertQuery);
            System.out.println("# The randevouz was successfully added in the database.");

            /* Get the member id from the database and set it to the member */
            stmt.close();

        } catch (SQLException ex) {
            Logger.getLogger(EditRandevouzTable.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updateRandevouz(int i, int i0, String provlima_Me_vitamini, String selected) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
