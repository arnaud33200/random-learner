package arnaud.radomlearner.preference;

import android.content.Context;
import android.content.SharedPreferences;

import arnaud.radomlearner.RandomLearnerApp;

/**
 * Created by arnaud on 2018/02/20.
 */

public class UserPreference {

    public static final String PREFERENCE_KEY_DICT_TYPE = "dictType";

    SharedPreferences preference;
    private static UserPreference instance;

    public static UserPreference getInstance() {
        return instance;
    }

    public UserPreference() {
        super();
        instance = this;
        Context context = RandomLearnerApp.getContext();
        this.preference = context.getSharedPreferences("preference", 0);
    }

    protected String getStringValue(String idString) {
        String string = "";
        string = preference.getString(idString, "");
        if (string == null) {
            string = "";
        }
        return string;
    }

    protected boolean setStringValue(String value, String idString) {
        SharedPreferences.Editor editor = preference.edit();
        editor.putString(idString, value);
        boolean success = editor.commit();
        return success;
    }


}
