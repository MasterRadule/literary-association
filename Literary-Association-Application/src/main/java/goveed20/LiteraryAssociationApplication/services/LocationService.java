package goveed20.LiteraryAssociationApplication.services;

import goveed20.LiteraryAssociationApplication.model.Location;
import goveed20.LiteraryAssociationApplication.utils.Coordinate;
import goveed20.LiteraryAssociationApplication.utils.Coordinates;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class LocationService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String apiURL = "http://api.positionstack.com/v1/forward";
    private final String apiKey = "fad3f55f45c530ab6e03f5b01470c85f";

    public Location createLocation(String country, String city) {
        String requestUrl = String
                .format("%s?access_key=%s&query=%s&limit=1", apiURL, apiKey, String.format("%s, %s", city, country));

        Coordinate c;
        try {
            ResponseEntity<Coordinates> coordinate = restTemplate.getForEntity(requestUrl, Coordinates.class);
            c = coordinate.getBody().getData().get(0);
        } catch (Exception e) {
            c = new Coordinate(45.0, 45.0);
        }

        return Location.builder()
                .country(country)
                .city(city)
                .latitude(c.getLatitude())
                .longitude(c.getLongitude())
                .build();
    }
}
