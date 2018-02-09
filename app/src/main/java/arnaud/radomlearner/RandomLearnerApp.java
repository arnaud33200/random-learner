package arnaud.radomlearner;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;

/**
 * Created by arnaud on 2017/12/07.
 */

public class RandomLearnerApp extends Application {

    public static RandomLearnerApp sharedInstance = null;

    @Override
    public void onCreate() {
        super.onCreate();

        sharedInstance = this;
    }

    public static Context getContext() {
        return sharedInstance.getApplicationContext();
    }

    public static int getContextColor(int colorRes) {
        return sharedInstance.getApplicationContext().getColor(colorRes);
    }
}
