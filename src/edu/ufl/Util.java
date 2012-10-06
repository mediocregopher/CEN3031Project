package edu.ufl;

import android.graphics.RectF;

class Util {

    /*
     * Given two RectF's, detects whether or not they are colliding. If they aren't, returns
     * the NONE enum, otherwise returns the enum most closely matching which side of the second
     * RectF (b) the first RectF (a) is colliding with
     */
    public enum IntersectRet { NONE,TOP,BOTTOM,LEFT,RIGHT}
    public static IntersectRet intersect(RectF a, RectF b) {
        RectF i = new RectF(a);
        if (i.intersect(b)) {
            if (i.width() > i.height()) {
                if (a.top < b.top) return IntersectRet.TOP;
                else return IntersectRet.BOTTOM;
            }
            else {
                if (a.right > b.right) return IntersectRet.RIGHT;
                else return IntersectRet.LEFT;
            }
        }
        else {
            return IntersectRet.NONE;
        }
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
