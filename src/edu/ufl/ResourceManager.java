package edu.ufl;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.content.res.AssetManager;
import android.view.Display;
import android.view.WindowManager;
import android.util.DisplayMetrics;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.util.HashMap;

/*
 * A stupid little singleton class so we don't have to pass context around
 * everywhere we go
 */

public class ResourceManager {

    private static Context con;
    private static float densityDpi;

    private static HashMap<Integer,BitmapPair> bitmaps = new HashMap<Integer,BitmapPair>();

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
    
    public static SharedPreferences getPreferences() {
        return con.getSharedPreferences("Preferences", 0);
    }

    public static AssetManager getAssetManager() {
        return ResourceManager.getResources().getAssets();
    }

    /* Scales dp to px */
    public static float dpToPx(float dp) {
        return dp * (ResourceManager.densityDpi / 160f);
    }

    /* Scales px to dp */
    public static float pxToDp(float px) {
        return px / (ResourceManager.densityDpi / 160f);
    }

    /* Returns BitmapPair for given drawable id. Used by
     * getBitmap() and getBitmapFlipped()
     */
    private static BitmapPair getBitmapPair(int drawableid) {
        BitmapPair b = ResourceManager.bitmaps.get(drawableid);
        if (b == null) {
            b = new BitmapPair(drawableid);
            ResourceManager.bitmaps.put(Integer.valueOf(drawableid), b);
        }
        return b;
    }

    public static Bitmap getBitmap(int drawableid) {
        return ResourceManager.getBitmapPair(drawableid).original;
    }

    /* Given a drawable id, stores both the original bitmap representation
     * and a copy which is flipped across the y-axis
     */
    private static class BitmapPair {
        private static BitmapFactory.Options defaultOptions = new BitmapFactory.Options() {{
            inPurgeable = true;
            inInputShareable = true;
        }};
        public final Bitmap original;
        public BitmapPair(int drawableid) {
            this.original = BitmapFactory.decodeResource( ResourceManager.getResources(), drawableid, defaultOptions );
        }
    }
}

