package cultureapp.domain.cultural_offer;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Embeddable
public class Location {
    @Column(name="longitude", nullable = false)
    Double longitude;

    @Column(name="latitude", nullable = false)
    Double latitude;

    @Column(name="address")
    String address;

    public static Location of(Double longitude, Double latitude, String address) {
        return new Location(longitude, latitude, address);
    }

}
