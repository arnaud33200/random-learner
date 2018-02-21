package arnaud.radomlearner;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by arnaud on 2018/02/20.
 */

public class UserPreference {

    public enum DictType {
        Adjective("Adjective"), Verb("Verb"), Hiragana("Hiragana"),
        KatakanaPart1("KatakanaPart1"), KatakanaAll("KatakanaAll");

        public final String value;

        DictType(String text) {
            value = text;
        }

        public static DictType fromString(String string) {
            if (string == null) {
                return Adjective;
            }
            for (DictType dictType : DictType.values()) {
                if (dictType.value.equals(string)) {
                    return dictType;
                }
            }
            return Adjective;
        }
    }

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

    public DictType getCurrentSelectedDictType() {
        String value = deserializeString(preference, "dictType");
        return DictType.fromString(value);
    }

    public void setCurrentSelectedDictType(DictType dictType) {
        serializeString(preference, dictType.value, "dictType");
    }

    protected String deserializeString(SharedPreferences preferences, String idString) {
        String string = "";
        string = preferences.getString(idString, "");
        if (string == null) {
            string = "";
        }
        return string;
    }

    protected void serializeString(SharedPreferences preferences, String string, String idString) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(idString, string);
        boolean success = editor.commit();
    }


}
