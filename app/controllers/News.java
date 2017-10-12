package controllers;

import models.*;
import play.mvc.Controller;
import play.mvc.Result;
import com.avaje.ebean.Ebean;
import com.avaje.ebean.Expr;
import com.avaje.ebean.Page;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;
import play.*;
import play.libs.Json;
import play.mvc.*;
import models.StockDetails;
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
import java.util.Map;
import play.data.Form;
import play.libs.Json;
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


 /* Created by Gilbert on 8/26/2016.
 */
public class News extends Controller {

     public static Result postnew() {
         return ok(postnews.render("News"));
     }

     public static Result newsp() {
         Logger.info("");
         //ObjectNode result;
         DynamicForm dy = Form.form().bindFromRequest();
         String event =dy.get("event");
         String news = dy.get("textarea");
         String date = dy.get("date");

         NewsPost st = new NewsPost();
         st.Event = event;
         st.News = news;
         st.Date = date;
         st.save();


         return redirect(routes.Fert.homePage());
     }


     public static Result getNews(){
         Logger.info("getting news");
         return ok(Json.toJson(NewsPost.findAll()));
     }
 }