package com.janmalec.jan.doorlock;

/**
 * Created by jan on 16.5.2015.
 */
public class Entry {

    private int timestamp;
    private int oClose; // 0 is open, 1 is close

    @Override
    public String toString() {
        return "Event [timestamp=" + timestamp + ", open_close=" + oClose
                + "]";
    }
}
