package com.mehmet.earthquakefinder.domain.response;
import lombok.Data;


@Data
public class Metadata {

    public Long generated;
    public String url;
    public String title;
    public Integer status;
    public String api;
    public Integer count;

}
