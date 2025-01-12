package Sea.incubator.sgdeb.controller;

import Sea.incubator.sgdeb.dto.GrilleDTO;
import Sea.incubator.sgdeb.service.FiliereService;
import Sea.incubator.sgdeb.service.GrilleService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/academics/grille")
public class GrilleController {
    private final FiliereService filiereService;
    private  final GrilleService grilleService;
    public GrilleController(FiliereService filiereService, GrilleService grilleService) {
        this.filiereService = filiereService;
        this.grilleService = grilleService;
    }

    @PostMapping("/add/{id}/{idA}")
    @Operation(summary = "Create a new Grille", description = "Creates a new Grille associated with a specific Filiere.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Grille created successfully"),
            @ApiResponse(responseCode = "404", description = "Filiere not found")
    })
    public ResponseEntity<GrilleDTO> createGrille(
            @RequestBody GrilleDTO grilleDTO,
            @Parameter(description = "ID of the Filiere to which the Grille is associated") @PathVariable UUID id,
            @Parameter(description = "ID of  Academic Year") @PathVariable UUID idA) {

        if (filiereService.getFiliere(id) != null) {
            GrilleDTO grilleDTO1 = grilleService.createGrille(grilleDTO, id,idA);
            return ResponseEntity.status(HttpStatus.CREATED).body(grilleDTO1);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/all")
    @Operation(summary = "Get all Grilles by Filiere ID", description = "Retrieves all Grilles associated with a specific Filiere.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Grilles retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Filiere not found")
    })
    public ResponseEntity<List<GrilleDTO>> getAllByFiliere(
            @Parameter(description = "ID of the Filiere") @RequestParam UUID id) {

        if (filiereService.getFiliere(id) != null) {
            return ResponseEntity.status(HttpStatus.OK).body(grilleService.getAllActiveByFiliere(id));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/{libelle}/libelleGet")
    @Operation(summary = "Get Grille by libelle", description = "Retrieves a Grille by its libelle.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Grille retrieved successfully"),
            @ApiResponse(responseCode = "204", description = "No content found")
    })
    public ResponseEntity<GrilleDTO> getFiliereByLibelle(
            @Parameter(description = "Libelle of the Grille to retrieve") @PathVariable String libelle) {

        if (grilleService.getByLibelle(libelle) != null) {
            return ResponseEntity.status(HttpStatus.OK).body(grilleService.getByLibelle(libelle));
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

    @GetMapping("/{id}/get")
    @Operation(summary = "Get Grille by ID", description = "Retrieves a Grille by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Grille retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Grille not found")
    })
    public ResponseEntity<GrilleDTO> getFiliere(
            @Parameter(description = "ID of the Grille to retrieve") @PathVariable UUID id) {

        if (grilleService.getById(id) != null) {
            return ResponseEntity.status(HttpStatus.OK).body(grilleService.getById(id));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/{id}/change")
    @Operation(summary = "Update an existing Grille", description = "Updates an existing Grille.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Grille updated successfully"),
            @ApiResponse(responseCode = "404", description = "Grille not found")
    })
    public ResponseEntity<GrilleDTO> updateGrille(
            @RequestBody GrilleDTO grilleDTO,
            @Parameter(description = "ID of the Grille to update") @PathVariable UUID id) {

        if (grilleService.getById(id) != null) {
            return ResponseEntity.status(HttpStatus.OK).body(grilleService.updateGrille(grilleDTO, id));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PatchMapping("{id}/change-libelle")
    @Operation(summary = "Update Grille libelle", description = "Updates the libelle of an existing Grille.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Grille libelle updated successfully"),
            @ApiResponse(responseCode = "404", description = "Grille not found")
    })
    public ResponseEntity<GrilleDTO> updateGrilleLibelle(
            @RequestBody GrilleDTO grilleDTO,
            @Parameter(description = "ID of the Grille to update") @PathVariable UUID id) {

        if (grilleService.getById(id) != null) {
            return ResponseEntity.status(HttpStatus.OK).body(grilleService.updateLibelle(grilleDTO, id));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PatchMapping("{id}/change-code")
    @Operation(summary = "Update Grille code", description = "Updates the code of an existing Grille.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Grille code updated successfully"),
            @ApiResponse(responseCode = "404", description = "Grille not found")
    })
    public ResponseEntity<GrilleDTO> updateCodeLibelle(
            @RequestBody GrilleDTO grilleDTO,
            @Parameter(description = "ID of the Grille to update") @PathVariable UUID id) {

        if (grilleService.getById(id) != null) {
            return ResponseEntity.status(HttpStatus.OK).body(grilleService.updateCode(grilleDTO, id));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    @PatchMapping("/{grille_id}/change-academic-year")
    @Operation(summary = "Update Grille Academic year", description = "Updates the actual academic year with an existing academic year of an existing Grille.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Grille code updated successfully"),
            @ApiResponse(responseCode = "404", description = "Grille not found or ")
    })
    public ResponseEntity<GrilleDTO> updateAcademicYear(@Parameter(description = "id of grille")@PathVariable UUID grille_id,@Parameter(description = "id of an existing academic year")@RequestParam UUID anne_id){
        GrilleDTO grilleDTO=grilleService.updateAnneeAcademic(grille_id,anne_id);
        if(grilleDTO!=null)
            return new ResponseEntity<>(grilleDTO,HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("{id}/delete")
    @Operation(summary = "Delete a Grille", description = "Deletes an existing Grille by ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Grille deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Grille not found")
    })
    public ResponseEntity<String> deleteGrille(
            @Parameter(description = "ID of the Grille to delete") @PathVariable UUID id) {

        if (grilleService.deleteGrille(id)) {
            return ResponseEntity.status(HttpStatus.OK).body("{\"message\" : \"The operation of deleting was successful\"}");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"message\" : \"Grille doesn't exist\"}");
        }
    }

    
    

}
