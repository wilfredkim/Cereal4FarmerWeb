package models;

import play.api.db.DB;
import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.Constraint;
import java.sql.Connection;
import java.util.List;
/**
 * Created by Gilbert on 7/28/2016.
 */
@Entity
public class Fertilizer extends Model{

    @Id
    public long AutoNo;

    @Constraints.Required
    public String ID_Number;

    @Constraints.Required
    public String UserName;




    @Constraints.Required
    public String PhoneNumber;


    @Constraints.Required
    public String BrandName;

    @Constraints.Required
    public String Quantity;
    @Constraints.Required
    public String Description;
    @Constraints.Required
    public String status;
    @Constraints.Required
    public String LandSize;
    @Constraints.Required
    public String Cultivation;
    @Constraints.Required
    public String Payment;
    @Constraints.Required
    public String wanna;










    public static Model.Finder<Long, Fertilizer> Fertlist = new Model.Finder<Long, Fertilizer>(Long.class, Fertilizer.class);

    public static List<Fertilizer> findAll() {
        return Fertlist.where().findList();
    }
    public static List<Fertilizer>findpendings()
    {

        return Fertlist.where().eq("status", "1").findList();
    }


    public static Fertilizer
    authenticateq(String status,String brand) {
        return Fertilizer.Fertlist.where().eq("status",status).eq("BrandName",brand).findUnique();


    }


    public static Fertilizer authenticateorder(String Id) {
        return Fertilizer.Fertlist.where().eq("ID_Number", Id).findUnique();


    }
  /*  public static List<Fertilizer>findaccepted()
    {
        return Fertlist.where().eq("status", "2").findList();
    }
    public static List<Fertilizer>findrejected()
    {
        return Fertlist.where().eq("status", "3").findList();
    }
*/

}
