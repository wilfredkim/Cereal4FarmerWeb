package controllers;

import play.*;
import play.mvc.*;
import play.Logger;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Result;
import views.html.*;
import views.*;
import views.html.fertilizers.*;
import views.html.seeds.*;
import views.html.registration.*;

import views.html.extras.*;

import static play.mvc.Results.ok;




public class Application extends Controller {
  
    public static Result index() {
        return ok(index.render("Your new application is ready."));
    }
   /* public  static Result test(){
        return ok(test.render("Cereal4farmers"));
    }*/
    public static  Result formaction(){
        DynamicForm dynamicForm = Form.form().bindFromRequest();
        String email = dynamicForm.get("email");
        Logger.info("Your email is " + email);
        return  ok();
    }
    public static Result farmnew (){
        return ok(farmnew.render("View Registration"));

    }
    public static Result try1(){
        return ok(trysms.render("ccdsa"));
    }


    public static Result field () {

        return ok(addfields.render("Add new"));
    }
    //fertilizers
    public static Result ViewFerti()
    {

        return ok(ViewFerti.render("Fertilizers Order"));
    }
    //seeds
    public static Result ViewSeed()
    {

        return ok(ViewSeed.render("Seeds Order"));
    }
    public  static  Result stock()
    {
        return  ok(stock.render("Stock"));
    }


}
