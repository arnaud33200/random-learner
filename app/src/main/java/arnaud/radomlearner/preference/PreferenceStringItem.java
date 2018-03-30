package arnaud.radomlearner.preference;

import android.content.SharedPreferences;

/**
 * Created by arnaud on 2018/03/30.
 */

public class PreferenceStringItem {
    private String value;

    private String defaultValue;
    private final String key;

    public PreferenceStringItem(String key, String defaultValue) {
        this.value = "";
        this.key = key;
        this.defaultValue = defaultValue;
    }

    public String getStringValue() {
        if (value.length() == 0) {
            value = UserPreference.getInstance().getStringValue(key);
            if (value.length() == 0) {
                setStringValue(defaultValue);
            }
        }

//        if (validator != null) {
//            value = validator.validateDeserializedValue(value);
//        }

        return value;
    }

    public void setStringValue(String s) {
        value = s == null ? "" : s;
        UserPreference.getInstance().setStringValue(s, key);

//        if (validator != null) {
//            validator.onValueSerialized(value);
//        }
    }
}
