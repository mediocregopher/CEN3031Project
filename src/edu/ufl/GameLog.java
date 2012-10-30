package edu.ufl;
import android.util.Log;
import android.graphics.RectF;


public class GameLog {

    private final static String GAMENAME = "TheGame";

    public static int d(String c, String d) {
        return Log.d(GAMENAME+"/"+c,d);
    }

    public static String rectFToString(RectF rectf) {
        return "[l:"+String.valueOf(rectf.left)  +" "+
                "t:"+String.valueOf(rectf.top)   +" "+
                "r:"+String.valueOf(rectf.right) +" "+
                "b:"+String.valueOf(rectf.bottom)+"]";
    }
}

