package ze.code.challenge.app.infrastructure.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ze.code.challenge.app.entity.Partner;

@Repository
public interface PartnerRepository extends MongoRepository<Partner, Long> {
}
