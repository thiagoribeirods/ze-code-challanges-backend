package ze.code.challenge.app.infrastructure.controller;

import jakarta.validation.Valid;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ze.code.challenge.app.entity.Partner;
import ze.code.challenge.app.infrastructure.controller.dto.PartnerRequestDTO;
import ze.code.challenge.app.usecase.CreatePartner;
import ze.code.challenge.app.usecase.GetPartnerByDocument;
import ze.code.challenge.app.usecase.GetPartnerById;
import ze.code.challenge.app.usecase.GetPartners;

import java.util.Optional;

@RestController
@RequestMapping("/partners")
public class PartnerController {

    private final CreatePartner createPartner;
    private final GetPartners getPartners;
    private final GetPartnerByDocument getPartnerByDocument;
    private final GetPartnerById getPartnerById;

    public PartnerController(CreatePartner createPartner, GetPartners getPartners, GetPartnerByDocument getPartnerByDocument, GetPartnerById getPartnerById) {
        this.createPartner = createPartner;
        this.getPartners = getPartners;
        this.getPartnerByDocument = getPartnerByDocument;
        this.getPartnerById = getPartnerById;
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

    @PostMapping
    public ResponseEntity<?> post(@RequestBody @Valid PartnerRequestDTO partnerRequestDTO) {

        if(getPartnerByDocument.execute(partnerRequestDTO.document()).isPresent()) {
            throw new DuplicateKeyException("Document already exists");
        }

        var response = createPartner.execute(partnerRequestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
