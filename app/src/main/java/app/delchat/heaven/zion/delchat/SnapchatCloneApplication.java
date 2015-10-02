package app.delchat.heaven.zion.delchat;

import android.app.Application;

import com.parse.Parse;

/**
 * Created by Zion on 24/09/15.
 */
public class SnapchatCloneApplication extends Application {

    @Override

    public void onCreate(){
        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);
        super.onCreate();
        Parse.initialize(this, "FVbFQSbKN0IkPzzIhsH9GCSRzmN7M5SzKamWsqpd", "4wHPEarGGTtFx6xJDfnOSDLY5BgBfH0lpAW2paBG");
    }
}
