package com.mehmet.earthquakefinder.controller;

import com.mehmet.earthquakefinder.domain.Earthquake;
import com.mehmet.earthquakefinder.service.EarthquakeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
public class EarthquakeController {

    @Autowired
    private EarthquakeService earthquakeService;

    @GetMapping(value = "/query")
    private ResponseEntity getEarthquakes(@RequestParam String countryName, @RequestParam Integer countOfDays){
        Optional<List<Earthquake>> earthquakeList = earthquakeService.findEarthquakes(countryName, countOfDays);
        if (earthquakeList.isEmpty()){
            String message = "No earthquakes were recorded past " + countOfDays + " days.";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
        return ResponseEntity.ok(earthquakeList.get());
    }
}
