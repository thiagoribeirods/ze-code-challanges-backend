package ze.code.challenge.app.infrastructure.controller.dto;

import org.springframework.data.mongodb.core.geo.GeoJsonMultiPolygon;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

public record PartnerRequestDTO(
        String tradingName,
        String ownerName,
        String document,
        GeoJsonMultiPolygon coverageArea,
        GeoJsonPoint address
) {
}
