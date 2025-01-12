package Sea.incubator.sgdeb.controller;

import Sea.incubator.sgdeb.dto.AnneeAcademicDto;
import Sea.incubator.sgdeb.service.AnneeAcademicService;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;


import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/academic/annee-academic")
public class AnneeAcademicController {

    private final AnneeAcademicService anneeAcademicService;


    public AnneeAcademicController(AnneeAcademicService anneeAcademicService) {
        this.anneeAcademicService = anneeAcademicService;
    }

    @PostMapping("/add")
    @Operation(summary = "Create a new Annee Academic", description = "Creates a new academic year.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Annee Academic created successfully")
    })
    public ResponseEntity<AnneeAcademicDto> createAnneeAcademic(
            @RequestBody AnneeAcademicDto anneeAcademicDto) {

        AnneeAcademicDto createdAnnee = anneeAcademicService.createAnneeAcademic(anneeAcademicDto);
        return new ResponseEntity<>(createdAnnee, HttpStatus.CREATED);
    }

    @GetMapping("/{id}/get")
    @Operation(summary = "Get Annee Academic by ID", description = "Retrieves an academic year by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Annee Academic retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Annee Academic not found")
    })
    public ResponseEntity<AnneeAcademicDto> getAnneeAcademic(
            @Parameter(description = "ID of the Annee Academic to retrieve") @PathVariable UUID id) {

        try {
            AnneeAcademicDto anneeAcademicDto = anneeAcademicService.getAnneeAcademic(id);
            return new ResponseEntity<>(anneeAcademicDto, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/all")
    @Operation(summary = "Get all Annees Academiques", description = "Retrieves a list of all academic years.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Annees Academiques retrieved successfully")
    })
    public ResponseEntity<List<AnneeAcademicDto>> getAllAnneesAcademiques() {
        List<AnneeAcademicDto> anneesAcademiques = anneeAcademicService.getAllActiveAnneeAcademic();
        return new ResponseEntity<>(anneesAcademiques, HttpStatus.OK);
    }

    @PutMapping("/{id}/change")
    @Operation(summary = "Update an existing Annee Academic", description = "Updates an existing academic year.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Annee Academic updated successfully"),
            @ApiResponse(responseCode = "404", description = "Annee Academic not found")
    })
    public ResponseEntity<AnneeAcademicDto> updateAnneeAcademic(
            @Parameter(description = "ID of the Annee Academic to update") @PathVariable UUID id,
            @RequestBody AnneeAcademicDto anneeAcademicDto) {

        try {
            AnneeAcademicDto updatedAnnee = anneeAcademicService.updateAnneeAcademic(id, anneeAcademicDto);
            return new ResponseEntity<>(updatedAnnee, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}/delete")
    @Operation(summary = "Delete an Annee Academic", description = "Deletes an existing academic year by ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Annee Academic deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Annee Academic not found")
    })
    public ResponseEntity<String> deleteAnneeAcademic(
            @Parameter(description = "ID of the Annee Academic to delete") @PathVariable UUID id) {

        boolean isDeleted = anneeAcademicService.deleteAnneeAcademic(id);
        if (isDeleted) {
            return new ResponseEntity<>("Annee Academic deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Annee Academic not found", HttpStatus.NOT_FOUND);
        }
    }
}
