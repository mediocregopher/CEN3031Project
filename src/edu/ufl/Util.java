package edu.ufl;

import android.graphics.RectF;

class Util {

    /*
     * Given two RectF's, detects whether or not they are colliding. If they aren't, returns
     * the NONE enum, otherwise returns the enum most closely matching which side of the second
     * RectF (b) the first RectF (a) is colliding with
     *
     * This function is a bit naive and doesn't quite work if your rectangles have very
     * exageratted proportions (one being very long, for instance). In these cases detection
     * of the collision still works, but direction guessing may be wrong. We won't be using
     * many rectangles in the game anyway, but it's something to know.
     */
    public enum IntersectRet { NONE,TOP,BOTTOM,LEFT,RIGHT}
    public static IntersectRet intersect(RectF a, RectF b) {

        if (a.left <= b.right  && a.right  >= b.left &&
            a.top  <= b.bottom && a.bottom >= b.top     ) {

            float xdiff =  (a.left + (a.right-a.left)/2f) - (b.left + (b.right-b.left)/2f);
            float ydiff = (a.top  + (a.bottom-a.top)/2f) - (b.top  + (b.bottom-b.top)/2f);

            if (Math.abs(xdiff) > Math.abs(ydiff)) {
                if (xdiff < 0) { return IntersectRet.LEFT;  }
                else           { return IntersectRet.RIGHT; }
            }
            else {
                if (ydiff < 0) { return IntersectRet.TOP;    }
                else           { return IntersectRet.BOTTOM; }
            }

        }
        return IntersectRet.NONE;
    }

    /*
     * Basic intersect method, takes in positions of two rectangles (their top left corners)
     * and their widths/heights and returns a bool of whether or not they're intersecting.
     */
    public static boolean intersectLite(float x1, float y1, float w1, float h1,
                                        float x2, float y2, float w2, float h2) {
        return (x1 <= x2+w2 && x1+w1 >= x2 &&
                y1 <= y2+h2 && y1+h1 >= y2);
    }

}
