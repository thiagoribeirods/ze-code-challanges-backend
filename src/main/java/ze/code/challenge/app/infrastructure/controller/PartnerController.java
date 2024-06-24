package ze.code.challenge.app.infrastructure.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    public PartnerController(CreatePartner createPartner,
                             GetPartners getPartners,
                             GetPartnerByDocument getPartnerByDocument,
                             GetPartnerById getPartnerById,
                             SearchPartnerByCoordinates searchPartnerByCoordinates) {
        this.createPartner = createPartner;
        this.getPartners = getPartners;
        this.getPartnerByDocument = getPartnerByDocument;
        this.getPartnerById = getPartnerById;
        this.searchPartnerByCoordinates = searchPartnerByCoordinates;
    }

    @GetMapping
    @Operation(summary = "Get partners", description = "Get list of partners")
    @ApiResponse(description = "This method returns a list of Partners",
                 responseCode = "200",
                 content = @Content(mediaType = "application/json"))
    public ResponseEntity<?> get() {
        return ResponseEntity.ok(getPartners.execute());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get partner", description = "Get partner by ID if it exists")
    @ApiResponse(description = "This method returns a Partner", responseCode = "200", content = @Content(mediaType = "application/json"))
    @ApiResponse(description = "This method returns not found if Partner does not exists", responseCode = "404", content = @Content(mediaType = "application/json"))
    public ResponseEntity<?> get(@PathVariable String id) {
        Optional<Partner> optionalPartner = getPartnerById.execute(id);

        if(optionalPartner.isPresent()) {
            return ResponseEntity.ok(optionalPartner.get());
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/search")
    @Operation(summary = "Get a list of nearest partners", description = "Given a specific location (coordinates long and lat), search the nearest partner which the coverage area includes the location.")
    public ResponseEntity<?> getCoverageArea(@RequestParam double longitude, @RequestParam double latitude) {
        return ResponseEntity.ok(searchPartnerByCoordinates.execute(longitude, latitude));
    }

    @PostMapping
    @Operation(summary = "Create a partner", description = "Create a new Partner")
    @ApiResponse(description = "This method returns DuplicateKeyException if the Document already exists", responseCode = "400", content = @Content(mediaType = "application/json"))
    @ApiResponse(description = "This method returns the created Partner", responseCode = "201", content = @Content(mediaType = "application/json"))
    public ResponseEntity<?> post(@RequestBody @Valid PartnerRequestDTO partnerRequestDTO) {

        if(getPartnerByDocument.execute(partnerRequestDTO.document()).isPresent()) {
            throw new DuplicateKeyException("Document already exists");
        }

        var response = createPartner.execute(partnerRequestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
