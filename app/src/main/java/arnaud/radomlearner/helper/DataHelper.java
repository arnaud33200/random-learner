package arnaud.radomlearner.helper;

import java.util.Random;

/**
 * Created by arnaud on 2018/02/08.
 */

public class DataHelper {

    public static int getRadomNumber(int min, int max) {
        if (max < 0) {
            return 0;
        }
        int number = new Random().nextInt((max - min) + 1) + min;
        return number;
    }
}
