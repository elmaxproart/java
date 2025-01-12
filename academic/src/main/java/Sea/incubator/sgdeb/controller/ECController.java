package Sea.incubator.sgdeb.controller;

import Sea.incubator.sgdeb.dto.ECDTO;
import Sea.incubator.sgdeb.service.ECService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/academics/ecs")
public class ECController {
    private final ECService ecService;


    public ECController(ECService ecService) {
        this.ecService = ecService;
    }

    @PostMapping("/add/{ueId}")
    @Operation(summary = "Create a new EC", description = "Creates a new EC associated with a specific UE.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "EC created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public ResponseEntity<ECDTO> createEc(
            @RequestBody ECDTO ecDTO,
            @Parameter(description = "ID of the UE to which the EC is associated") @PathVariable UUID ueId) {
        ECDTO createdEC = ecService.createEc(ecDTO, ueId);
        return new ResponseEntity<>(createdEC, HttpStatus.CREATED);
    }

    @GetMapping("/{ecId}")
    @Operation(summary = "Get EC by ID", description = "Retrieves an EC by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "EC retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "EC not found")
    })
    public ResponseEntity<ECDTO> getEc(
            @Parameter(description = "ID of the EC to retrieve") @PathVariable UUID ecId) {
        ECDTO ecDTO = ecService.getEc(ecId);
        return new ResponseEntity<>(ecDTO, HttpStatus.OK);
    }

    @GetMapping("/{codeEc}/code")
    @Operation(summary = "Get EC by code", description = "Retrieves an EC by its code.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "EC retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "EC not found")
    })
    public ResponseEntity<ECDTO> getEcByCode(
            @Parameter(description = "Code of the EC to retrieve") @PathVariable String codeEc) {
        ECDTO ecDTO = ecService.getEc(codeEc);
        return new ResponseEntity<>(ecDTO, HttpStatus.OK);
    }

    @PutMapping("/{ecId}")
    @Operation(summary = "Update an existing EC", description = "Updates an existing EC.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "EC updated successfully"),
            @ApiResponse(responseCode = "404", description = "EC not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public ResponseEntity<ECDTO> updateEc(
            @RequestBody ECDTO ecDTO,
            @Parameter(description = "ID of the EC to update") @PathVariable UUID ecId) {
        ECDTO updatedEC = ecService.updateEc(ecDTO, ecId);
        return new ResponseEntity<>(updatedEC, HttpStatus.OK);
    }

    @PatchMapping("/{ecId}/change-nom")
    @Operation(summary = "Update EC name", description = "Updates the name of an existing EC.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "EC name updated successfully"),
            @ApiResponse(responseCode = "404", description = "EC not found")
    })
    public ResponseEntity<ECDTO> updateNomEc(
            @RequestBody ECDTO ecDTO,
            @Parameter(description = "ID of the EC to update") @PathVariable UUID ecId) {
        ECDTO updatedEC = ecService.updateNomEc(ecDTO, ecId);
        return new ResponseEntity<>(updatedEC, HttpStatus.OK);
    }

    @PatchMapping("/{ecId}/change-code")
    @Operation(summary = "Update EC code", description = "Updates the code of an existing EC.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "EC code updated successfully"),
            @ApiResponse(responseCode = "404", description = "EC not found")
    })
    public ResponseEntity<ECDTO> updateCode(
            @RequestBody ECDTO ecDTO,
            @Parameter(description = "ID of the EC to update") @PathVariable UUID ecId) {
        ECDTO updatedEC = ecService.updateCode(ecDTO, ecId);
        return new ResponseEntity<>(updatedEC, HttpStatus.OK);
    }

    @DeleteMapping("/{ecId}/delete")
    @Operation(summary = "Delete an EC", description = "Deletes an existing EC by ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "EC deleted successfully"),
            @ApiResponse(responseCode = "404", description = "EC not found")
    })
    public ResponseEntity<String> deleteEc(
            @Parameter(description = "ID of the EC to delete") @PathVariable UUID ecId) {
        boolean isDeleted = ecService.deleteEc(ecId);
        if (isDeleted) {
            return new ResponseEntity<>("EC deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("EC not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/all")
    @Operation(summary = "Get all ECs by UE ID", description = "Retrieves all ECs associated with a specific UE.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "ECs retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "UE not found")
    })
    public ResponseEntity<List<ECDTO>> getAllByUE(
            @Parameter(description = "ID of the UE") @RequestParam UUID ueId) {
        List<ECDTO> ecs = ecService.getAllActiveByUE(ueId);
        return new ResponseEntity<>(ecs, HttpStatus.OK);
    }
}
