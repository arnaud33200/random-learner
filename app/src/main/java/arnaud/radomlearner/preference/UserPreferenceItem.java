package arnaud.radomlearner.preference;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import arnaud.radomlearner.RandomLearnerApp;

/**
 * Created by arnaud on 2018/02/20.
 */

public class UserPreferenceItem<T extends Serializable> {

    protected T value;
    protected T defaultValue;
    protected final String key;

    public static final String PREFERENCE_KEY_DICT_TYPE = "dictType";

    public UserPreferenceItem(String key, T defaultValue) {
        this.key = key;
        this.defaultValue = defaultValue;
    }

    public T getValue() {
        if (value == null) {
            String stringValue = RandomLearnerApp.getPreference().getString(key, "");
            value = (T) deserialize(stringValue);
            if (value == null) {
                setValue(defaultValue);
            }
        }
        return value;
    }

    public boolean setValue(T v) {
        value = v != null ? v : defaultValue;
        String stringValue = serialize(value);
        SharedPreferences.Editor editor = RandomLearnerApp.getPreference().edit();
        editor.putString(key, stringValue);
        boolean success = editor.commit();
        return success;
    }

    public String serialize(T obj) {
        String stringvalue = "";
        if (obj == null) { return ""; }
        try {
            ByteArrayOutputStream serialObj = new ByteArrayOutputStream();
            ObjectOutputStream objStream = new ObjectOutputStream(serialObj);
            objStream.writeObject(obj);
            objStream.close();
            stringvalue = encodeBytes(serialObj.toByteArray());
        } catch (Exception e) { }
        if (stringvalue == null) { stringvalue = ""; }
        return stringvalue;
    }

    public T deserialize(String str) {
        if (str == null || str.length() == 0) {
            return null;
        }
        try {
            ByteArrayInputStream serialObj = new ByteArrayInputStream(decodeBytes(str));
            ObjectInputStream objStream = new ObjectInputStream(serialObj);
            return (T) objStream.readObject();
        } catch (Exception e) { }
        return null;
    }

    public static String encodeBytes(byte[] bytes) {
        StringBuffer strBuf = new StringBuffer();

        for (int i = 0; i < bytes.length; i++) {
            strBuf.append((char) (((bytes[i] >> 4) & 0xF) + ((int) 'a')));
            strBuf.append((char) (((bytes[i]) & 0xF) + ((int) 'a')));
        }

        return strBuf.toString();
    }

    public static byte[] decodeBytes(String str) {
        byte[] bytes = new byte[str.length() / 2];
        for (int i = 0; i < str.length(); i += 2) {
            char c = str.charAt(i);
            bytes[i / 2] = (byte) ((c - 'a') << 4);
            c = str.charAt(i + 1);
            bytes[i / 2] += (c - 'a');
        }
        return bytes;
    }


}
