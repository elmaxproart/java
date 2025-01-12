package Sea.incubator.sgdeb.controller;

import Sea.incubator.sgdeb.dto.UEDTO;
import Sea.incubator.sgdeb.service.UEService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import java.util.UUID;
import Sea.incubator.sgdeb.model.UE;

@RestController
@RequestMapping("/api/academics/ues")
public class UEController {

    private final UEService ueService;


    public UEController(UEService ueService) {
        this.ueService = ueService;
    }

    @PostMapping("/add/{grilleId}")
    @Operation(summary = "Create a new UE", description = "Creates a new UE associated with a specific Grille.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "UE created successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    public ResponseEntity<UEDTO> createUE(
            @RequestBody UEDTO ueDTO,
            @Parameter(description = "ID of the Grille to which the UE is associated") @PathVariable UUID grilleId) {

        UEDTO createdUE = ueService.createUE(ueDTO, grilleId);
        if (createdUE != null) {
            return new ResponseEntity<>(createdUE, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get UE by ID", description = "Retrieves a UE by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "UE retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "UE not found")
    })
    public ResponseEntity<UEDTO> getUE(
            @Parameter(description = "ID of the UE to retrieve") @PathVariable UUID id) {

        UEDTO ueDTO = ueService.getUE(id);
        return new ResponseEntity<>(ueDTO, HttpStatus.OK);
    }

    @GetMapping("/code/{code}")
    @Operation(summary = "Get UE by code", description = "Retrieves a UE by its code.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "UE retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "UE not found")
    })
    public ResponseEntity<UEDTO> getUEByCode(
            @Parameter(description = "Code of the UE to retrieve") @PathVariable String code) {

        UEDTO ueDTO = ueService.getUE(code);
        return new ResponseEntity<>(ueDTO, HttpStatus.OK);
    }

    @GetMapping("/grille/{grilleId}")
    @Operation(summary = "Get all UEs by Grille ID", description = "Retrieves all UEs associated with a specific Grille.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "UEs retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Grille not found")
    })
    public ResponseEntity<List<UEDTO>> getAllUEByGrille(
            @Parameter(description = "ID of the Grille") @PathVariable UUID grilleId) {

        List<UEDTO> ues = ueService.getAllActiveUEByGrille(grilleId);
        return new ResponseEntity<>(ues, HttpStatus.OK);
    }

    @PutMapping("/{ueId}/change")
    @Operation(summary = "Update an existing UE", description = "Updates an existing UE.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "UE updated successfully"),
            @ApiResponse(responseCode = "404", description = "UE not found")
    })
    public ResponseEntity<UEDTO> updateUE(
            @RequestBody UEDTO ueDTO,
            @Parameter(description = "ID of the UE to update") @PathVariable UUID ueId) {

        UEDTO updatedUE = ueService.updateUE(ueDTO, ueId);
        return new ResponseEntity<>(updatedUE, HttpStatus.OK);
    }

    @PatchMapping("/{ueId}/change-libelle")
    @Operation(summary = "Update UE libelle", description = "Updates the libelle of an existing UE.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "UE libelle updated successfully"),
            @ApiResponse(responseCode = "404", description = "UE not found")
    })
    public ResponseEntity<UEDTO> updateLibelle(
            @RequestBody UEDTO ueDTO,
            @Parameter(description = "ID of the UE to update") @PathVariable UUID ueId) {

        UEDTO updatedUE = ueService.updateLibelle(ueDTO, ueId);
        return new ResponseEntity<>(updatedUE, HttpStatus.OK);
    }

    @PatchMapping("/{ueId}/change-code")
    @Operation(summary = "Update UE code", description = "Updates the code of an existing UE.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "UE code updated successfully"),
            @ApiResponse(responseCode = "404", description = "UE not found")
    })
    public ResponseEntity<UEDTO> updateCode(
            @RequestBody UEDTO ueDTO,
            @Parameter(description = "ID of the UE to update") @PathVariable UUID ueId) {

        UEDTO updatedUE = ueService.updateCode(ueDTO, ueId);
        return new ResponseEntity<>(updatedUE, HttpStatus.OK);
    }

    @DeleteMapping("/{ueId}/delete")
    @Operation(summary = "Delete a UE", description = "Deletes an existing UE by ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "UE deleted successfully"),
            @ApiResponse(responseCode = "404", description = "UE not found")
    })
    public ResponseEntity<String> deleteUE(
            @Parameter(description = "ID of the UE to delete") @PathVariable UUID ueId) {

        boolean isDeleted = ueService.deleteUE(ueId);
        if (isDeleted) {
            return ResponseEntity.status(HttpStatus.OK).body("{\"message\":\"UE deleted successfully\"}");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"message\":\"UE not found\"}");
        }
    }



}
