package ze.code.challenge.app.entity;

import jakarta.servlet.http.Part;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.geo.GeoJsonMultiPolygon;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import ze.code.challenge.app.infrastructure.controller.dto.PartnerRequestDTO;

@Document("partners")
@Data
@AllArgsConstructor
public class Partner {

    @Id
    private ObjectId id;

    private String tradingName;

    private String ownerName;

    @Indexed(unique = true)
    private String document;

    private GeoJsonMultiPolygon coverageArea;

    private GeoJsonPoint address;

    public Partner () {}

    public Partner (PartnerRequestDTO dto) {
        this.tradingName = dto.tradingName();
        this.ownerName = dto.ownerName();
        this.document = dto.document();
        this.coverageArea = dto.coverageArea();
        this.address = dto.address();
    }

}
