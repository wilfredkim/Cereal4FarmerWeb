package controllers;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Expr;
import com.avaje.ebean.Page;
import models.Fertilizer;
import models.MFarmDetails;
import models.RegisterAdmin;
import models.StockDetails;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;
import play.Logger;
import play.data.DynamicForm;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import  views.html.farmdetails.*;
import  views.*;

import javax.swing.*;
import java.util.Map;

/**
 * Created by Gilbert on 9/28/2016.
 */
public class FarmDetails extends Controller {

    public static Result addfarm() {
        return ok(addfarm.render("Farm Details"));
    }
    public static Result viewfarm() {
        return ok(viewfarm.render("Farm Details"));
    }

    public static  Result addfarmd(){
        Logger.info("In Receiving Details");
        ObjectNode result;
        DynamicForm dy = Form.form().bindFromRequest();
        String Fullname = dy.get("fname");
        String Address = dy.get("address");
        String Email = dy.get("email");
        String Id = dy.get("id");
        String Town = dy.get("town");
        String location = dy.get("location");
        String total = dy.get("tsize");
        String culti = dy.get("csize");

        MFarmDetails user = MFarmDetails.authenticateUser(Id);
        // Logger.info(Id);
        if (user != null) {
            result = Json.newObject();
            //result.put( "Username already exists");
            JOptionPane.showMessageDialog(null, "User already exists");


        }   else {


            MFarmDetails mFarmDetails = new MFarmDetails();
            mFarmDetails.FullName= Fullname;
            mFarmDetails.IDNumber = Id;
            mFarmDetails.Address = Address;
            mFarmDetails.Email = Email;
            mFarmDetails.Town = Town;
            mFarmDetails.Location = location;
            mFarmDetails.Land_Size= total;
            mFarmDetails.Cultivation_Size = culti;

            mFarmDetails.save();
 }
        return redirect(routes.FarmDetails.viewfarm());
    }

    public static Result flist() {
        //System.out.println("fertilizers");
        Map<String, String[]> params = request().queryString();
        // Integer iTotalRecords1 = Addfields.FieldList.findRowCount();
        Integer iTotalRecords = MFarmDetails.farmlist.findRowCount();
        String filter = params.get("sSearch")[0];
        Integer pageSize = Integer.valueOf(params.get("iDisplayLength")[0]);
        Integer page = Integer.valueOf(params.get("iDisplayStart")[0]) / pageSize;

        /**
         * Get sorting order and column
         */
        String sortBy = "IDNumber";
        String order = params.get("sSortDir_0")[0];

        switch (Integer.valueOf(params.get("iSortCol_0")[0])) {
            case 0:
                sortBy = "IDNumber";
                break;
            case 1:
                sortBy = "num";
                break;
            case 2:
                sortBy = "FullName";
                break;
        }

        /**
         * Get page to show from database
         * It is important to set setFetchAhead to false, since it doesn't benefit a stateless application at all.
         */
        Page<MFarmDetails> areaPage = MFarmDetails.farmlist.where(
                Expr.or(
                        Expr.ilike("IDNumber", "%" + filter + "%"),
                        Expr.or(
                                Expr.ilike("num", "%" + filter + "%"),
                                Expr.ilike("FullName", "%" + filter + "%")
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

        for (MFarmDetails cc : areaPage.getList()) {
            ObjectNode row = Json.newObject();
            //    System.out.println("in data table fetch: " + cc.RoomName);
            row.put("ID_Number", cc.IDNumber);
            row.put("FullName", cc.FullName);
            row.put("Address", cc.Address);
            row.put("num", cc.num);
            row.put("Town", cc.Town);
            row.put("Location", cc.Location);

            row.put("LandSize" ,cc.Land_Size);
            row.put("Cultivation", cc.Cultivation_Size);




            anc.add(row);
        }

        return ok(result);
    }
    public static Result renderupdate(Long id) {
        MFarmDetails update =MFarmDetails.farmlist.byId(id);
        return ok(updatefarm.render("updating  ",update));
    }
    public static Result update(Long id) {
      /*DynamicForm dy = Form.form().bindFromRequest();
      String brand= dy.get("qua");
      int qua = Integer.parseInt(brand);
      StockDetails upda= new StockDetails();
      upda.Quantity=m;
      int dbq = Integer.parseInt(m);
*/

        MFarmDetails mFarmDetails = new MFarmDetails();
        mFarmDetails.num= id;

        mFarmDetails.Location=Form.form().bindFromRequest().get("loc");
        mFarmDetails.Land_Size=Form.form().bindFromRequest().get("size");
        mFarmDetails.Cultivation_Size=Form.form().bindFromRequest().get("csize");

        Ebean.update(mFarmDetails);


        return redirect(routes.FarmDetails.viewfarm());


    }
}
