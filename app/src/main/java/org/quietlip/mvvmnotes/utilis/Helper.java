package org.quietlip.mvvmnotes.utilis;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.snackbar.Snackbar;

public class Helper {
    private static Helper helper;
    private static Snackbar snackbar;

    private Helper() {
    }

    public static Helper getHelper() {
        if (helper == null) {
            helper = new Helper();
        }
        return helper;
    }

    public static void makeSnackbar(CoordinatorLayout coordinatorLayout, String message) {
        snackbar = Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    public static void dismissSnackbar() {
        if (snackbar != null) {
            snackbar.dismiss();
        }
    }
}
