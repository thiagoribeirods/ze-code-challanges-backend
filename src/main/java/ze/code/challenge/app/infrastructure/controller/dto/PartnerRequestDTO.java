package ze.code.challenge.app.infrastructure.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.mongodb.core.geo.GeoJsonMultiPolygon;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
public record PartnerRequestDTO(
        @NotNull @NotBlank @NotEmpty String tradingName,
        @NotNull @NotBlank @NotEmpty String ownerName,
        @NotNull @NotBlank @NotEmpty String document,
        @NotNull GeoJsonMultiPolygon coverageArea,
        @NotNull GeoJsonPoint address
) {
}
