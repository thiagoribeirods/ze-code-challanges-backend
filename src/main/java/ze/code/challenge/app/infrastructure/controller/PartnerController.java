package ze.code.challenge.app.infrastructure.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ze.code.challenge.app.entity.Partner;
import ze.code.challenge.app.infrastructure.controller.dto.PartnerRequestDTO;
import ze.code.challenge.app.usecase.CreatePartner;
import ze.code.challenge.app.usecase.GetPartners;

@RestController
@RequestMapping("/partners")
public class PartnerController {

    private final CreatePartner createPartner;
    private final GetPartners getPartners;

    public PartnerController(CreatePartner createPartner, GetPartners getPartners) {
        this.createPartner = createPartner;
        this.getPartners = getPartners;
    }

    @GetMapping
    public ResponseEntity<?> get() {
        return ResponseEntity.ok(getPartners.execute());
    }

    @PostMapping
    public ResponseEntity<?> post(@RequestBody @Valid PartnerRequestDTO partnerRequestDTO) {
        var response = createPartner.execute(partnerRequestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
