package edu.ufl;

import android.content.res.Resources;

/*
 * A stupid little singleton class so we don't have to pass context around
 * everywhere we go
 */

public class ResourceManager {

    private static Resources res;
    
    public static void init(Resources r) {
        ResourceManager.res = r;
    }

    public static Resources getResources() {
        return ResourceManager.res;
    }
}

