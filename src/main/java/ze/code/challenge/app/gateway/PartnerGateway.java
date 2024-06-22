package ze.code.challenge.app.gateway;

import org.springframework.stereotype.Component;
import ze.code.challenge.app.entity.Partner;
import ze.code.challenge.app.infrastructure.repository.PartnerRepository;

import java.util.List;
import java.util.Optional;

@Component
public class PartnerGateway {

    private final PartnerRepository repository;

    public PartnerGateway(PartnerRepository repository) {
        this.repository = repository;
    }

    public Partner save(Partner partner) {
        return this.repository.save(partner);
    }

    public List<Partner> findAll() {
        return this.repository.findAll();
    }

    public Optional<Partner> findByDocument(String document) {
        return this.repository.findByDocument(document);
    }
}
