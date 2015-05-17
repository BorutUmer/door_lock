package com.janmalec.jan.doorlock;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by jan on 16.5.2015.
 */
public class Entry implements java.io.Serializable{

    private Date timestamp = null;
    private int oClose; // 0 is open, 1 is close

    public Entry(){}

    public Entry(int oClose){
        super();

        this.timestamp = new Date();
        this.oClose = oClose;
    }

    @Override
    public String toString() {
        return "Event [timestamp=" + getTimestamp() + ", open_close=" + oClose
                + "]";
    }


    public String getTimestamp(){
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "dd-MM-yyyy HH:mm:ss", Locale.getDefault());
        return dateFormat.format(timestamp);

    }

    public void setTimestamp(Date timestamp){
        this.timestamp = timestamp;
    }

    public int getOpenClose(){
        return oClose;
    }

    public void setoClose(int oClose){
        this.oClose = oClose;
    }
}
