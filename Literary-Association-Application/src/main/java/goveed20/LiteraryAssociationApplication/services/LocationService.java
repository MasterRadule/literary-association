package goveed20.LiteraryAssociationApplication.services;

import goveed20.LiteraryAssociationApplication.model.Location;
import goveed20.LiteraryAssociationApplication.utils.Coordinate;
import goveed20.LiteraryAssociationApplication.utils.Coordinates;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class LocationService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String apiURL = "http://api.positionstack.com/v1/forward";
    private final String apiKey = "fad3f55f45c530ab6e03f5b01470c85f";

    public Location createLocation(String country, String city) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiURL).queryParam("access_key", apiKey)
                .queryParam("query", String.format("%s, %s", city, country));

        Coordinate c;
        try {
            ResponseEntity<Coordinates> coordinate = restTemplate
                    .getForEntity(builder.toUriString(), Coordinates.class);
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
