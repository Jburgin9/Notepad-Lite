package org.quietlip.mvvmnotes.utilis;

import android.util.Log;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.snackbar.Snackbar;

public class Helper {
    private static final String TAG = "Helper";
    private static Snackbar snackbar;


    private Helper() {}

    public static void makeSnackbar(CoordinatorLayout coordinatorLayout, String message) {
        snackbar = Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    public static void dismissSnackbar() {
        if (snackbar != null) {
            snackbar.dismiss();
        }
    }

    public static int timeStringSplit(String formatted24Hours){
        Log.d(TAG, "string inserted: " + formatted24Hours);
        /*
        *what this method is going to do is take in a 24-hour formatted string of the time
        * and split it into 2 ints, hours and minutes. This method should return an int,
        * I want hours out of this method so that I could be able to make an if-statement
        * to determine if the hours is == to 24 so that I can display days. Follow up on this is
        * how to display n days.
        * use for loop to separate the numbers before and after the semi-colon.
         */
        try {
            if (formatted24Hours.length() == 5) {
                int firstHourSlot = formatted24Hours.charAt(0);
                int secondHourSlot = formatted24Hours.charAt(1);
                int thirdHourSlot = formatted24Hours.charAt(3);
                int fourthtHourSlot = formatted24Hours.charAt(4);
                Log.d(TAG, "last number: " + fourthtHourSlot);
            }  else {
            }
        } catch (NumberFormatException e){
            e.printStackTrace();
        }
        return 0;

    }

}
