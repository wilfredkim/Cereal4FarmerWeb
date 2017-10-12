package controllers;

import models.Addfields;
import models.RegisterAdmin;
import org.codehaus.jackson.node.ObjectNode;
import play.Logger;
import play.data.DynamicForm;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import  play.mvc.*;
import views.html.*;
import views.*;
import views.html.fertilizers.*;
import views.html.seeds.*;
import views.html.registration.*;
import views.html.login.*;

import javax.swing.*;


/**
 * Created by Gilbert on 9/27/2016.
 */
public class Login  extends Controller{

    public static Result loginadmin() {
        return ok(loginadmin.render("Login"));
    }
    public static Result  regadmin(){
        return ok(registeradmin.render("Register"));
    }
    public static Result pass(){
        return ok(forgotpass.render("Reset password"));
    }
   /* public  static  Result mainc () {
        return ok(maincontent.render("Home"));
    }*/
    public static  Result regAdmin() {
        Logger.info("In Receiving Details");
        ObjectNode result;
        DynamicForm dy = Form.form().bindFromRequest();
        String Fullname = dy.get("fname");
        String Email = dy.get("email");
        String gender = dy.get("option-yes");
        String Username = dy.get("username");
        String password = dy.get("pass");
        String repasswoed = dy.get("repass");

        RegisterAdmin user = RegisterAdmin.authenticateUser(Username);
        // Logger.info(Id);
        if (user != null) {
            result = Json.newObject();
            //result.put( "Username already exists");


        } else if(password!=repasswoed){

           // result.put("Password does not match");


        }




        else {


            RegisterAdmin registerAdmin = new RegisterAdmin();
            registerAdmin.FullName= Fullname;
            registerAdmin.Email = Email;
            registerAdmin.Gender =gender;
            registerAdmin.Username = Username;
            registerAdmin.Password = password;
            registerAdmin.save();



            return redirect(routes.Login.loginadmin());

        }
       return redirect(routes.Login.loginadmin());
    }

    public static Result login(){
        Logger.info("In Receiving login Details");
        ObjectNode result;
        DynamicForm dy = Form.form().bindFromRequest();

        String username= dy.get("user");

        String password = dy.get("pass");



        RegisterAdmin user=RegisterAdmin.authenticateUserlogin(username,password);


        if(user!= null) {
            result = Json.newObject();
            result.put("responseCode", "24");
            //return redirect(routes.Login.mainc());
            //result.put("responseCode", "24");
        }else{

            JOptionPane.showMessageDialog(null, "Login fails incorrect credetials");
        }




return ok();
    }



    }
