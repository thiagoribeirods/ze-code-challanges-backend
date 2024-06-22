package ze.code.challenge.app.usecase;

import org.springframework.stereotype.Service;
import ze.code.challenge.app.entity.Partner;
import ze.code.challenge.app.gateway.PartnerGateway;

import java.util.Optional;

@Service
public class GetPartnerById {

    private final PartnerGateway gateway;

    public GetPartnerById(PartnerGateway gateway) {
        this.gateway = gateway;
    }

    public Optional<Partner> execute(String id) {
        return this.gateway.findById(id);
    }
}
