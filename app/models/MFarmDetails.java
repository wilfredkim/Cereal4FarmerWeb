package models;

import play.data.validation.Constraints;
import play.db.ebean.Model;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.Constraint;
import java.util.List;

/**
 * Created by Gilbert on 9/28/2016.
 */
@Entity
public class MFarmDetails extends Model {
    @Id
    public long num;

    @Constraints.Required
    public String FullName;
    @Constraints.Required
    public String IDNumber;
    @Constraints.Required
    public String Address;


    @Constraints.Required
    public String Email;

    @Constraints.Required
    public String Town;


    @Constraints.Required
    public String Location;

    @Constraints.Required
    public String Land_Size;

    @Constraints.Required
    public String Cultivation_Size;



    public static Model.Finder<Long, MFarmDetails> farmlist = new Model.Finder<Long, MFarmDetails>(Long.class, MFarmDetails.class);

    public static List<MFarmDetails> findAll() {return farmlist.where().findList();}
    public static MFarmDetails authenticateUser(String Id) {
        return MFarmDetails.farmlist.where().eq("IDNumber", Id).findUnique();


    }


}
