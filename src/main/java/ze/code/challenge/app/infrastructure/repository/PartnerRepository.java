package ze.code.challenge.app.infrastructure.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import ze.code.challenge.app.entity.Partner;

import java.util.List;
import java.util.Optional;

@Repository
public interface PartnerRepository extends MongoRepository<Partner, String> {

    Optional<Partner> findByDocument(String document);

}
