package com.mehmet.earthquakefinder.service;

import com.fasterxml.jackson.databind.DeserializationFeature;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mehmet.earthquakefinder.domain.Earthquake;
import com.mehmet.earthquakefinder.domain.Location;
import com.mehmet.earthquakefinder.domain.response.EarthquakeResponseDto;
import com.mehmet.earthquakefinder.domain.response.Feature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class EarthquakeService {

    @Autowired
    private RestTemplate restTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    @Value("${api.url}")
    private String API_URL;

    List<Location> countryList;

    /*
    given json file contains country names and their latitude and longitude information, respectively
    taken from the source https://github.com/eesur/country-codes-lat-long/blob/master/country-codes-lat-long-alpha3.json
    with this way, users can enter the name of the country (in English) and get the earthquake info
    this process can be improved by finding online api for the lat-long list so that it can be maintainable,
    however most of them required api-keys which were not free of fee
    */
    {
        // reading the file, mapping the required fields to use in other methods
        try {
            countryList = Arrays.asList(objectMapper.readValue(new File("src/main/resources/country-lat-long-list.json"), Location[].class));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // finding the country info from the country list to send the request
    private Location countryLocator(String countryName) {
        Location countryLocation = null;
        for (Location location : countryList) {
            if (location.getName().equalsIgnoreCase(countryName)) {
                countryLocation = location;
            }
        }
        return countryLocation;
    }


    // returning Optional eases the process of checking the presence of a value (empty/present)
    // avoing null check operations -- especially in the controller layer
    public Optional<List<Earthquake>> findEarthquakes(String countryName, Integer countOfDays) {

        // if the user enters a wrong/misspelled country name, it will throw an error
        // this part can be improved so that the response body will have a similar msg in the controller
        Location location = countryLocator(countryName);
        if (location == null) {
            throw new InputMismatchException("No country found with the name of %s" + countryName);
        }

        String endTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String startTime = LocalDateTime.now().minusDays(countOfDays).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        // for the location spotting - Circle method parameters are used
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(API_URL + "/query").queryParam("format", "geojson").queryParam("starttime", startTime).queryParam("endtime", endTime).queryParam("latitude", location.getLatitude()).queryParam("longitude", location.getLongitude()).queryParam("maxradius", "12");

        EarthquakeResponseDto response = restTemplate.getForObject(uriBuilder.toUriString(), EarthquakeResponseDto.class);
        int countOfEarthquakes = response.getFeatures().size();
        if (!(countOfEarthquakes > 0)) {
            return Optional.empty();
        }

        List<Earthquake> earthquakeList = new ArrayList<>();
        for (int i = 0; i < countOfEarthquakes; i++) {
            Earthquake earthquake = new Earthquake(countryName, response.getFeatures().get(i));
            earthquakeList.add(earthquake);
        }
        return Optional.of(earthquakeList);
    }
}
