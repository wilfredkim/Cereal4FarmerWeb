package models;

import controllers.Seed;
import play.data.validation.Constraints;
import play.db.ebean.Model;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.Constraint;
import java.util.List;

/**
 * Created by Gilbert on 7/28/2016.
 */
@Entity
    public class Seeds  extends Model{

        @Id
        public long Auto;

        @Constraints.Required
        public String ID_Number;
    @Constraints.Required
    public String UserName;




    @Constraints.Required
    public String PhoneNumber;


    @Constraints.Required
    public String SeedBrand;

    @Constraints.Required
    public String Quantity;
    @Constraints.Required
    public String Description;
    @Constraints.Required
    public String LandSize;
    @Constraints.Required
    public String Cultivation;

    @Constraints.Required
    public String status;
    @Constraints.Required
    public String Payment;



    public static Model.Finder<Long, Seeds> Seedlist = new Model.Finder<Long, Seeds>(Long.class,Seeds.class);

    public static List<Seeds> findAll() {
        return Seedlist.where().findList();
    }

    public static Seeds authenticateSta(String status) {
        return Seeds.Seedlist.where().eq("status", status).findUnique();


    }
    public static Addfields authenticateUser(String Id) {
        return Addfields.FieldList.where().eq("ID_Number", Id).findUnique();


    }


    public static Seeds authenticateorder(String Id) {
        return Seeds.Seedlist.where().eq("ID_Number", Id).findUnique();


    }


}
