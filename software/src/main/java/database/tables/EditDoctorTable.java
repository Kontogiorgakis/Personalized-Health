/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database.tables;

import mainClasses.Doctor;
import com.google.gson.Gson;
import mainClasses.User;
import database.DB_Connection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import mainClasses.SimpleUser;

/**
 *
 * @author Mike
 */
public class EditDoctorTable {
    
    /*ArrayList of all doctors*/
    public ArrayList<Doctor> allDoctorsDatabase() throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        ArrayList<Doctor> doctors=new ArrayList<Doctor>();
        ResultSet rs;
        try {
            rs = stmt.executeQuery("SELECT * FROM doctors");
            System.out.println("grafoune gg");
            while (rs.next()) {
                String json = DB_Connection.getResultsToJSON(rs);
                Gson gson = new Gson();
                Doctor doctor = gson.fromJson(json, Doctor.class);
                doctors.add(doctor);
            }
            return doctors;

        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return null;
    }
    
    /*certify doctor*/
    public void certifyDoctor(String username) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        String update = "UPDATE doctors SET certified='" + 1 + "' WHERE username = '" + username + "'";
        stmt.executeUpdate(update);
    }
    
    /*delete user from database*/
    public void deleteDoctor(String username) throws SQLException, ClassNotFoundException{
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        String deleteQuery = "DELETE FROM doctors WHERE username='" + username + "'";
        stmt.executeUpdate(deleteQuery);
        System.out.println("#Doctor Deleted successfully");
        stmt.close();
        con.close();
    }
    
    
    

    public void addDoctorFromJSON(String json) throws ClassNotFoundException {
        Doctor doc = jsonToDoctor(json);
        addNewDoctor(doc);
    }

    public Doctor jsonToDoctor(String json) {
        Gson gson = new Gson();

        Doctor doc = gson.fromJson(json, Doctor.class);
        return doc;
    }

    public String doctorToJSON(Doctor doc) {
        Gson gson = new Gson();

        String json = gson.toJson(doc, Doctor.class);
        return json;
    }

    public void updateDoctor(String username, int height) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        String update = "UPDATE doctors SET height='" + height + "' WHERE username = '" + username + "'";
        System.out.println("#Doctor has been certified!");
        stmt.executeUpdate(update);
    }

    public void printDoctorDetails(String username, String password) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        ResultSet rs;
        try {
            rs = stmt.executeQuery("SELECT * FROM doctors WHERE username = '" + username + "' AND password='" + password + "'");
            while (rs.next()) {
                System.out.println("===Result===");
                DB_Connection.printResults(rs);
            }

        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
    }

    public Doctor databaseToDoctor(String username) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        ResultSet rs;
        try {
            rs = stmt.executeQuery("SELECT * FROM doctors WHERE username = '" + username + "'");
            rs.next();
            String json = DB_Connection.getResultsToJSON(rs);
            Gson gson = new Gson();
            Doctor doc = gson.fromJson(json, Doctor.class);
            return doc;
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return null;
    }

    /*databaseToDoctor for email*/
    public Doctor databaseToDoctorEmail(String email) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        ResultSet rs;
        try {
            rs = stmt.executeQuery("SELECT * FROM doctors WHERE email = '" + email + "'");
            rs.next();
            String json = DB_Connection.getResultsToJSON(rs);
            Gson gson = new Gson();
            Doctor doc = gson.fromJson(json, Doctor.class);
            return doc;
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return null;
    }

    /*databaseToDoctor for amka*/
    public Doctor databaseToDoctorAmka(String amka) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        ResultSet rs;
        try {
            rs = stmt.executeQuery("SELECT * FROM doctors WHERE amka = '" + amka + "'");
            rs.next();
            String json = DB_Connection.getResultsToJSON(rs);
            Gson gson = new Gson();
            Doctor doc = gson.fromJson(json, Doctor.class);
            return doc;
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return null;
    }

    /* mpampis */
    public String databaseDoctorToJSON(String username, String password) throws SQLException, ClassNotFoundException{
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        ResultSet rs;
        try {
            rs = stmt.executeQuery("SELECT * FROM doctors WHERE username = '" + username + "' AND password='" + password + "'");
            rs.next();
            String json = DB_Connection.getResultsToJSON(rs);
            return json;
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return null;
    }
    
    /*mpampis*/
    public Doctor databaseToDoctorCertified(String username) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        ResultSet rs;
        try {
            rs = stmt.executeQuery("SELECT * FROM doctors WHERE username = '" + username + "' AND certified = 1");
            rs.next();
            String json=DB_Connection.getResultsToJSON(rs);
            Gson gson = new Gson();
            Doctor doc = gson.fromJson(json, Doctor.class);
            return doc;
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return null;
    }
    
    public void updateDoctor(Doctor doc) throws ClassNotFoundException, SQLException{
        Connection con = DB_Connection.getConnection();

        Statement stmt = con.createStatement();

        String insertQuery = "UPDATE doctors SET username ='" + doc.getUsername() + "', email = '" + doc.getEmail() + "', password = '" + doc.getPassword() + "', firstname = '" + doc.getFirstname() + "', lastname = '" + doc.getLastname() +"', birthdate = '" + doc.getBirthdate() + "', gender = '" + doc.getGender() + "', amka = '" + doc.getAmka() + "', country = '" + doc.getCountry() + "', city = '" + doc.getCity() +
                "', address = '" + doc.getAddress() +"', lat = '" + doc.getLat() + "', lon = '" + doc.getLon() +"', telephone = '" + doc.getTelephone() + "', height = '" + doc.getHeight() +"', weight = '"+ doc.getWeight() +"', blooddonor = '" + doc.getBlooddonor() + "', bloodtype = '" + doc.getBloodtype() + "', specialty = '" + doc.getSpecialty() + "', doctor_info = '" + doc.getDoctor_info() + "' WHERE username= '" + doc.getUsername()+ "'";
        System.out.println(insertQuery);
        stmt.executeUpdate(insertQuery);
        System.out.println("# The doctor was successfully updated in the database.");

        /* Get the member id from the database and set it to the member */
        stmt.close();

    }

    public ArrayList<Doctor> databaseToDoctors() throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        ArrayList<Doctor> doctors=new ArrayList<Doctor>();
        ResultSet rs;
        try {
            rs = stmt.executeQuery("SELECT * FROM doctors");
            while (rs.next()) {
                String json = DB_Connection.getResultsToJSON(rs);
                Gson gson = new Gson();
                Doctor doc = gson.fromJson(json, Doctor.class);
                doctors.add(doc);
            }
            return doctors;

        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return null;
    }

    public String databaseToJSON(String username, String password) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        ResultSet rs;
        try {
            rs = stmt.executeQuery("SELECT * FROM doctors WHERE username = '" + username + "' AND password='" + password + "'");
            rs.next();
            String json = DB_Connection.getResultsToJSON(rs);
            return json;
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return null;
    }

    /*csd 4412 certify 1*/
    public ArrayList<Doctor> certifiedDocs() throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        ArrayList<Doctor> doctors=new ArrayList<Doctor>();
        ResultSet rs;
        try {
            rs = stmt.executeQuery("SELECT * FROM doctors WHERE certified = '" + 1 + "'");
            while (rs.next()) {
                String json = DB_Connection.getResultsToJSON(rs);
                Gson gson = new Gson();
                Doctor doc = gson.fromJson(json, Doctor.class);
                doctors.add(doc);
            }
            return doctors;

        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return null;
    }


    public void createDoctorTable() throws SQLException, ClassNotFoundException {

        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        String query = "CREATE TABLE doctors"
                + "(doctor_id INTEGER not NULL AUTO_INCREMENT, "
                + "    username VARCHAR(30) not null unique,"
                + "    email VARCHAR(40) not null unique,	"
                + "    password VARCHAR(32) not null,"
                + "    firstname VARCHAR(20) not null,"
                + "    lastname VARCHAR(30) not null,"
                + "    birthdate DATE not null,"
                + "    gender  VARCHAR (7) not null,"
                + "    amka VARCHAR (11) not null,"
                + "    country VARCHAR(30) not null,"
                + "    city VARCHAR(50) not null,"
                + "    address VARCHAR(50) not null,"
                + "    lat DOUBLE,"
                + "    lon DOUBLE,"
                + "    telephone VARCHAR(14) not null,"
                + "    height INTEGER,"
                + "    weight DOUBLE,"
                + "   blooddonor BOOLEAN,"
                + "   bloodtype VARCHAR(7) not null,"
                + "   specialty VARCHAR(30) not null,"
                + "   doctor_info VARCHAR(500) not null,"
                + "   certified BOOLEAN,"
                + " PRIMARY KEY ( doctor_id))";
        stmt.execute(query);
        stmt.close();
    }

    /**
     * Establish a database connection and add in the database.
     *
     * @throws ClassNotFoundException
     */
    public void addNewDoctor(Doctor doc) throws ClassNotFoundException {
        try {
            Connection con = DB_Connection.getConnection();

            Statement stmt = con.createStatement();

            String insertQuery = "INSERT INTO "
                    + " doctors (username,email,password,firstname,lastname,birthdate,gender,amka,country,city,address,"
                    + "lat,lon,telephone,height,weight,blooddonor,bloodtype,specialty,doctor_info,certified)"
                    + " VALUES ("
                    + "'" + doc.getUsername() + "',"
                    + "'" + doc.getEmail() + "',"
                    + "'" + doc.getPassword() + "',"
                    + "'" + doc.getFirstname() + "',"
                    + "'" + doc.getLastname() + "',"
                    + "'" + doc.getBirthdate() + "',"
                    + "'" + doc.getGender() + "',"
                    + "'" + doc.getAmka() + "',"
                    + "'" + doc.getCountry() + "',"
                    + "'" + doc.getCity() + "',"
                    + "'" + doc.getAddress() + "',"
                    + "'" + doc.getLat() + "',"
                    + "'" + doc.getLon() + "',"
                    + "'" + doc.getTelephone() + "',"
                    + "'" + doc.getHeight() + "',"
                    + "'" + doc.getWeight() + "',"
                    + "'" + doc.isBloodDonor() + "',"
                    + "'" + doc.getBloodtype() + "',"
                    + "'" + doc.getSpecialty() + "',"
                    + "'" + doc.getDoctor_info() + "',"
                    + "'" + doc.getCertified() + "'"
                    + ")";
            //stmt.execute(table);
            System.out.println(insertQuery);
            stmt.executeUpdate(insertQuery);
            System.out.println("# The doctor was successfully added in the database.");

            /* Get the member id from the database and set it to the member */
            stmt.close();

        } catch (SQLException ex) {
            Logger.getLogger(EditDoctorTable.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
