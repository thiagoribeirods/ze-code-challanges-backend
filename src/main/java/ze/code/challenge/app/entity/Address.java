package ze.code.challenge.app.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("address")
@Data
@AllArgsConstructor
public class Address {

    private String type;

    private GeoJsonPoint coordinates;

    public Address() {}
}
