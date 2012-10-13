package edu.ufl;

import android.content.Context;
import android.content.res.Resources;
import android.view.Display;
import android.view.WindowManager;
import android.util.DisplayMetrics;

/*
 * A stupid little singleton class so we don't have to pass context around
 * everywhere we go
 */

public class ResourceManager {

    private static Context con;
    private static float densityDpi;

    public static void init(Context c) {
        ResourceManager.con = c;

        /* <magic> */
        Display display = ((WindowManager) c.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        /* </magic> */

        ResourceManager.densityDpi = outMetrics.densityDpi;
    }

    public static Resources getResources() {
        return ResourceManager.con.getResources();
    }

    /* Scales dp to px */
    public static float dpToPx(float dp) {
        return dp * (ResourceManager.densityDpi / 160f);
    }

    /* Scales px to dp */
    public static float pxToDp(float px) {
        return px / (ResourceManager.densityDpi / 160f);
    }
}

