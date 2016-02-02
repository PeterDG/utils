package com.peter.util.time;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by Peter on 1/9/2016.
 */
public class ZonedDate {

    ZonedDateTime date;
    DateTimeFormatter formatter;

    public ZonedDate(String date) {
        this.date = ZonedDateTime.parse(date);
        formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    }

    public ZonedDateTime setZoneOffset(String strZone){
        LocalDateTime dt = LocalDateTime.now();
        ZoneId zone = ZoneId.of(strZone);
        ZonedDateTime zdt = dt.atZone(zone);
        ZoneOffset offset = zdt.getOffset();
        date= date.withZoneSameInstant(offset);
        return date;
    }

    public String toString(){
        String formattedDate = formatter.format(date);
        return formattedDate;
    }

    public void setFormatter(DateTimeFormatter formatter) {
        this.formatter = formatter;
    }
}
