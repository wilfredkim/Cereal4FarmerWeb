package controllers;


import com.avaje.ebean.Ebean;
import com.avaje.ebean.Expr;
import com.avaje.ebean.Page;
import models.*;
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

/**
 * Created by Gilbert on 8/26/2016.
 */
public class stockdt extends Controller {
    public  static  Result viewstock()
    {
        return ok(Viewstock.render("view fertilizers stock"));
    }



    public static Result stockfert(){
        Logger.info("registration arrives");
        //ObjectNode result;
        DynamicForm dy = Form.form().bindFromRequest();
        String brand= dy.get("bname");
        String quantity= dy.get("quant");
        //String Tprice= dy.get("tprice");
        String price= dy.get("price");


        StockDetails  st = new StockDetails();
        st.Brand =brand;
        st.Quantity =quantity;
        //st.Totalp = Tprice;
        st.Price = price;
        st.save();


        return redirect(routes.stockdt.viewstock());
    }
        public static Result edit(Long id) {
        StockDetails update =StockDetails.Stocklist.byId(id);
        return ok(updatestock.render("updating fertilizers ",update));
    }
    public static Result updatefert(Long id){
        StockDetails update1 =new StockDetails();
        update1.num =id;
        update1.Brand = Form.form().bindFromRequest().get("brand");
        update1.Quantity=Form.form().bindFromRequest().get("qua");
       // update1.Totalp=Form.form().bindFromRequest().get("tprice");
        update1.Price=Form.form().bindFromRequest().get("price");
        Ebean.update(update1);



        return redirect(routes.stockdt.viewstock());



    }


///seeds stock
public  static  Result viewstockseed(){
    return ok(viewstockseed.render("view seeds stock"));
}

    public static Result stockseed(){
        Logger.info("ss");
        //ObjectNode result;
        DynamicForm dy = Form.form().bindFromRequest();
        String brand= dy.get("bname");
        String quantity= dy.get("quant");
        //String Tprice= dy.get("tprice");
        String price= dy.get("price");


        SeedsStock st = new SeedsStock();
        st.Brand =brand;
        st.Quantity =quantity;
        //st.Totalp = Tprice;
        st.Price = price;
        st.save();


        return redirect(routes.stockdt.viewstockseed());
    }
    public static Result editseedstock(Long id) {

        SeedsStock update = SeedsStock.Seedstocklist.byId(id);
        return ok(updatestockseed.render("updating seeds ",update));
    }
    public static Result updateseed(Long id){
        SeedsStock update1 =new SeedsStock();
        update1.num =id;
        update1.Brand = Form.form().bindFromRequest().get("brand");
        update1.Quantity=Form.form().bindFromRequest().get("qua");
        //update1.Totalp=Form.form().bindFromRequest().get("tprice");
        update1.Price=Form.form().bindFromRequest().get("price");
        Ebean.update(update1);


        return redirect(routes.stockdt.viewstockseed());



    }
    //findall to send back to android
    public static Result getSeed(){
        Logger.info("getting seed menu");
        return ok(Json.toJson(SeedsStock.findAll()));
    }
    public static Result getFert(){
        Logger.info("getting fert menu");
        return ok(Json.toJson(StockDetails.findAll()));
    }


    public static Result stockfertlist() {

        System.out.println("stocks");
        Map<String, String[]> params = request().queryString();

        Integer iTotalRecords = StockDetails.Stocklist.findRowCount();
        String filter = params.get("sSearch")[0];
        Integer pageSize = Integer.valueOf(params.get("iDisplayLength")[0]);
        Integer page = Integer.valueOf(params.get("iDisplayStart")[0]) / pageSize;

        /**
         * Get sorting order and column
         */
        String sortBy = "Brand";
        String order = params.get("sSortDir_0")[0];

        switch (Integer.valueOf(params.get("iSortCol_0")[0])) {
            case 0:
                sortBy = "Brand";
                break;
            case 1:
                sortBy = "num";
                break;
            case 2:
                sortBy = "Quantity";
                break;
        }


         /*Get page to show from database
         It is important to set setFetchAhead to false, since it doesn't benefit a stateless application at all.
         */
        Page<StockDetails> areaPage = StockDetails.Stocklist.where(
                Expr.or(
                        Expr.ilike("Brand", "%" + filter + "%"),
                        Expr.or(
                                Expr.ilike("num", "%" + filter + "%"),
                                Expr.ilike("Quantity", "%" + filter + "%")
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

        for (StockDetails cc : areaPage.getList()) {
            ObjectNode row = Json.newObject();
            //    System.out.println("in data table fetch: " + cc.RoomName);
            row.put("Brand", cc.Brand);
            row.put("Quantity", cc.Quantity);
            //row.put("Totalp", cc.Totalp);
            row.put("Price", cc.Price);
            row.put("num", cc.num);


            anc.add(row);
        }
        return ok(result);
    }


    public static Result stockseedlist() {

        System.out.println("stocks");
        Map<String, String[]> params = request().queryString();

        Integer iTotalRecords = SeedsStock.Seedstocklist.findRowCount();
        String filter = params.get("sSearch")[0];
        Integer pageSize = Integer.valueOf(params.get("iDisplayLength")[0]);
        Integer page = Integer.valueOf(params.get("iDisplayStart")[0]) / pageSize;

        /**
         * Get sorting order and column
         */
        String sortBy = "Brand";
        String order = params.get("sSortDir_0")[0];

        switch (Integer.valueOf(params.get("iSortCol_0")[0])) {
            case 0:
                sortBy = "Brand";
                break;
            case 1:
                sortBy = "num";
                break;
            case 2:
                sortBy = "Quantity";
                break;
        }


         /*Get page to show from database
         It is important to set setFetchAhead to false, since it doesn't benefit a stateless application at all.
         */
        Page<SeedsStock> areaPage = SeedsStock.Seedstocklist.where(
                Expr.or(
                        Expr.ilike("Brand", "%" + filter + "%"),
                        Expr.or(
                                Expr.ilike("num", "%" + filter + "%"),
                                Expr.ilike("Quantity", "%" + filter + "%")
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

        for (SeedsStock cc : areaPage.getList()) {
            ObjectNode row = Json.newObject();
            //    System.out.println("in data table fetch: " + cc.RoomName);
            row.put("Brand", cc.Brand);
            row.put("Quantity", cc.Quantity);
            //row.put("Totalp", cc.Totalp);
            row.put("Price", cc.Price);
            row.put("num", cc.num);


            anc.add(row);
        }
        return ok(result);
    }





}
