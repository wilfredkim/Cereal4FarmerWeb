package controllers;



import com.avaje.ebean.Ebean;
import models.Fertilizer;
import models.Inquiry;
import org.codehaus.jackson.map.annotate.JacksonInject;
import play.mvc.Controller;
import scala.util.control.Exception;
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


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.util.Map;

import java.util.HashMap;

/**
 * Created by Gilbert on 9/3/2016.
 */
public class Sendingsms extends Controller {

   public static Result sendsms(Long id){
       Inquiry inquire = new Inquiry();
       inquire.num = id;
       inquire.status ="2";
       Ebean.update(inquire);
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
