package ze.code.challenge.app.infrastructure.controller;

import jakarta.validation.Valid;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ze.code.challenge.app.entity.Partner;
import ze.code.challenge.app.infrastructure.controller.dto.PartnerRequestDTO;
import ze.code.challenge.app.usecase.*;

import java.util.Optional;

@RestController
@RequestMapping("/partners")
public class PartnerController {

    private final CreatePartner createPartner;
    private final GetPartners getPartners;
    private final GetPartnerByDocument getPartnerByDocument;
    private final GetPartnerById getPartnerById;
    private final SearchPartnerByCoordinates searchPartnerByCoordinates;

    public PartnerController(CreatePartner createPartner, GetPartners getPartners, GetPartnerByDocument getPartnerByDocument, GetPartnerById getPartnerById, SearchPartnerByCoordinates searchPartnerByCoordinates) {
        this.createPartner = createPartner;
        this.getPartners = getPartners;
        this.getPartnerByDocument = getPartnerByDocument;
        this.getPartnerById = getPartnerById;
        this.searchPartnerByCoordinates = searchPartnerByCoordinates;
    }

    @GetMapping
    public ResponseEntity<?> get() {
        return ResponseEntity.ok(getPartners.execute());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable String id) {
        Optional<Partner> optionalPartner = getPartnerById.execute(id);

        if(optionalPartner.isPresent()) {
            return ResponseEntity.ok(optionalPartner.get());
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/search")
    public ResponseEntity<?> getCoverageArea(@RequestParam double longitude, @RequestParam double latitude) {
        return ResponseEntity.ok(searchPartnerByCoordinates.execute(longitude, latitude));
    }

    @PostMapping
    public ResponseEntity<?> post(@RequestBody @Valid PartnerRequestDTO partnerRequestDTO) {

        if(getPartnerByDocument.execute(partnerRequestDTO.document()).isPresent()) {
            throw new DuplicateKeyException("Document already exists");
        }

        var response = createPartner.execute(partnerRequestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
