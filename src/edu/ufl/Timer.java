package edu.ufl;

import java.io.*;

public class Timer {

    private long t;
    private String s;

    public Timer(String m) {
        this.s = m;
        this.t = System.currentTimeMillis();
    }

    public void done() {
        GameLog.d("Timer",this.s+":"+String.valueOf(System.currentTimeMillis()-this.t)+" milliseconds");
    }
}
