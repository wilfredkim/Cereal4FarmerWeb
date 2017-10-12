package models;

import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.Constraint;
import java.util.List;

/**
 * Created by Gilbert on 7/17/2016.
 */
@Entity
public class Addfields extends Model {
    @Id
    public long Ida;

    @Constraints.Required
    public String First_name;

    @Constraints.Required
    public String Second_name;

    @Constraints.Required
    public String Address;


    @Constraints.Required
    public String Email;

    @Constraints.Required
    public String Id;


    @Constraints.Required
    public String Block_number;

    @Constraints.Required
    public String Land_Size;

    @Constraints.Required
    public String Cultivation_Size;

    @Constraints.Required
    public String Password;
    @Constraints.Required
    public String status;

    public static Model.Finder<Long, Addfields> FieldList = new Model.Finder<Long, Addfields>(Long.class, Addfields.class);

    public static List<Addfields> findAll() {
        return FieldList.where().findList();
    }


    public static Addfields authenticateUser(String Id) {
        return Addfields.FieldList.where().eq("Id", Id).findUnique();


    }

    public static Addfields authenticateUserlogin(String idnumber, String password) {
        return Addfields.FieldList.where().eq("Id", idnumber).eq("Password", password).findUnique();


    }

    public static Addfields authenticates(String status,String password) {
        return Addfields.FieldList.where().eq("status", status).eq("Id",password).findUnique();


    }
    public  static List<Addfields> findByCategoryId(String id){
        return FieldList.where().eq("Cultivation_Size", id).findList();
    }

}
