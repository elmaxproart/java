package Sea.incubator.sgdeb.controller;

import Sea.incubator.sgdeb.dto.DossierDTO;
import Sea.incubator.sgdeb.model.DossierCandidat;
import Sea.incubator.sgdeb.repository.DossierRepository;
import Sea.incubator.sgdeb.service.impl.DossierService;
import Sea.incubator.sgdeb.service.tools.DTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/p/dossiers")
public class DossierController {

    private final DossierService dossierService;
    private final DTO dto;
    private final DossierRepository dossierRepository;

    public DossierController(DossierService dossierService, DTO dto, DossierRepository dossierRepository) {
        this.dossierService = dossierService;
        this.dto = dto;
        this.dossierRepository = dossierRepository;
    }

    @Operation(summary = "Créer un dossier", description = "Ajoute un nouveau dossier.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Dossier créé avec succès"),
            @ApiResponse(responseCode = "400", description = "Requête invalide")
    })
    @PostMapping("/add")
    public ResponseEntity<DossierDTO> createDossier(@RequestBody DossierDTO dossierDTO) {
        DossierCandidat dossierCandidatEntity = dto.toEntity(dossierDTO);
        DossierCandidat savedDossierCandidat = dossierService.createDossier(dossierCandidatEntity);
        DossierDTO savedDossierDTO = dto.toDTO(savedDossierCandidat);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDossierDTO);
    }

    @Operation(summary = "Récupérer tous les dossiers", description = "Retourne la liste de tous les dossiers.")
    @GetMapping("/")
    public ResponseEntity<List<DossierDTO>> getDossiers() {
        List<DossierCandidat> dossierCandidats = dossierService.getAllDossiers();
        List<DossierDTO> dossierDTOs = dto.toDTOs(dossierCandidats);
        return ResponseEntity.ok(dossierDTOs);
    }

    @Operation(summary = "Récupérer un dossier par ID", description = "Retourne un dossier par son ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dossier trouvé"),
            @ApiResponse(responseCode = "404", description = "Dossier non trouvé")
    })
    @GetMapping("/{id}")
    public ResponseEntity<DossierDTO> getDossierById(@PathVariable UUID id) {
        DossierCandidat dossierCandidat = dossierService.getDossierById(id);
        if (dossierCandidat != null) {
            DossierDTO dossierDTO = dto.toDTO(dossierCandidat);
            return ResponseEntity.ok(dossierDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(summary = "Mettre à jour un dossier", description = "Met à jour un dossier existant.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dossier mis à jour"),
            @ApiResponse(responseCode = "404", description = "Dossier non trouvé"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    @PutMapping("/{id}")
    public ResponseEntity<DossierDTO> updateDossier(@PathVariable UUID id, @RequestBody DossierDTO dossierDTO) {
        try {
            DossierCandidat dossierCandidatEntity = dto.toEntity(dossierDTO);
            DossierCandidat updatedDossierCandidat = dossierService.updateDossier(id, dossierCandidatEntity);
            DossierDTO updatedDossierDTO = dto.toDTO(updatedDossierCandidat);
            return ResponseEntity.ok(updatedDossierDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Operation(summary = "Supprimer un dossier", description = "Supprime un dossier par son ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Dossier supprimé"),
            @ApiResponse(responseCode = "404", description = "Dossier non trouvé"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDossier(@PathVariable UUID id) {
        try {
            dossierService.deleteDossier(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Récupérer un dossier par code de préinscription", description = "Retourne un dossier par son code de préinscription.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dossier trouvé"),
            @ApiResponse(responseCode = "404", description = "Dossier non trouvé")
    })
    @GetMapping("/get/{code}")
    public ResponseEntity<DossierDTO> getDossierByCodePreinscription(@PathVariable long code) {
        DossierCandidat dossierCandidat = dossierService.getDossierByCodePreinscription(code);
        if (dossierCandidat != null) {
            DossierDTO dossierDTO = dto.toDTO(dossierCandidat);
            return ResponseEntity.ok(dossierDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(summary = "Récupérer les pourcentages d'admission par région", description = "Retourne les pourcentages d'admission par région.")
    @GetMapping("/percentages")
    public ResponseEntity<Map<String, Double>> getCandidatPercentages() {
        Map<String, Double> percentages = dossierService.calculateRegionPercentages();
        return ResponseEntity.ok(percentages);
    }
}