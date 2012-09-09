package edu.ufl;
import android.util.Log;


public class GameLog {

    private final static String GAMENAME = "TheGame";

    public static int d(String c, String d) {
        return Log.d(GAMENAME+"/"+c,d);
    }
}

