package controllers;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.SqlUpdate;
import models.*;
import org.codehaus.jackson.node.ObjectNode;
import play.*;
import play.libs.Json;
import play.mvc.*;
import play.Logger;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Result;
import scala.util.parsing.combinator.testing.Str;
import views.html.fertilizers.*;
import views.html.seeds.*;
import views.html.registration.*;

import static play.mvc.Results.ok;


import views.html.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

/**
 * Created by Gilbert on 7/21/2016.
 */
public class mobileRe extends Controller {
    public static  String idn;
    public static Result regField() {
        Logger.info("In Receiving Details");
        ObjectNode result;
        DynamicForm dy = Form.form().bindFromRequest();
        String Firstname = dy.get("firstname");
        String Secondname = dy.get("secondname");
        String phone = dy.get("phonenumber");
        String Email = dy.get("email");
        String Id = dy.get("idnumber");
        String Block = dy.get("blocknumber");
        String Landsize = dy.get("landsize");
        String cal = dy.get("cultivation");
        String password = dy.get("password");
        Addfields user = Addfields.authenticateUser(Id);
        Logger.info(Id);
        String land;
        if (user != null) {
            result = Json.newObject();
            //JOptionPane.showMessageDialog(null, "Please fill the details");
            result.put("responseCode", "100");
            MFarmDetails mFarmDetails = new MFarmDetails();
            land = mFarmDetails.Land_Size;
            /*int lands = Integer.ParseInt(Landsize);
            int cals = Integer.ParseInt(cal);*/


        }


        else{


            Addfields xx = new Addfields();
            xx.status = "1";
            xx.First_name = Firstname;
            xx.Second_name = Secondname;
            xx.Address = phone;
            xx.Email = Email;
            xx.Id = Id;
            xx.Block_number = Block;
            xx.Land_Size = Landsize;
            xx.Cultivation_Size = cal;
            xx.Password = password;
            xx.save();
            result = Json.newObject();
            result.put("responseCode", "24");
        }
        return ok(result);


    }
    public static Result login(){
        Logger.info("In Receiving login Details");
        ObjectNode result;
        DynamicForm dy = Form.form().bindFromRequest();

        String Id= dy.get("idnumber");

        String password = dy.get("password");



        Addfields user=Addfields.authenticateUserlogin(Id,password);
        Logger.info(Id);
        Logger.info(password);

        if(user!=null) {
            result = Json.newObject();
            result.put("responseCode", "24");
            //result.put("responseCode", "24");
      }else{

            result = Json.newObject();
            result.put("responseCode", "100");
        }
        return ok(result);




    }
    public static Result checkstatus(){
        Logger.info("in checking status");

        ObjectNode result;
        DynamicForm dy = Form.form().bindFromRequest();

        String Id= dy.get("idnumber");

        String status="2";
 Addfields user=Addfields.authenticates(status,Id);
   if(user!=null) {
            result = Json.newObject();
            result.put("responseCode", "50");
        }else{

            result = Json.newObject();
            result.put("responseCode", "120");
        }
        return ok(result);




    }


    public static Result existfert(){

        ObjectNode result;
        DynamicForm dy = Form.form().bindFromRequest();

        String Id= dy.get("idnumber");

        //String password = dy.get("password");



        //Addfields user=Addfields.authenticateUserlogin(Id,password);
        Fertilizer fertilizer = Fertilizer.authenticateorder(Id);
        Logger.info(Id);
        //Logger.info(password);

        if(fertilizer!=null) {
            result = Json.newObject();
            result.put("responseCode", "120");
            //result.put("responseCode", "24");
        }else{

            result = Json.newObject();
            result.put("responseCode", "24");
        }
        return ok(result);




    }
    public static Result existseed(){

        ObjectNode result;
        DynamicForm dy = Form.form().bindFromRequest();

        String Id= dy.get("idnumber");

        //String password = dy.get("password");



        //Addfields user=Addfields.authenticateUserlogin(Id,password);
        Seeds seeds = Seeds.authenticateorder(Id);
        Logger.info(Id);
        //Logger.info(password);

        if(seeds!=null) {
            result = Json.newObject();
            result.put("responseCode", "120");
            //result.put("responseCode", "24");
        }else{

            result = Json.newObject();
            result.put("responseCode", "24");
        }
        return ok(result);

  }
    public static Result compareland(){

        Logger.info("receiving the id  ");
        ObjectNode result ;
        Logger.info("This function");
        DynamicForm dy = Form.form().bindFromRequest();
         idn = dy.get("idnumber");
        String pass = dy.get("password");
        String  brandname= dy.get("fert");
        String quantity= dy.get("quantity");
        String phonenumber= dy.get("phonenumber");
        String description= dy.get("description");
        String landsize = dy.get("size");
        String csize = dy.get("cultivation");
        Logger.info( brandname);
        Logger.info( quantity);
        Logger.info( phonenumber);
        Logger.info(description);
        Logger.info( idn);
        Logger.info( pass);
        Logger.info( landsize);

        result = Json.newObject();
  try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cereal4f", "root", "willy9470");
            ResultSet rs;
            PreparedStatement ps = conn.prepareStatement("select * from Addfields where Id=?" );
            ps.setString(1,idn);
            rs = ps.executeQuery();
            while (rs.next() ) {
                String land=  rs.getString("Land_Size");
                String size = rs.getString("Cultivation_Size");
                int old = Integer.parseInt(size);
                int now  = Integer.parseInt(csize);
                if(old==now){
                    //result.put("responseCode", "24");
                    try {
                        String sql= ("select * from Fertilizer where BrandName=?");
                         ps = conn.prepareStatement(sql);

                        ps.setString(1,brandname);
                        rs = ps.executeQuery();
                        while (rs.next() ) {
                            String qua=  rs.getString("Quantity");
                            int quantit = Integer.parseInt(qua);

                            if(quantit<=0){
                                result.put("responseCode", "48");
                            }
                            else{
                                result.put("responseCode", "24");
                            }
                        }
                        conn.close();
                        //return ok(rs.toString());
                    } catch (Exception e) {
                        System.err.println("Got an exception! ");
                    }

                }else{
                    result.put("responseCode", "100");
                }
            }
            conn.close();
            //return ok(rs.toString());
        } catch (Exception e) {
            System.err.println("Got an exception! ");
        }



        return ok(result);
    }
    public static Result getland(){
        Logger.info("returning the sizes");
        Logger.info("getting sizes of land");

return ok(Json.toJson(Addfields.findAll()));
    }




}
