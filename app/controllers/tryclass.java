package controllers;

import play.api.db.DB;
import play.mvc.Action;
import play.mvc.Controller;
import java.sql.Connection;

import com.avaje.ebean.Expr;
import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.Page;
import models.*;
import models.Fertilizer;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;
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
 * Created by Gilbert on 10/24/2016.
 */
public class tryclass extends Controller {
   public static Result getlocation(){
   // Connection conn = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cereal4f", "root", "willy9470");
            ResultSet rs;
            PreparedStatement ps = conn.prepareStatement("select city from zips where city=? limit 1");
            ps.setString(1,"Dallas");
            rs = ps.executeQuery();
            while (rs.next() ) {
                rs.getString("city");
            }

            conn.close();
            return ok(rs.toString());
        } catch (Exception e) {
            System.err.println("Got an exception! ");
        }


        return ok();
    }
    }

