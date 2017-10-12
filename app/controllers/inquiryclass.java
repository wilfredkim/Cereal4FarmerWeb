package controllers;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Expr;
import com.avaje.ebean.Page;
import models.Addfields;
import models.Fertilizer;
import models.Inquiry;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;
import play.Logger;
import play.data.DynamicForm;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.Map;
import views.html.*;
import views.*;
import views.html.fertilizers.*;
import views.html.seeds.*;
import views.html.registration.*;
import views.html.inquiry.*;

import views.html.extras.*;

/**
 * Created by Gilbert on 9/7/2016.
 */

public class inquiryclass extends Controller {
    public static Result renderview (){
        return ok(view.render("View Inquiry"));

    }
    public static Result answer(Long id) {
        Inquiry inquire =Inquiry.Inqlist.byId(id);
        return ok(answer.render("Answer", inquire));

    }
    public static Result Postanswer(Long id) {


        Inquiry inquire = new Inquiry();
        inquire.num= id;
        inquire.status ="2";
        Ebean.update(inquire);


        return redirect(routes.inquiryclass.renderview());


    }

    public static Result inqrec(){
        Logger.info("receives the inquiry");
        ObjectNode result;
        DynamicForm dy = Form.form().bindFromRequest();
        String Firstname= dy.get("name");
        String message = dy.get("message");
        String phone= dy.get("phone");
        String date= dy.get("date");
        String Id= dy.get("id");

        Inquiry inquire = new Inquiry();
            inquire.status ="1";
            inquire.Name = Firstname;
            inquire.ID_Number = Id;
            inquire.PhoneNumber =phone;
           inquire.Date = date;
           inquire.Message = message;
        inquire.save();;



            result=Json.newObject();
            result.put("responseCode", "24");

        return ok(result);
  }
    public static Result list() {

        Map<String, String[]> params = request().queryString();

        Integer iTotalRecords = Inquiry.Inqlist.findRowCount();
        String filter = params.get("sSearch")[0];
        Integer pageSize = Integer.valueOf(params.get("iDisplayLength")[0]);
        Integer page = Integer.valueOf(params.get("iDisplayStart")[0]) / pageSize;

        /**
         * Get sorting order and column
         */
        String sortBy = "ID_Number";
        String order = params.get("sSortDir_0")[0];

        switch (Integer.valueOf(params.get("iSortCol_0")[0])) {
            case 0:
                sortBy = "ID_Number";
                break;
            case 1:
                sortBy = "num";
                break;
            case 2:
                sortBy = "PhoneNumber";
                break;
        }

        /**
         * Get page to show from database
         * It is important to set setFetchAhead to false, since it doesn't benefit a stateless application at all.
         */
        Page<Inquiry> areaPage = Inquiry.Inqlist.where(
                Expr.or(
                        Expr.ilike("ID_Number", "%" + filter + "%"),
                        Expr.or(
                                Expr.ilike("num", "%" + filter + "%"),
                                Expr.ilike("PhoneNumber", "%" + filter + "%")
                        )
                )
        )
                .orderBy(sortBy + " " + order + ", num " + order)
                .findPagingList(pageSize).setFetchAhead(false)
                .getPage(page);

        Integer iTotalDisplayRecords = areaPage.getTotalRowCount();


        ObjectNode result = Json.newObject();

        result.put("sEcho", Integer.valueOf(params.get("sEcho")[0]));
        result.put("iTotalRecords", iTotalRecords);
        result.put("iTotalDisplayRecords", iTotalDisplayRecords);

        ArrayNode anc = result.putArray("aaData");

        for (Inquiry cc : areaPage.getList()) {
            ObjectNode row = Json.newObject();
            //    System.out.println("in data table fetch: " + cc.RoomName);
            row.put("Name",cc.Name);
            row.put("ID_Number", cc.ID_Number);
            row.put("PhoneNumber", cc.PhoneNumber);
            row.put("num", cc.num);
            row.put("Date", cc.Date);
            row.put("Message", cc.Message);

            row.put("status" ,cc.status);


            anc.add(row);
        }

        return ok(result);
    }


}
