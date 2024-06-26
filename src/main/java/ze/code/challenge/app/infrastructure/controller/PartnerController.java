package ze.code.challenge.app.infrastructure.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Partner Controller", description = "This controller hosts all endpoints for this API")
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

    @GetMapping("/nearest")
    @Operation(summary = "Get a list of nearest partners", description = "Given a specific location (coordinates long and lat), search the nearest partner which the coverage area includes the location.")
    public ResponseEntity<?> getCoverageArea(@RequestParam double longitude, @RequestParam double latitude) {
        return ResponseEntity.ok(searchPartnerByCoordinates.execute(longitude, latitude));
    }

    @PostMapping
    @Operation(summary = "Create a partner", description = "Create a new Partner")
    @ApiResponse(description = "This method returns DuplicateKeyException if the Document already exists", responseCode = "400", content = @Content(mediaType = "application/json"))
    @ApiResponse(responseCode = "201", description = "This method returns the created Partner", content = @Content(mediaType = "application/json"))
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Example of Partner",
            content = @Content(schema = @Schema(implementation = Partner.class),
            examples = @ExampleObject(value = "{\"tradingName\": \"Adega da Cerveja - Pinheiros\", \"ownerName\": \"Zé da Silva\", \"document\": \"1432132123891/0001\", \"coverageArea\": { \"type\": \"MultiPolygon\", \"coordinates\": [[[[30, 20], [45, 40], [10, 40], [30, 20]]], [[[15, 5], [40, 10], [10, 20], [5, 10], [15, 5]]]] }, \"address\": { \"type\": \"Point\", \"coordinates\": [-46.57421, -21.785741] }}")))
    public ResponseEntity<?> post(@RequestBody @Valid PartnerRequestDTO partnerRequestDTO) {

        if(getPartnerByDocument.execute(partnerRequestDTO.document()).isPresent()) {
            throw new DuplicateKeyException("Document already exists");
        }

        var response = createPartner.execute(partnerRequestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
