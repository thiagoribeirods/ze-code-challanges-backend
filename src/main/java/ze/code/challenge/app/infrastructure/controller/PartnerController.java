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
import ze.code.challenge.app.usecase.GetPartners;

@RestController
@RequestMapping("/partners")
public class PartnerController {

    private final CreatePartner createPartner;
    private final GetPartners getPartners;
    private final GetPartnerByDocument getPartnerByDocument;

    public PartnerController(CreatePartner createPartner, GetPartners getPartners, GetPartnerByDocument getPartnerByDocument) {
        this.createPartner = createPartner;
        this.getPartners = getPartners;
        this.getPartnerByDocument = getPartnerByDocument;
    }

    @GetMapping
    public ResponseEntity<?> get() {
        return ResponseEntity.ok(getPartners.execute());
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
