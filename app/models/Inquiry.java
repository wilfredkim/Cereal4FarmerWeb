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
public class Inquiry  extends Model {

    @Id
    public long num;

    @Constraints.Required
    public String Name;

    @Constraints.Required
    public String ID_Number;

    @Constraints.Required
    public String PhoneNumber;


    @Constraints.Required
    public String Date;

    @Constraints.Required
    public String Message;

    @Constraints.Required
    public String status;

    public static Model.Finder<Long, Inquiry> Inqlist = new Model.Finder<Long, Inquiry>(Long.class, Inquiry.class);

    public static List<Inquiry> findAll() {
        return Inqlist.where().findList();
    }


}
