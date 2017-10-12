package controllers;


import com.avaje.ebean.*;
import models.*;
import org.codehaus.jackson.node.ObjectNode;
import play.Logger;
import play.Logger;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Result;
import play.mvc.Controller;
import com.avaje.ebean.Ebean;
import  com.avaje.ebean.Expr;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;
import scala.util.parsing.combinator.testing.Str;
import views.*;
import views.html.*;

import views.html.fertilizers.*;
import views.html.seeds.*;
import views.html.registration.*;


import views.html.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;
import play.data.Form;
import play.libs.Json;
import play.mvc.Result;
import com.avaje.ebean.Page;
import models.Seeds;
import org.codehaus.jackson.node.ArrayNode;
import play.libs.Json;
import org.codehaus.jackson.node.ObjectNode;

import play.*;
import play.mvc.*;
import play.Logger;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Result;

import static controllers.Default.redirect;
import static play.mvc.Results.ok;


import views.html.*;

/**
 * Created by Gilbert on 8/23/2016.
 */
    public class Seed extends Controller{

    public static Result edit(Long id) {
        Seeds seeds = Seeds.Seedlist.byId(id);
        return ok(assignseeds.render("Assigning seeds orders",seeds));

}
    public static Result seedassigned(Long id) {
        Seeds seeds = new Seeds();
        seeds.Auto = id;
        seeds.status = "2";
        Ebean.update(seeds);
        DynamicForm dy = Form.form().bindFromRequest();
        String phone= dy.get("phone");
        String idnumber = dy.get("idnum");
        String Brand = dy.get("bname");
        String Quan  =dy.get("qua");
        String msg= "Dear customer your order has been approved please proceed making payment.Thankyou in  advance";

        Logger.info(" " +phone);
        Logger.info("   "+msg );

        String user = "username=" + "wilfred";
        String hash = "&Apikey=" + "a6a16092f97f5e3d683428cd008dba6de190cd42641ae095c3a82bd49ad4e4e2";
        String message = "&message=" + msg;
        String sender = "&from=" + "Cereal4Farmers";
        String numbers = "&to=" + phone;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cereal4f", "root", "willy9470");
            ResultSet rs;
            String sql= ("select * from seeds_stock where Brand=?");
            PreparedStatement ps = conn.prepareStatement(sql );
            ps.setString(1,Brand);
            rs = ps.executeQuery();
            while (rs.next() ) {
                String oldqu=  rs.getString("Quantity");
                int old = Integer.parseInt(oldqu);
                int now  = Integer.parseInt(Quan);
                int rem = old - now;
                Logger.info("this is old "+ old);
                Logger.info("this is new"+ now);
                Logger.info("this is rem"+ rem);
                String sold = String.valueOf(rem);
                Logger.info(sold);
                SqlUpdate sqlUpdate = Ebean.createSqlUpdate("UPDATE seeds_stock SET Quantity= :Quantity where Brand= :Brand");
                sqlUpdate.setParameter("Brand",Brand);
                sqlUpdate.setParameter("Quantity",rem);
                sqlUpdate.execute();




            }
            conn.close();
            //return ok(rs.toString());
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e);
        }










        return redirect("https://api.africastalking.com/restless/send?username=wilfred&Apikey=a6a16092f97f5e3d683428cd008dba6de190cd42641ae095c3a82bd49ad4e4e2"+numbers +message);


       // return redirect(routes.Application.ViewSeed());


    }

    public static Result seedrejected(Long id) {

       Seeds seeds = new Seeds();
        seeds.Auto = id;
        seeds.status = "3";
        Ebean.update(seeds);
        DynamicForm dy = Form.form().bindFromRequest();
        String phone= dy.get("phone");
        String idnumber = dy.get("idnum");
        String Brand = dy.get("bname");
        String Quan  =dy.get("qua");
        String msg= "Dear customer your order has been rejected please check on validity of you are landsize.Thankyou in  advance";

        Logger.info(" " +phone);
        Logger.info("   "+msg );

        String user = "username=" + "wilfred";
        String hash = "&Apikey=" + "a6a16092f97f5e3d683428cd008dba6de190cd42641ae095c3a82bd49ad4e4e2";
        String message = "&message=" + msg;
        String sender = "&from=" + "Cereal4Farmers";
        String numbers = "&to=" + phone;



        return redirect("https://api.africastalking.com/restless/send?username=wilfred&Apikey=a6a16092f97f5e3d683428cd008dba6de190cd42641ae095c3a82bd49ad4e4e2"+numbers +message);




      //  return redirect(routes.Application.ViewSeed());


    }
        public static Result viewSeed(){
        ObjectNode result;
        DynamicForm dy = Form.form().bindFromRequest();
            String username =  dy.get("username");
        String id  = dy.get("idnumber");
        String pass = dy.get("password");
        String  brandname= dy.get("brandname");
        String quantity= dy.get("quantity");
        String phonenumber= dy.get("phonenumber");
        String description= dy.get("description");
            String size =dy.get("size");
            String csize = dy.get("cultivation");
            String amount  = dy.get("amount");
     Logger.info(" " + brandname);
        Logger.info(" " + quantity);
        Logger.info(" " + phonenumber);
        Logger.info(" " + description);
        Logger.info(" " + id);
            Logger.info(" " + username);
        Logger.info(" " + pass);
            Logger.info(" " + size);
            Logger.info(" " + csize);
            Logger.info("" + amount);



        Seeds save1  = new Seeds();

            save1.UserName = username;
          save1.ID_Number =  id;
        //save1.PassWord = pass;
        save1.PhoneNumber = phonenumber;
        save1.SeedBrand = brandname;
        save1.Quantity = quantity;
        save1.Description=description;
         save1.LandSize =size;
         save1.Cultivation =csize;
            save1.Payment = amount;
            save1.status ="2";



        save1.save();
        result = Json.newObject();
        result.put("responseCode", "24");


        return ok(result);
    }


    public static Result payseed(Long id){
        ObjectNode result;
       Seeds seeds = new Seeds();
        DynamicForm dy = Form.form().bindFromRequest();
        String amount  = dy.get("Amount");

        Logger.info(" " + amount);
        seeds.Auto =id;
        seeds.Payment = amount;
         seeds.status="2";
        Ebean.update(seeds);
        result = Json.newObject();
        result.put("responseCode", "24");
 return ok(result);
 }

    public static Result slists() {

        System.out.println("seeds");
        Map<String, String[]> params = request().queryString();

        Integer iTotalRecords = Seeds.Seedlist.findRowCount();
        String filter = params.get("sSearch")[0];
        Integer pageSize = Integer.valueOf(params.get("iDisplayLength")[0]);
        Integer page = Integer.valueOf(params.get("iDisplayStart")[0]) / pageSize;

        /**
         * Get sorting order and column
         */
        String sortBy = "status";
        String order = params.get("sSortDir_0")[0];

        switch (Integer.valueOf(params.get("iSortCol_0")[0])) {
            case 0:
                sortBy = "status";
                break;
            case 1:
                sortBy = "Auto";
                break;
            case 2:
                sortBy = "PhoneNumber";
                break;
        }


         /*Get page to show from database
         It is important to set setFetchAhead to false, since it doesn't benefit a stateless application at all.
         */
        Page<Seeds> areaPage = Seeds.Seedlist.where(
                Expr.or(
                        Expr.ilike("status", "%" + filter + "%"),
                        Expr.or(
                                Expr.ilike("Auto", "%" + filter + "%"),
                                Expr.ilike("PhoneNumber", "%" + filter + "%")
                        )
                )
        )
              .orderBy(sortBy + " " + order + ", Auto " + order)
                .findPagingList(pageSize).setFetchAhead(false)
                .getPage(page);
        Integer iTotalDisplayRecords = areaPage.getTotalRowCount();


        ObjectNode result = Json.newObject();

        result.put("sEcho", Integer.valueOf(params.get("sEcho")[0]));
        result.put("iTotalRecords", iTotalRecords);
        result.put("iTotalDisplayRecords", iTotalDisplayRecords);

        ArrayNode anc = result.putArray("aaData");

        for (Seeds cc : areaPage.getList()) {
            ObjectNode row = Json.newObject();
            //    System.out.println("in data table fetch: " + cc.RoomName);
            row.put("ID_Number", cc.ID_Number);
            row.put("UserName", cc.UserName);
            //row.put("PassWord", cc.PassWord);
            row.put("PhoneNumber", cc.PhoneNumber);
            row.put("SeedBrand", cc.SeedBrand);
            row.put("Quantity", cc.Quantity);
            row.put("LandSize" ,cc.LandSize);
            row.put("Cultivation", cc.Cultivation);
            row.put("Description", cc.Description);
            row.put("status" ,cc.status);
            row.put("Payment" ,cc.Payment);
            row.put("Auto", cc.Auto);


            anc.add(row);
        }
        return ok(result);
    }





}




