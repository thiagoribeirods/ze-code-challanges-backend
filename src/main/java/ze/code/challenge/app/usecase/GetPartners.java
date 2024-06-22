package ze.code.challenge.app.usecase;

import org.springframework.stereotype.Service;
import ze.code.challenge.app.entity.Partner;
import ze.code.challenge.app.gateway.PartnerGateway;

import java.util.List;

@Service
public class GetPartners {

    private final PartnerGateway gateway;

    public GetPartners(PartnerGateway gateway) {
        this.gateway = gateway;
    }

    public List<Partner> execute() {
        return this.gateway.findAll();
    }
}
