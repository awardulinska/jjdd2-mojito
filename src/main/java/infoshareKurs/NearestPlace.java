package infoshareKurs;

import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NearestPlace {
    private final Logger logger = LogManager.getLogger(NearestPlace.class);

    List<City> cityList;

    public NearestPlace(List<City> city) {
        this.cityList = city;
    }

    private String name;

    public String findNearestPlace(GeoLocation geoLocation) {
        logger.debug("znalezienie najblizszej stacji od miejsca uzytkownika");
        double lowestDistance = 9999999999999999.9;
        DistanceMath distanceMath = new DistanceMath();

        for (City city : cityList) {
            for (Place place : city.getPlaceList()) {
                if (lowestDistance > distanceMath.countDistance(place, geoLocation)) {
                    lowestDistance = distanceMath.countDistance(place, geoLocation);
                    this.name = place.getName();
                }
            }
        }
        return "Najbliższa stacja rowerowa znajduje się " + lowestDistance + " km od Ciebie. Stacja nazywa się " + this.name;

    }

    public List<Place> findPlace(GeoLocation geoLocation, double distance) {
        logger.debug("znalezienie stacji w danym promieniu od uzytkownika");
        DistanceMath distanceMath = new DistanceMath();
        List<Place> placelist = new ArrayList<>();
        for (City city : cityList) {
            for (Place place : city.getPlaceList()) {
                double placedistance = distanceMath.countDistance(place, geoLocation);
                if (placedistance <= distance) {
                    placelist.add(place);
                }
            }
        }
        if (placelist.size() > 0) {
            String b ="";
            for (Place place : placelist) {
                b = b + "\n" + place.getName();
            }
        }return placelist;
    }
}