package com.peter.util.data.serializable.json.data;

import java.io.Serializable;

/**
 * Created by Peter on 1/7/2017.
 */
public class Instrument implements Serializable{
    private String instrument;
    private String time;
    private String bid;
    private String ask;
    private String status;

    public String getInstrument() {
        return instrument;
    }

    public void setInstrument(String instrument) {
        this.instrument = instrument;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public String getAsk() {
        return ask;
    }

    public void setAsk(String ask) {
        this.ask = ask;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
