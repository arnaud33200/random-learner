package arnaud.radomlearner;

import android.app.Application;
import android.content.Context;

import arnaud.radomlearner.preference.UserPreference;

/**
 * Created by arnaud on 2017/12/07.
 */

public class RandomLearnerApp extends Application {

    public static RandomLearnerApp sharedInstance = null;

    @Override
    public void onCreate() {
        super.onCreate();

        sharedInstance = this;

        new UserPreference();
    }

    public static Context getContext() {
        return sharedInstance.getApplicationContext();
    }

    public static int getContextColor(int colorRes) {
        return sharedInstance.getApplicationContext().getColor(colorRes);
    }
}
