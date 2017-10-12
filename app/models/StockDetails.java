package models;

import play.data.validation.Constraints;
import play.db.ebean.Model;
import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.Constraint;
import java.util.List;
import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.Constraint;
import java.util.List;

/**

 * Created by Gilbert on 8/26/2016.
 */
@Entity
public class StockDetails extends  Model{
      @Id
    public long num;

    @Constraints.Required
    public String Brand;

    @Constraints.Required
    public String Quantity;

    @Constraints.Required
    public String Totalp;


    @Constraints.Required
    public String Price;


    public static Model.Finder<Long,StockDetails> Stocklist = new Model.Finder<Long, StockDetails>(Long.class, StockDetails.class);

    public static List<StockDetails> findAll() { return Stocklist.where().findList();
    }


    public static List<StockDetails>findByCategoryId(String id){

        return  Stocklist.where().eq("Brand",id).findList();
    }
    public static  StockDetails   authenticateminus(String brand) {
        return StockDetails.Stocklist.where().eq(" Brand", brand).findUnique();



    }



}
