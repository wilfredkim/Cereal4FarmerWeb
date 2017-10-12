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
public class RegisterAdmin extends  Model {


    @Id
    public long num;

    @Constraints.Required
    public String FullName;

    @Constraints.Required
    public String Email ;

    @Constraints.Required
    public String Gender;


    @Constraints.Required
    public String Username;

    @Constraints.Required
    public String Password;




    public static Model.Finder<Long, RegisterAdmin> Adminlist = new Model.Finder<Long, RegisterAdmin>(Long.class, RegisterAdmin.class);

    public static List<RegisterAdmin> findAll() {
        return Adminlist.where().findList();
    }


    public static RegisterAdmin authenticateUser(String Username) {
        return RegisterAdmin.Adminlist.where().eq("Username", Username).findUnique();


    }
    public static RegisterAdmin authenticateUserlogin(String username, String password) {
        return RegisterAdmin.Adminlist.where().eq("Username", username).eq("Password", password).findUnique();


    }
}
