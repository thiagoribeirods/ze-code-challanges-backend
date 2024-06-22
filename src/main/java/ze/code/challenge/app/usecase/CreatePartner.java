package ze.code.challenge.app.usecase;

import org.springframework.stereotype.Service;
import ze.code.challenge.app.entity.Partner;
import ze.code.challenge.app.gateway.PartnerGateway;
import ze.code.challenge.app.infrastructure.controller.dto.PartnerRequestDTO;

@Service
public class CreatePartner {

    private final PartnerGateway gateway;

    public CreatePartner(PartnerGateway gateway) {
        this.gateway = gateway;
    }

    public Partner execute(PartnerRequestDTO partnerRequestDTO) {

        Partner partner = new Partner(partnerRequestDTO);

        return gateway.save(partner);
    }
}
