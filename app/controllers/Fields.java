package controllers;

import com.avaje.ebean.Page;
import models.Addfields;
import play.*;
import play.libs.Json;
import play.mvc.*;
import play.Logger;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Result;
import play.mvc.Controller;
import com.avaje.ebean.Ebean;
import  com.avaje.ebean.Expr;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;
import views.html.fertilizers.*;
import views.html.seeds.*;
import views.html.registration.*;


import static play.mvc.Results.ok;

import views.html.*;


import java.util.Map;

/**
 * Created by Gilbert on 7/17/2016.
 */
public class Fields extends Controller{
    public static Result regField(){
        Logger.info("registration arrives");
        ObjectNode result;
        DynamicForm dy = Form.form().bindFromRequest();
        String Firstname= dy.get("firstname");
        String Secondname= dy.get("secondname");
        String phone= dy.get("phonenumber");
        String Email= dy.get("email");
        String Id= dy.get("idnumber");
        String Block= dy.get("blocknumber");
        String Landsize= dy.get("landsize");
        String cal= dy.get("cultivation");
        String password = dy.get("password");


        Addfields xx = new Addfields();
        xx.status ="1";
        xx.First_name = Firstname;
        xx.Second_name = Secondname;
        xx.Address = phone;
        xx.Email =Email;
        xx.Id = Id;
        xx.Block_number =Block;
        xx.Land_Size =Landsize;
        xx.Cultivation_Size = cal;
        xx.Password = password;

        xx.save();


        return redirect(routes.Application.farmnew());
    }

    public static Result list() {

        System.out.println("registration");
        Map<String, String[]> params = request().queryString();

        Integer iTotalRecords = Addfields.FieldList.findRowCount();
        String filter = params.get("sSearch")[0];
        Integer pageSize = Integer.valueOf(params.get("iDisplayLength")[0]);
        Integer page = Integer.valueOf(params.get("iDisplayStart")[0]) / pageSize;

        /**
         * Get sorting order and column
         */
        String sortBy = "First_name";
        String order = params.get("sSortDir_0")[0];

        switch (Integer.valueOf(params.get("iSortCol_0")[0])) {
            case 0:
                sortBy = "First_name";
                break;
            case 1:
                sortBy = "Ida";
                break;
            case 2:
                sortBy = "Email";
                break;
        }

        /**
         * Get page to show from database
         * It is important to set setFetchAhead to false, since it doesn't benefit a stateless application at all.
         */
        Page<Addfields> areaPage = Addfields.FieldList.where(
                Expr.or(
                        Expr.ilike("First_name", "%" + filter + "%"),
                        Expr.or(
                                Expr.ilike("Ida", "%" + filter + "%"),
                                Expr.ilike("Email", "%" + filter + "%")
                        )
                )
        )
                .orderBy(sortBy + " " + order + ", Ida " + order)
                .findPagingList(pageSize).setFetchAhead(false)
                .getPage(page);

        Integer iTotalDisplayRecords = areaPage.getTotalRowCount();


        ObjectNode result = Json.newObject();

        result.put("sEcho", Integer.valueOf(params.get("sEcho")[0]));
        result.put("iTotalRecords", iTotalRecords);
        result.put("iTotalDisplayRecords", iTotalDisplayRecords);

        ArrayNode anc = result.putArray("aaData");

        for (Addfields cc : areaPage.getList()) {
            ObjectNode row = Json.newObject();
            //    System.out.println("in data table fetch: " + cc.RoomName);
            row.put("First_name", cc.First_name);
            row.put("Second_name", cc.Second_name);
            row.put("Ida", cc.Ida);
            row.put("Address", cc.Address);
            row.put("Email", cc.Email);
            row.put("Password", cc.Password);
            row.put("Block_number" ,cc.Block_number);
            row.put("Id", cc.Id);
            row.put("Land_Size",cc.Land_Size);
            row.put("Cultivation_Size" , cc.Cultivation_Size);
            row.put("status",cc.status);

            anc.add(row);
        }

        return ok(result);
    }
    public static Result edit(Long id) {
        Addfields xx= Addfields.FieldList.byId(id);
        return ok(editreg.render("EditUser",xx));

    }
    public static Result Regassigned(Long id){
        Addfields xx=new Addfields();
        xx.Ida=id;
       xx.status="2";
        Ebean.update(xx);
        DynamicForm dy = Form.form().bindFromRequest();
        String phone= dy.get("phone");
        String msg= dy.get("answer");

        Logger.info(" " +phone);
        Logger.info("   "+msg );

        String user = "username=" + "wilfred";
        String hash = "&Apikey=" + "a6a16092f97f5e3d683428cd008dba6de190cd42641ae095c3a82bd49ad4e4e2";
        String message = "&message=" + msg;
        String sender = "&from=" + "Cereal4Farmers";
        String numbers = "&to=" + phone;




        return redirect("https://api.africastalking.com/restless/send?username=wilfred&Apikey=a6a16092f97f5e3d683428cd008dba6de190cd42641ae095c3a82bd49ad4e4e2"+numbers +message);


    }
    public static Result Regrejected(Long id){
        Addfields xx=new Addfields();
        xx.Ida=id;
        xx.status="3";
        Ebean.update(xx);


        DynamicForm dy = Form.form().bindFromRequest();
        String phone= dy.get("phone");
        String msg= dy.get("answer");

        Logger.info(" " +phone);
        Logger.info("   "+msg );

        String user = "username=" + "wilfred";
        String hash = "&Apikey=" + "a6a16092f97f5e3d683428cd008dba6de190cd42641ae095c3a82bd49ad4e4e2";
        String message = "&message=" + msg;
        String sender = "&from=" + "Cereal4Farmers";
        String numbers = "&to=" + phone;




        return redirect("https://api.africastalking.com/restless/send?username=wilfred&Apikey=a6a16092f97f5e3d683428cd008dba6de190cd42641ae095c3a82bd49ad4e4e2"+numbers +message);




    }






}
