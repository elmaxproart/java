package Sea.incubator.sgdeb.controller;

import Sea.incubator.sgdeb.dto.PayementPDTO;
import Sea.incubator.sgdeb.model.PayementP;
import Sea.incubator.sgdeb.service.impl.PayementServiceImpl_Prescription;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/py/PPaiements")
public class PayementPC {

    private final PayementServiceImpl_Prescription payementService;

    public PayementPC(PayementServiceImpl_Prescription payementService) {
        this.payementService = payementService;
    }

    @PostMapping("/add")
    @Operation(summary = "Créer un paiement",
            description = "Cette méthode permet de créer un nouveau paiement.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Paiement créé avec succès"),
            @ApiResponse(responseCode = "400", description = "Erreur de validation des données")
    })
    public ResponseEntity<PayementP> createPayement(@RequestBody PayementPDTO payementInscriptionDto) {
        PayementP payementSaved = payementService.createPayement(payementInscriptionDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(payementSaved);
    }

    @GetMapping("/get/paiement")
    @Operation(summary = "Obtenir tous les paiements",
            description = "Cette méthode permet de récupérer la liste de tous les paiements.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste des paiements récupérée avec succès")
    })
    public List<PayementP> getPayement() {
        return payementService.getAllPayement();
    }

    @GetMapping("/get/paiement/{id}")
    @Operation(summary = "Obtenir un paiement par ID",
            description = "Cette méthode permet de récupérer un paiement spécifique par son ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Paiement trouvé"),
            @ApiResponse(responseCode = "404", description = "Paiement non trouvé pour l'ID donné")
    })
    public ResponseEntity<PayementP> getPayementbyId(@Parameter(description = "ID du paiement à récupérer") @PathVariable long id) {
        PayementP payementInscriptionDto = payementService.getPayementbyID(id);

        if (payementInscriptionDto != null) {
            return ResponseEntity.status(HttpStatus.OK).body(payementInscriptionDto);
        } else {
            System.err.println("Paiement non trouvé pour l'ID : " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


    @GetMapping("/verifier")
    @Operation(summary = "Vérifier un paiement",
            description = "Cette méthode permet de vérifier un paiement par son code bancaire.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Paiement vérifié avec succès"),
            @ApiResponse(responseCode = "404", description = "Paiement non trouvé pour le code bancaire donné")
    })
    public ResponseEntity<PayementP> verifierPaiement(@RequestParam("codeBank") String codeBank) {
        try {
            codeBank = codeBank.replace(" ", "");
            PayementP paiement = payementService.verificationPayementPrescription(codeBank);
            return ResponseEntity.ok(paiement);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}