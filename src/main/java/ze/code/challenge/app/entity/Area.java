package ze.code.challenge.app.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.geo.GeoJsonMultiPolygon;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("areas")
@Data
@AllArgsConstructor
public class Area {

    private String type;

    private GeoJsonMultiPolygon coordinates;

    public Area() {}

}
