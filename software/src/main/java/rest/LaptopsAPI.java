/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import database.tables.EditBloodTestTable;
import mainClasses.BloodTest;


import com.google.gson.Gson;
import java.sql.SQLException;
import java.text.ParseException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONObject;
import java.text.SimpleDateFormat;  
import java.util.Date; 


/**
 * REST Web Service
 *
 * @author mountant
 */
@Path("eshop")
public class LaptopsAPI {

        static HashMap<String, Laptop> laptops = new HashMap<>();

    /**
     * Creates a new instance of GenericResource
     */
    public LaptopsAPI() {
        if (laptops.isEmpty()) {
            Laptop p = new Laptop("Toshiba","Toshiba_Satellite","i5","8GB",15);
            Laptop p1 = new Laptop("Toshiba","Toshiba_satellite_PRO","i7","16GB",15);
            Laptop p2 = new Laptop("Dell","Dell_A","i5","8GB",150);

            laptops.put(p.name, p);
            laptops.put(p1.name, p1);
            laptops.put(p2.name, p2);
        }
    }

  
    @GET
    @Path("/laptops")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAllLaptops() {
        Response.Status status = Response.Status.OK;
            String json = new Gson().toJson(laptops.values());
            return Response.status(status).type("application/json").entity(json).build();
    }

    /**
     * Retrieves representation of an instance of restApi.GenericResource
     *
     * @param brand
     * @param id
     * @return an instance of java.lang.String
     */
    @GET
    @Path("/laptops/{brand}")
    @Produces({ MediaType.APPLICATION_JSON})
    public Response getLaptop(@PathParam("brand") String brand) {
        Response.Status status = Response.Status.OK;
         ArrayList<Laptop> laptopsWithBrand= new ArrayList<Laptop>();
         for (Laptop l : laptops.values()) {
            if (l.brand.equals(brand)) {
                laptopsWithBrand.add(l);
            }
        }
        if(!laptopsWithBrand.isEmpty()){
            String json = new Gson().toJson(laptopsWithBrand);
            return Response.status(status).type("application/json").entity(json).build();
        }
        else{
               return Response.status(Response.Status.BAD_REQUEST).type("application/json").entity("{\"error\":\"Laptop Brand not exists\"}").build();
        }
    }
    
    
    /*ASKISI 3*/
    @GET
    @Path("/bloodTestMeasure/{AMKA}/{Measure}")
    @Produces({ MediaType.APPLICATION_JSON})
    public Response getMeasureTest(@PathParam("AMKA") String amka, @PathParam("Measure") String measure) throws SQLException {
        Response.Status status = Response.Status.OK;
        //System.out.println("death race");
        
        EditBloodTestTable bloodtest= new EditBloodTestTable();
        
        ArrayList<BloodTest> list = new ArrayList<>();   
        try {
                list=bloodtest.BloodyTest(amka);
                if(list.size()==0){
                   return Response.status(Response.Status.BAD_REQUEST).type("application/json").entity("{\"error\":\"Amka not exist!\"}").build(); 
                }
               
                String[] allDocs=new String[list.size()];
      
              
                for(int i=0; i<list.size(); i++){
                    allDocs[i]="{";
                    allDocs[i]=allDocs[i]+"\"test_date\":\""+list.get(i).getTest_date()+"\", ";
                    allDocs[i]=allDocs[i]+"\"medical_center\":\""+list.get(i).getMedical_center()+"\", ";
                    if(measure.equals("blood_sugar")){
                        allDocs[i]=allDocs[i]+"\"blood_sugar\":\""+list.get(i).getBlood_sugar()+"\"}";
                    }else if(measure.equals("cholesterol")){
                        allDocs[i]=allDocs[i]+"\"cholesterol\":\""+list.get(i).getCholesterol()+"\"}";
                    }else if(measure.equals("iron")){
                        allDocs[i]=allDocs[i]+"\"iron\":\""+list.get(i).getIron()+"\"}";
                    }else if(measure.equals("vitamin_d3")){
                        allDocs[i]=allDocs[i]+"\"vitamin_d3\":\""+list.get(i).getVitamin_d3()+"\"}";
                    }else if(measure.equals("vitamin_b12")){
                        allDocs[i]=allDocs[i]+"\"vitamin_b12\":\""+list.get(i).getVitamin_b12()+"\"}";
                    }else{
                        return Response.status(Response.Status.BAD_REQUEST).type("application/json").entity("{\"error\":\"Measure not exist!\"}").build(); 
                    } 
                }
                //System.out.println(allDocs[0]);
                ///System.out.println(allDocs[1]);
                String results="[";
                for(int i=0; i<list.size();i++){
                    if(i+1==list.size()){
                        results=results+allDocs[i]+"]";
                    }else{
                        results=results+allDocs[i]+",";
                    }
                }
                //System.out.println(results);
                return Response.status(status).type("application/json").entity(results).build();      
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(LaptopsAPI.class.getName()).log(Level.SEVERE, null, ex);
            }
            return Response.status(Response.Status.CONFLICT).type("application/json").entity("{\"error\":\"Something went wrong!\"}").build(); 
    }
    
    /*ASK9ISI 2*/
    @GET
    @Path("/bloodTestMeasure/{AMKA}")
    @Produces({ MediaType.APPLICATION_JSON})
    public Response getMeasures(@PathParam("AMKA") String amka, @QueryParam("fromDate") String fromDate, @QueryParam("toDate") String toDate) throws SQLException, ParseException {
        //System.out.println(amka);
        //System.out.println(fromDate);
        //System.out.println(toDate);
        
        
        Response.Status status = Response.Status.OK;
        EditBloodTestTable bloodtest= new EditBloodTestTable();
       
        ArrayList<BloodTest> list = new ArrayList<>();   
        try {
                list=bloodtest.BloodyTest(amka);
                if(list.size()==0){
                   return Response.status(Response.Status.BAD_REQUEST).type("application/json").entity("{\"error\":\"Amka not exists!\"}").build(); 
                }
                Date[] dates=new Date[list.size()]; 
                String[] allDocs=new String[list.size()];
         
                SimpleDateFormat formatter2=new SimpleDateFormat("yyyy-MM-dd");
                String thisDate;
                
                
                for(int i=0; i<list.size(); i++){
                    allDocs[i]="{";
                    allDocs[i]=allDocs[i]+"\"amka "+i+"\":\""+list.get(i).getAmka()+"\", ";
                    allDocs[i]=allDocs[i]+"\"test_date "+i+"\":\""+list.get(i).getTest_date()+"\", ";
                    //for dates
                    //System.out.println("poios den agapa to xarti");
                    thisDate=list.get(i).getTest_date();
                    dates[i]=formatter2.parse(thisDate); 
                    //System.out.println(thisDate+"\t"+dates[i]);  
                    
                    allDocs[i]=allDocs[i]+"\"medical_center "+i+"\":\""+list.get(i).getMedical_center()+"\", ";
                    allDocs[i]=allDocs[i]+"\"blood_sugar "+i+"\":\""+list.get(i).getBlood_sugar()+"\", ";
                    allDocs[i]=allDocs[i]+"\"blood_sugar_level "+i+"\":\""+list.get(i).getBlood_sugar_level()+"\", ";
                    allDocs[i]=allDocs[i]+"\"cholesterol "+i+"\":\""+list.get(i).getCholesterol()+"\", ";
                    allDocs[i]=allDocs[i]+"\"cholesterol_level "+i+"\":\""+list.get(i).getCholesterol_level()+"\", ";
                    allDocs[i]=allDocs[i]+"\"iron "+i+"\":\""+list.get(i).getIron()+"\", ";
                    allDocs[i]=allDocs[i]+"\"iron_level "+i+"\":\""+list.get(i).getIron_level()+"\", ";
                    allDocs[i]=allDocs[i]+"\"vitamin_d3 "+i+"\":\""+list.get(i).getVitamin_d3()+"\", ";
                    allDocs[i]=allDocs[i]+"\"vitamin_d3_level "+i+"\":\""+list.get(i).getVitamin_d3_level()+"\", ";
                    allDocs[i]=allDocs[i]+"\"vitamin_b12 "+i+"\":\""+list.get(i).getVitamin_b12()+"\", ";
                    allDocs[i]=allDocs[i]+"\"vitamin_b12_level "+i+"\":\""+list.get(i).getVitamin_b12_level()+"\"}";
                }
              
                if(fromDate!=null && toDate!=null){
                    SimpleDateFormat fdate=new SimpleDateFormat("yyyy-MM-dd");
                    SimpleDateFormat tdate=new SimpleDateFormat("yyyy-MM-dd");
                    Date from=fdate.parse(fromDate);
                    Date to=fdate.parse(toDate);
                    
                    
                    int inside=0;
                    String results="[";
                    for(int i=0; i<list.size();i++){
                        /*if(list.size()==1){
                            if(dates[i].after(from) && dates[i].before(to)){
                                System.out.println("ANAMESA");
                                return Response.status(status).type("application/json").entity(allDocs[i]).build();
                            }
                        }*/
                        if(i+1==list.size()){
                            if(dates[i].after(from) && dates[i].before(to)){
                                inside=1;
                                //System.out.println("ANAMESA telos");
                                results=results+allDocs[i]+"]";
                            }
                        }else{
                            if(dates[i].after(from) && dates[i].before(to)){
                                inside=1;
                                //System.out.println("ANAMESA");
                                results=results+allDocs[i]+",";
                            }
                        }
                    }
                    
                    if(results.endsWith(",")){
                        //System.out.println("loski");
                        results = results.substring(0,results.length() - 1);
                        results=results+"]";
                        //System.out.println(results);
                    }
                    
                    if(inside==1){
                        return Response.status(status).type("application/json").entity(results).build();
                    }else{
                        return Response.status(Response.Status.BAD_REQUEST).type("application/json").entity("{\"error\":\"Not results in this range of dates!\"}").build(); 
                    }
                }else{
                    String results="[";
                    for(int i=0; i<list.size();i++){
                        if(i+1==list.size()){
                            results=results+allDocs[i]+"]";
                        }else{
                            results=results+allDocs[i]+",";
                        }
                    }
                    return Response.status(status).type("application/json").entity(results).build();
                }
                
                
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(LaptopsAPI.class.getName()).log(Level.SEVERE, null, ex);
            }
            return Response.status(Response.Status.CONFLICT).type("application/json").entity("{\"error\":\"Something went wrong!\"}").build(); 
    }
    

    @POST
    @Path("/newLaptop")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response addLaptop(String laptop) {
        Gson gson=new Gson();
        Laptop lap=gson.fromJson(laptop, Laptop.class);
        if (laptops.containsKey(lap.name)) {
            return Response.status(Response.Status.CONFLICT).type("application/json").entity("{\"error\":\"Laptop Exists\"}").build();
        } else {
            laptops.put(lap.name, lap);
            return Response.status(Response.Status.OK).type("application/json").entity("{\"success\":\"Laptop Added\"}").build();
        }
    }
    
    
    /*ASKISI 1*/
    @POST
    @Path("/newBloodTest")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response addTest(String newbloodtest) {
        //Gson gson=new Gson();
        //EditBloodTestTable lap=gson.fromJson(laptop, EditBloodTestTable.class);
        
        
        //System.out.println("asdfasdf");
        //System.out.println(newbloodtest);
        EditBloodTestTable bloodtest= new EditBloodTestTable();
        
        BloodTest blood=new BloodTest();
        
        blood=bloodtest.jsonToBloodTest(newbloodtest);
        //System.out.println("niaouuu");
       // System.out.println(blood.getAmka());
        
        //String amka=blood.getAmka();
        //System.out.println(amka);
        if(blood.getAmka()==null ||blood.getTest_date()==null||blood.getMedical_center()==null ){
            return Response.status(Response.Status.CONFLICT).type("application/json").entity("{\"failure\":\"Some data are null!!!\"}").build();
        }
        if(blood.getBlood_sugar()==0 && blood.getCholesterol()==0 && blood.getIron()==0 && blood.getVitamin_d3()==0 && blood.getVitamin_b12()==0){
            return Response.status(Response.Status.CONFLICT).type("application/json").entity("{\"failure\":\"BloodTest didn't Added!!!\"}").build();
        }
        
        //System.out.println("opanatos");
     
        try {
            bloodtest.addBloodTestFromJSON(newbloodtest);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LaptopsAPI.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.status(Response.Status.OK).type("application/json").entity("{\"success\":\"BloodTest Added\"}").build();
    }

    @PUT
    @Path("/laptopQuantity/{name}/{quantity}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateLaptop(@PathParam("name") String name, @PathParam("quantity") int quantity, @HeaderParam("Accept") String acceptHeader) {
        if (laptops.containsKey(name) == false) {
            return Response.status(Response.Status.NOT_FOUND).type("application/json").entity("{\"error\":\"Laptop Does not Exists\"}").build();
        } else if (quantity <= 0) {
            return Response.status(Response.Status.NOT_ACCEPTABLE).type("application/json").entity("{\"error\":\"Quantity must be over 0\"}").build();
        } else {
            Laptop p = laptops.get(name);
            p.quantity += quantity;
            return Response.status(Response.Status.OK).type("application/json").entity("{\"success\":\"Quantity Updated\"}").build();
        }
    }
    
    
    /*ASKISI 4*/
    @PUT
    @Path("/bloodTest/{bloodTestID}/{measure}/{value}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateTest(@PathParam("bloodTestID") int id, @PathParam("measure") String measure, @PathParam("value") int value , @HeaderParam("Accept") String acceptHeader) throws SQLException, ClassNotFoundException {
                EditBloodTestTable bloodtest= new EditBloodTestTable();
        BloodTest blood=new BloodTest();
        blood=bloodtest.databaseOfId(id);
        
        System.out.println(blood.getAmka());
        
        if(blood==null){
             return Response.status(Response.Status.NOT_FOUND).type("application/json").entity("{\"error\":\"Id doesn't exist!\"}").build();
        }
        
        if(!measure.equals("blood_sugar") && !measure.equals("cholesterol") && !measure.equals("iron") && !measure.equals("vitamin_d3") && !measure.equals("vitamin_b12")){
            return Response.status(Response.Status.NOT_FOUND).type("application/json").entity("{\"error\":\"measure isn't correct!\"}").build();
        }
        if(value<=0){
            return Response.status(Response.Status.NOT_ACCEPTABLE).type("application/json").entity("{\"error\":\"value is negative!\"}").build();
        }
        
        bloodtest.updateMeasure(id, measure, value);
        return Response.status(Response.Status.OK).type("application/json").entity("{\"Success\":\"bloodTest updated succesfull (200)\"}"+blood.getAmka()).build();
    }
 

    @DELETE
    @Path("/laptop/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteLaptop(@PathParam("name") String name) {
        Response.Status status = Response.Status.OK;
        if(laptops.containsKey(name)){
            laptops.remove(name);
            return Response.status(status).type("application/json").entity("{\"success\":\"Laptop Deleted\"}").build();
        }
        else{
             return Response.status(Response.Status.NOT_FOUND).type("application/json").entity("{\"error\":\"Laptop Does not Exists\"}").build();
        }
    }
    
    /*ASKISI 5*/
    @DELETE
    @Path("/bloodTestDeletion/{bloodTestID}/{AMKA}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteBloodTest(@PathParam("bloodTestID") int id,@PathParam("AMKA") String amka) throws SQLException, ClassNotFoundException {
        Response.Status status = Response.Status.OK;
        //System.out.println("foraaa");
        EditBloodTestTable bloodtest= new EditBloodTestTable();
        BloodTest blood=new BloodTest();
        blood=bloodtest.databaseOfId(id);
        
        System.out.println(amka+"-----"+blood.getAmka());
        if(!amka.equals(blood.getAmka())){
            return Response.status(Response.Status.NOT_FOUND).type("application/json").entity("{\"error\":\"This BloodTest doesnt belong to you!\"}").build();
        }
        
        if(blood==null){
            System.out.println("yooo");
             return Response.status(Response.Status.NOT_FOUND).type("application/json").entity("{\"error\":\"Id doesn't exist!\"}").build();
        }
        
        bloodtest.deleteBloodTest(id);
        //System.out.println("#SUCCESS");
        return Response.status(Response.Status.OK).type("application/json").entity("{\"Success\":\"bloodTest deleted succesfully (200)\"}"+blood.getAmka()).build();
    }

}
