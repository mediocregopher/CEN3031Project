package edu.ufl;

import android.content.Context;
import android.content.res.Resources;

/*
 * A stupid little singleton class so we don't have to pass context around
 * everywhere we go
 */

public class ResourceManager {

    private static Context con;

    public static void init(Context c) {
        ResourceManager.con = c;
    }

    public static Resources getResources() {
        return ResourceManager.con.getResources();
    }
}

