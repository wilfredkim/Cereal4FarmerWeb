package controllers;

/**
 * Created by Gilbert on 9/2/2016.
 */
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import play.Logger;

/*import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;*/
public class GCMnotification {

   /* public static boolean sendNotification(String notification, String regId) {
        // api key
       *//* final Sender sender = new Sender("AIzaSyCQAE7VyFHreppcOkv7aXGk8JOxy4Osxdgvx");
        Result result = null;

        final Message message = new Message.Builder().timeToLive(30)
                .delayWhileIdle(true)
                .addData("date", new Date().getTime() + "")
                .addData("message", notification).build();

        final List<String> regids = new ArrayList<String>();
        regids.add(regId);
        Logger.info("entered2 : " + regids.size());
        try {
            result = sender.send(message, regids, 1);
        } catch (final IOException e) {
            e.printStackTrace();
        }
        return true;*//*


    }*/
/*

    String bottoken="236248479:AAHV-3DCT7LQedJRuro3PjA9utRtX7q-6kU";
    String url ="http://api.telegram.org.bot".bottoken;
*/



}
