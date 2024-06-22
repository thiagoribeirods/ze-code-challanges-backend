package ze.code.challenge.app.usecase;

import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.stereotype.Service;
import ze.code.challenge.app.entity.Partner;
import ze.code.challenge.app.gateway.PartnerGateway;

import java.util.List;

@Service
public class SearchPartnerByCoordinates {

    private final PartnerGateway gateway;

    public SearchPartnerByCoordinates(PartnerGateway gateway) {
        this.gateway = gateway;
    }

    public List<Partner> execute(double longitude, double latitude) {
        GeoJsonPoint address = new GeoJsonPoint(latitude, longitude);

        return gateway.findByCoverageArea(address);
    }

}
