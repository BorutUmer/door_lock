package com.janmalec.jan.doorlock;

/**
 * Created by jan on 16.5.2015.
 */
public class Entry {

    private int id;
    private int timestamp;
    private int oClose; // 0 is open, 1 is close

    @Override
    public String toString() {
        return "Event [timestamp=" + timestamp + ", open_close=" + oClose
                + "]";
    }

    public int getId(){
        return id;
    }

    public int getTimestamp(){
        return timestamp;
    }

    public int getOpenClose(){
        return oClose;
    }

    public void setId(int id_){
        id = id_;
    }

    public void setTimestamp(int timestamp_){
        timestamp = timestamp_;
    }

    public void setoClose(int oClose_){
        oClose = oClose_;
    }
}
