package funsooyenuga.numericalanalysis.util;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.EditText;

/**
 * Created by FAB THE GREAT on 09/02/2017.
 */

public class Util {
    /**
     * Attaches a Fragment to an Activity
     * @param manager - Fragment manager
     * @param resourceId - The id of the container (eg FrameLayout id)
     * @param fragment - Fragment to be added
     */
    public static void hostFragment(FragmentManager manager, int resourceId, @NonNull Fragment fragment) {
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(resourceId, fragment)
                .commit();
    }

    /**
     * Gets the value of an EditText and casts it into double.
     * @param editText
     * @return the double value of the EditText content
     */
    public static double getDouble(EditText editText) {
        return Double.valueOf(editText.getText().toString());
    }

    /**
     * Gets the value of an EditText and casts it to int
     * @param editText
     * @return the integer value of the EditText content
     */
    public static int getInt(EditText editText) {
        return Integer.valueOf(editText.getText().toString());
    }

    /**
     * Checks if an EditText is empty
     * @param editText
     * @return true if it is empty, otherwise return false
     */
    public static boolean isEmpty(EditText editText) {
        return editText.getText().toString().isEmpty();
    }
}
