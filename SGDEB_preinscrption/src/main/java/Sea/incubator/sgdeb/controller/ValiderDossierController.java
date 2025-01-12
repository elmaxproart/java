package Sea.incubator.sgdeb.controller;

import Sea.incubator.sgdeb.model.DossierCandidat;
import Sea.incubator.sgdeb.model.ValiderDossier;
import Sea.incubator.sgdeb.service.impl.PreinscriptionServiceImpl;
import Sea.incubator.sgdeb.service.impl.ValiderDossierService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/p/validations")
public class ValiderDossierController {
    private final ValiderDossierService validerDossierService;
    private final PreinscriptionServiceImpl preinscriptionService;

    @Autowired
    public ValiderDossierController(ValiderDossierService validerDossierService, PreinscriptionServiceImpl preinscriptionService) {
        this.validerDossierService = validerDossierService;
        this.preinscriptionService = preinscriptionService;
    }

    @Operation(summary = "Créer une validation de dossier", description = "Ajoute une validation pour un dossier.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Validation créée avec succès"),
            @ApiResponse(responseCode = "400", description = "Requête invalide"),
            @ApiResponse(responseCode = "500", description = "Erreur interne lors de la création")
    })
    @PostMapping("/validation/{idDossierPhysique}")
    public ResponseEntity<ValiderDossier> createValidation(
            @PathVariable UUID idDossierPhysique,
            @RequestParam String codeBank,
            @RequestBody ValiderDossier validerDossier) {
        boolean valide = validerDossier.isValider();
        try {
            ValiderDossier createdValidation = validerDossierService.createValidation( idDossierPhysique, codeBank, validerDossier, valide);
            return new ResponseEntity<>(createdValidation, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            throw new RuntimeException("Erreur lors de la création de la validation : " + e.getMessage());
        }
    }

    @Operation(summary = "Récupérer les validations par dossier", description = "Retourne la liste des validations associées aux dossiers.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Validations récupérées avec succès"),
            @ApiResponse(responseCode = "404", description = "Aucune validation trouvée")
    })
    @GetMapping("validation/dossierValider/")
    public ResponseEntity<List<ValiderDossier>> getValidationByDossier() {
        List<DossierCandidat> candidats = new ArrayList<>();
        candidats.add(new DossierCandidat());

        preinscriptionService.filtrerCandidats(candidats);
        return new ResponseEntity<>(validerDossierService.getValidationByDossier(candidats), HttpStatus.OK);
    }

    // Les méthodes PUT et DELETE peuvent également être documentées de la même manière.
}