package com.mehmet.earthquakefinder.domain.response;

import lombok.Data;
import java.util.List;


@Data
public class EarthquakeResponseDto {

    public String type;
    public Metadata metadata;
    public List<Feature> features = null;
    public List<Float> bbox = null;

}
