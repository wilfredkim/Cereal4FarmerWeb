package models;

import play.db.ebean.Model;
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
public class NewsPost extends Model {
    @Id
    public long num;

    @Constraints.Required
    public String Event;
    @Constraints.Required
    public String News;
    @Constraints.Required
    public String Date;



    public static Model.Finder<Long,NewsPost> Newslist = new Model.Finder<Long, NewsPost>(Long.class, NewsPost.class);

    public static List<NewsPost> findAll() {
        return Newslist.orderBy("Date desc").where().findList();
    }

}
