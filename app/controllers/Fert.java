package controllers;

import com.avaje.ebean.*;

import java.sql.Connection;

import models.*;
import models.Fertilizer;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;
import play.db.DB;
import play.libs.Json;
import play.mvc.Controller;
import play.*;
import play.mvc.*;
import play.Logger;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Result;
import play.api.Play;

import play.api.*;


import scala.util.parsing.combinator.testing.Str;
import views.html.fertilizers.*;
import views.html.seeds.*;
import views.html.registration.*;


import static play.mvc.Results.ok;


import views.html.*;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

import views.html.fertilizers.*;
import views.html.seeds.*;
import views.html.registration.*;





/**
 * Created by Gilbert on 7/28/2016.
 */
public class Fert extends Controller {

    public static String brandname;
    public static String quantity;

    public static Result edit(Long id) {
        Fertilizer sfertilizer = Fertilizer.Fertlist.byId(id);
        return ok(assignfert.render("Assigning fertilizers orders", sfertilizer));
    }

    public static Result saveFert() {
        ObjectNode result;
        DynamicForm dy = Form.form().bindFromRequest();
        String intialq = null;
        String idn = dy.get("idnumber");
        String username = dy.get("username");
        //String secondname=  dy.get("secondname");
        String pass = dy.get("password");
        brandname = dy.get("fert");

        quantity = dy.get("quantity");
        String phonenumber = dy.get("phonenumber");
        String description = dy.get("description");
        String landsize = dy.get("size");
        String csize = dy.get("cultivation");
        String pay = dy.get("Amount");
        Logger.info(" " + brandname);
        Logger.info(" " + quantity);
        Logger.info(" " + phonenumber);
        Logger.info(" " + description);
        Logger.info(" " + idn);
        Logger.info(" " + username);
        Logger.info(" " + pay);
        Logger.info(" " + pass);
        Logger.info(" " + landsize);
        Logger.info(" " + csize);
        // String firstname =  Seeds.Seedlist.where().eq("status","3").findRowCount();
 Fertilizer Save2Db = new Fertilizer();
        //Save2Db.AutoNo =id;

        Save2Db.UserName = username;
        //Save2Db.SecondName = secondname;
        //Save2Db.PassWord = pass;
        Save2Db.ID_Number = idn;
        Save2Db.PhoneNumber = phonenumber;
        Save2Db.BrandName = brandname;
        Save2Db.Quantity = quantity;
        Save2Db.Description = description;
        Save2Db.LandSize = landsize;
        Save2Db.Cultivation = csize;
        Save2Db.Payment = pay;
        Save2Db.status = "1";
        Save2Db.wanna = "1";
        Save2Db.save();
        result = Json.newObject();

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cereal4f", "root", "willy9470");
            ResultSet rs;
            String sql= ("select * from stock_details where Brand=?");
            PreparedStatement ps = conn.prepareStatement(sql );
            ps.setString(1,brandname);
            rs = ps.executeQuery();
            while (rs.next() ) {
                String oldqu=  rs.getString("Quantity");
                int old = Integer.parseInt(oldqu);
                int now  = Integer.parseInt(quantity);
                int rem = old - now;
                Logger.info("this is old "+ old);
                Logger.info("this is new"+ now);
                Logger.info("this is rem"+ rem);
                String sold = String.valueOf(rem);
                Logger.info(sold);
          SqlUpdate sqlUpdate = Ebean.createSqlUpdate("UPDATE stock_details SET Quantity= :Quantity where Brand= :Brand");
                sqlUpdate.setParameter("Brand",brandname);
                sqlUpdate.setParameter("Quantity",rem);
                sqlUpdate.execute();




            }
            conn.close();
            //return ok(rs.toString());
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e);
        }


        result.put("responseCode", "24");

        return ok(result);
    }


    public static Result list() {
        System.out.println("fertilizers");
        Map<String, String[]> params = request().queryString();
        // Integer iTotalRecords1 = Addfields.FieldList.findRowCount();
        Integer iTotalRecords = Fertilizer.Fertlist.findRowCount();
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
                sortBy = "AutoNo";
                break;
            case 2:
                sortBy = "BrandName";
                break;
        }

        /**
         * Get page to show from database
         * It is important to set setFetchAhead to false, since it doesn't benefit a stateless application at all.
         */
        Page<Fertilizer> areaPage = Fertilizer.Fertlist.where(
                Expr.or(
                        Expr.ilike("status", "%" + filter + "%"),
                        Expr.or(
                                Expr.ilike("AutoNo", "%" + filter + "%"),
                                Expr.ilike("BrandName", "%" + filter + "%")
                        )
                )
        )
                .orderBy(sortBy + " " + order + ", AutoNo " + order)
                .findPagingList(pageSize).setFetchAhead(false)
                .getPage(page);

        Integer iTotalDisplayRecords = areaPage.getTotalRowCount();


        ObjectNode result = Json.newObject();

        result.put("sEcho", Integer.valueOf(params.get("sEcho")[0]));
        result.put("iTotalRecords", iTotalRecords);
        result.put("iTotalDisplayRecords", iTotalDisplayRecords);

        ArrayNode anc = result.putArray("aaData");

        for (Fertilizer cc : areaPage.getList()) {
            ObjectNode row = Json.newObject();
            //    System.out.println("in data table fetch: " + cc.RoomName);
            row.put("ID_Number", cc.ID_Number);
            row.put("UserName", cc.UserName);
            row.put("PhoneNumber", cc.PhoneNumber);
            row.put("AutoNo", cc.AutoNo);
            row.put("BrandName", cc.BrandName);
            row.put("Quantity", cc.Quantity);
            row.put("Description", cc.Description);
            row.put("LandSize", cc.LandSize);
            row.put("Cultivation", cc.Cultivation);
            row.put("Payment", cc.Payment);
            row.put("status", cc.status);
            row.put("wanna", cc.wanna);


            anc.add(row);
        }

        return ok(result);
    }

    public static Result deleteorders() {
        SqlUpdate sqlUpdate = Ebean.createSqlUpdate("DELETE FROM Fertilizer where ID_Number=?");
        sqlUpdate.execute();

        return ok();
    }


    public static Result statusassigned(Long id) {
        Fertilizer fertilizer = new Fertilizer();
       fertilizer.AutoNo = id;
        fertilizer.status ="2";
        Ebean.update(fertilizer);
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
            String sql= ("select * from stock_details where Brand=?");
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
                SqlUpdate sqlUpdate = Ebean.createSqlUpdate("UPDATE stock_details SET Quantity= :Quantity where Brand= :Brand");
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



        //return redirect(routes.Application.ViewFerti());


    }

    public static Result statusrejected(Long id) {
        Fertilizer fertilizer = new Fertilizer();
        fertilizer.AutoNo = id;
        fertilizer.status ="3";
        Ebean.update(fertilizer);
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

    }

    public static Result homePage() {
        int regp = Addfields.FieldList.where().eq("status", "1").findRowCount();
        int rega = Addfields.FieldList.where().eq("status", "2").findRowCount();
        int regr = Addfields.FieldList.where().eq("status", "3").findRowCount();
        int inqr = Inquiry.Inqlist.where().eq("status", "1").findRowCount();
        int inqa = Inquiry.Inqlist.where().eq("status", "2").findRowCount();

        int rem = 1000;
        int remS= 1000;

          return ok(test.render("Home", regp, rega, regr, inqr, inqa, rem, remS));
    }


}
