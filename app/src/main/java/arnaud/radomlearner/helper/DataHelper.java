package arnaud.radomlearner.helper;

import android.content.res.Resources;
import android.util.DisplayMetrics;

import java.util.Random;

import arnaud.radomlearner.RandomLearnerApp;

/**
 * Created by arnaud on 2018/02/08.
 */

public class DataHelper {

    public static boolean getRadomBoolean() {
        return getRadomNumber(0, 1) == 1;
    }

    public static int getRadomNumber(int min, int max) {
        if (max < 0) {
            return 0;
        }
        int number = new Random().nextInt((max - min) + 1) + min;
        return number;
    }

    public static float convertDpToPixel(float dp) {
        Resources resources = RandomLearnerApp.getContext().getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return px;
    }

}
