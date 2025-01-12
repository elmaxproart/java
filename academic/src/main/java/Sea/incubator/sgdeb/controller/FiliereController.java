package Sea.incubator.sgdeb.controller;


import Sea.incubator.sgdeb.dto.FiliereDTO;
import Sea.incubator.sgdeb.model.enumType.Faculte;
import Sea.incubator.sgdeb.service.DepartementService;
import Sea.incubator.sgdeb.service.FiliereService;
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
@RequestMapping("/api/academics/filieres")
public class FiliereController {
    private final DepartementService departementService;
    private final FiliereService filiereService;

    public FiliereController(DepartementService departementService, FiliereService filiereService) {
        this.departementService = departementService;
        this.filiereService = filiereService;
    }

    @PostMapping("/add/{id}")
    @Operation(summary = "Create a new Filiere", description = "Creates a new Filiere associated with a specific Departement.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Filiere created successfully"),
            @ApiResponse(responseCode = "404", description = "Departement not found")
    })
    public ResponseEntity<FiliereDTO> createFiliere(
            @RequestBody FiliereDTO filiereDTO,
            @Parameter(description = "ID of the Departement to which the Filiere is associated") @PathVariable UUID id) {

        if (departementService.getDepartement(id) != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(filiereService.createFiliere(filiereDTO, id));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/all")
    @Operation(summary = "Get all Filieres", description = "Retrieves a list of all Filieres.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Filieres retrieved successfully")
    })
    public ResponseEntity<List<FiliereDTO>> getAllList() {
        return ResponseEntity.status(HttpStatus.OK).body(filiereService.getAllFiliere());
    }

    @GetMapping("/all/bydepartement")
    @Operation(summary = "Get all Filieres by Departement ID", description = "Retrieves all Filieres associated with a specific Departement.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Filieres retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Departement not found")
    })
    public ResponseEntity<List<FiliereDTO>> getAllByDepartement(
            @Parameter(description = "ID of the Departement") @RequestParam UUID id) {

        if (departementService.getDepartement(id) != null) {
            return ResponseEntity.status(HttpStatus.OK).body(filiereService.getAllAsMarkedNotDeleted(id));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/{code}/codefil")
    @Operation(summary = "Get Filiere by code", description = "Retrieves a Filiere by its code.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Filiere retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Filiere not found")
    })
    public ResponseEntity<FiliereDTO> getFiliereByCode(
            @Parameter(description = "Code of the Filiere to retrieve") @PathVariable String code) {

        if (filiereService.getFiliere(code) != null) {
            return ResponseEntity.status(HttpStatus.OK).body(filiereService.getFiliere(code));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Filiere by ID", description = "Retrieves a Filiere by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Filiere retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Filiere not found")
    })
    public ResponseEntity<FiliereDTO> getFiliere(
            @Parameter(description = "ID of the Filiere to retrieve") @PathVariable UUID id) {

        if (filiereService.getFiliere(id) != null) {
            return ResponseEntity.status(HttpStatus.OK).body(filiereService.getFiliere(id));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/{libelle}/libellefil")
    @Operation(summary = "Get Filiere by libelle", description = "Retrieves a Filiere by its libelle.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Filiere retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Filiere not found")
    })
    public ResponseEntity<FiliereDTO> getFiliereByLibelle(
            @Parameter(description = "Libelle of the Filiere to retrieve") @PathVariable String libelle) {

        if (filiereService.getFiliereByLibelle(libelle) != null) {
            return ResponseEntity.status(HttpStatus.OK).body(filiereService.getFiliereByLibelle(libelle));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/{id}/change")
    @Operation(summary = "Update an existing Filiere", description = "Updates an existing Filiere.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Filiere updated successfully"),
            @ApiResponse(responseCode = "404", description = "Filiere not found")
    })
    public ResponseEntity<FiliereDTO> updateFiliere(
            @RequestBody FiliereDTO newFiliere,
            @Parameter(description = "ID of the Filiere to update") @PathVariable UUID id) {

        if (filiereService.getFiliere(id) != null) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(filiereService.updateFiliere(newFiliere, id));
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

    @PatchMapping("/{id}/change_name")
    @Operation(summary = "Update Filiere name", description = "Updates the name of an existing Filiere.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Filiere name updated successfully"),
            @ApiResponse(responseCode = "404", description = "Filiere not found")
    })
    public ResponseEntity<FiliereDTO> updateLibelle(
            @RequestBody FiliereDTO filiereDTO,
            @Parameter(description = "ID of the Filiere to update") @PathVariable UUID id) {

        if (filiereService.getFiliere(id) != null) {
            return ResponseEntity.status(HttpStatus.OK).body(filiereService.updateName(filiereDTO, id));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PatchMapping("/{id}/change_code")
    @Operation(summary = "Update Filiere code", description = "Updates the code of an existing Filiere.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Filiere code updated successfully"),
            @ApiResponse(responseCode = "404", description = "Filiere not found")
    })
    public ResponseEntity<FiliereDTO> updateCode(
            @RequestBody FiliereDTO filiereDTO,
            @Parameter(description = "ID of the Filiere to update") @PathVariable UUID id) {

        if (filiereService.getFiliere(id) != null) {
            return ResponseEntity.status(HttpStatus.OK).body(filiereService.updateCode(filiereDTO, id));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    @PatchMapping("/{code}/change-effectif")
    @Operation(summary = "Update Filiere code", description = "Updates the effectif of an existing Filiere.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Filiere code updated successfully"),
            @ApiResponse(responseCode = "404", description = "Filiere not found")
    })
    public ResponseEntity<FiliereDTO> updateEffectif(
            @RequestBody FiliereDTO filiereDTO,
            @Parameter(description = "Code of the Filiere to update") @PathVariable String code) {

        if (filiereService.getFiliere(code) != null) {
            filiereService.updateEffectif(filiereDTO,code);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a Filiere", description = "Deletes an existing Filiere by ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Filiere deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Filiere not found")
    })
    public ResponseEntity<String> deleteFiliere(
            @Parameter(description = "ID of the Filiere to delete") @PathVariable UUID id) {

        if (filiereService.deleteFiliere(id)) {
            return ResponseEntity.status(HttpStatus.OK).body("{\"message\" : \"The operation of deleting was successful\"}");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"message\" : \"Filiere doesn't exist\"}");
        }
    }

    @GetMapping("/{faculte}/fac")
    @Operation(summary = "get all filiere",description = "get all by faculte")
    @ApiResponse(responseCode = "200",description = "get all filiere by name of faculte")
    public ResponseEntity<List<FiliereDTO>> getAllByFaculty(@Parameter(description = "the found name faculte") @PathVariable String faculte){
        return new ResponseEntity<>(filiereService.getAllByFaculte(faculte),HttpStatus.OK);
    }
    }

