package ze.code.challenge.app.gateway;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import ze.code.challenge.app.entity.Partner;
import ze.code.challenge.app.infrastructure.repository.PartnerRepository;

import java.util.List;
import java.util.Optional;

@Component
public class PartnerGateway {

    private final PartnerRepository repository;
    private final MongoTemplate mongoTemplate;

    public PartnerGateway(PartnerRepository repository, MongoTemplate mongoTemplate) {
        this.repository = repository;
        this.mongoTemplate = mongoTemplate;
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

    public Optional<Partner> findById(String id) {
        return this.repository.findById(id);
    }

    public List<Partner> findByCoverageArea(GeoJsonPoint coordinates) {
        Query query = new Query();

        query.addCriteria(Criteria.where("coverageArea").intersects(coordinates));

        return mongoTemplate.find(query, Partner.class);
    }
}
