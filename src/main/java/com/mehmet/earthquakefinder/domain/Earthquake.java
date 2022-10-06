package com.mehmet.earthquakefinder.domain;
import com.mehmet.earthquakefinder.domain.response.Feature;
import lombok.Data;

import java.time.format.DateTimeFormatter;

@Data
public class Earthquake {

    private String countryName;
    private String placeOfEarthquake;
    private float magnitude;
    private String date;
    private String time;

    // mapping the required fields to display in the body from the response of the api call
    // handling the mapping operations in the domain layer for this size of project can be better for encapsulation
    // and simple business logic operations
    public Earthquake(String countryName, Feature feature) {
        setCountryName(countryName);
        setPlaceOfEarthquake(feature.getProperties().getPlace());
        setMagnitude(feature.getProperties().getMag());
        setDate(feature.getProperties().getTime().toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        setTime(feature.getProperties().getTime().toLocalDateTime().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
    }

}
