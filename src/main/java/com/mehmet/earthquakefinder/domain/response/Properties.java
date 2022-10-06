package com.mehmet.earthquakefinder.domain.response;

import lombok.Data;
import java.sql.Timestamp;

@Data
public class Properties {

    public Float mag;
    public String place;
    public Timestamp time;
    public Long updated;
    public Object tz;
    public String url;
    public String detail;
    public Object felt;
    public Object cdi;
    public Object mmi;
    public Object alert;
    public String status;
    public Integer tsunami;
    public Integer sig;
    public String net;
    public String code;
    public String ids;
    public String sources;
    public String types;
    public Integer nst;
    public Float dmin;
    public Float rms;
    public Integer gap;
    public String magType;
    public String type;
    public String title;

}
