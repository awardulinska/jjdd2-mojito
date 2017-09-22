package infoshareKurs;

import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class City {
    private final Logger logger = LogManager.getLogger();


    private String countryName;

    private Double latitude;

    private Double longitude;

    private String name;

    private List<Place> placeList;

    protected City(Double latitude, Double longitude, String name, String country) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
        this.countryName = country;
        this.placeList = new ArrayList<Place>();
    }

    @Override
    public String toString() {
        return name + "[" +
                latitude +
                "/" + longitude +
                ']';
    }

    protected Double getLatitude() {
        return latitude;
    }

    protected Double getLongitude() {
        return longitude;
    }

    protected String getName() {
        return name;
    }

    protected List<Place> getPlaceList() {
        return placeList;
    }

    protected String getCountryName() {
        return countryName;
    }
}
