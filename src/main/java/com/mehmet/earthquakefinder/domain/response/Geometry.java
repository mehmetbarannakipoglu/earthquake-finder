package com.mehmet.earthquakefinder.domain.response;
import lombok.Data;
import java.util.List;

@Data
public class Geometry {
    public String type;
    public List<Float> coordinates = null;
}
