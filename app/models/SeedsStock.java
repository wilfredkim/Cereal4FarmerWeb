package models;

import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.Id;
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
public class SeedsStock  extends Model {


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


    public static Model.Finder<Long,SeedsStock> Seedstocklist = new Model.Finder<Long, SeedsStock>(Long.class, SeedsStock.class);

    public static List<SeedsStock> findAll() { return Seedstocklist.where().findList();
    }
}
