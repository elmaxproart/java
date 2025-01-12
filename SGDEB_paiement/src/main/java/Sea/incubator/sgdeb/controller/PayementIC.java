package Sea.incubator.sgdeb.controller;

import Sea.incubator.sgdeb.model.PayementI;
import Sea.incubator.sgdeb.service.impl.PayementServiceImpl_Inscription;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/py/IPaiements")
public class PayementIC {

    private final PayementServiceImpl_Inscription payementServiceImplInscription;

    public PayementIC(PayementServiceImpl_Inscription payementServiceImplInscription) {
        this.payementServiceImplInscription = payementServiceImplInscription;
    }

    @PostMapping("/add/paiement")
    @Operation(summary = "Créer un paiement",
            description = "Cette méthode permet de créer un nouveau paiement.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Paiement créé avec succès"),
            @ApiResponse(responseCode = "400", description = "Erreur de validation des données")
    })
    public ResponseEntity<PayementI> createPayement(@RequestBody PayementI payementInscriptionDto) {
        PayementI payementSaved = payementServiceImplInscription.createPayement(payementInscriptionDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(payementSaved);
    }

    @GetMapping("/get/paiement")
    @Operation(summary = "Obtenir tous les paiements",
            description = "Cette méthode permet de récupérer la liste de tous les paiements.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste des paiements récupérée avec succès")
    })
    public List<PayementI> getPayement() {
        return payementServiceImplInscription.getAllPayement();
    }

    @GetMapping("/get/paiement/{id}")
    @Operation(summary = "Obtenir un paiement par ID",
            description = "Cette méthode permet de récupérer un paiement spécifique par son ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Paiement trouvé"),
            @ApiResponse(responseCode = "404", description = "Paiement non trouvé pour l'ID donné")
    })
    public ResponseEntity<PayementI> getPayementbyId(@Parameter(description = "ID du paiement à récupérer") @PathVariable long id) {
        PayementI payementInscriptionDto = payementServiceImplInscription.getPayementbyID(id);

        if (payementInscriptionDto != null) {
            return ResponseEntity.status(HttpStatus.OK).body(payementInscriptionDto);
        } else {
            System.err.println("Paiement non trouvé pour l'ID : " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping("/put/paiement/{id}")
    @Operation(summary = "Mettre à jour un paiement",
            description = "Cette méthode permet de mettre à jour un paiement existant par son ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Paiement mis à jour avec succès"),
            @ApiResponse(responseCode = "404", description = "Paiement non trouvé pour l'ID donné"),
            @ApiResponse(responseCode = "400", description = "Erreur de validation des données")
    })
    public ResponseEntity<PayementI> putPayementById(@Parameter(description = "ID du paiement à mettre à jour") @PathVariable long id,
                                                     @RequestBody PayementI payementInscriptionDto) {
        try {
            PayementI payementSaved = payementServiceImplInscription.updatePayement(id, payementInscriptionDto);
            return ResponseEntity.status(HttpStatus.OK).body(payementSaved);
        } catch (RuntimeException e) {
            throw new RuntimeException("Erreur de mise à jour du paiement");
        }
    }

    @DeleteMapping("/delect/paiement/{id}")
    @Operation(summary = "Supprimer un paiement",
            description = "Cette méthode permet de supprimer un paiement par son ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Paiement supprimé avec succès"),
            @ApiResponse(responseCode = "404", description = "Paiement non trouvé pour l'ID donné")
    })
    public ResponseEntity<PayementI> delectPayement(@Parameter(description = "ID du paiement à supprimer") @PathVariable long id) {
        try {
            payementServiceImplInscription.delectPayement(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (RuntimeException e) {
            throw new RuntimeException("Erreur de suppression");
        }
    }

    @GetMapping("/get/{code}")
    @Operation(summary = "Obtenir un paiement par code",
            description = "Cette méthode permet de récupérer un paiement spécifique par son code.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Paiement trouvé"),
            @ApiResponse(responseCode = "404", description = "Paiement non trouvé pour le code donné")
    })
    public ResponseEntity<PayementI> getPayementbyCode(@Parameter(description = "Code du paiement à récupérer") @PathVariable long code) {
        PayementI payementInscriptionDto = payementServiceImplInscription.getPayementbycode(code);

        if (payementInscriptionDto != null) {
            return ResponseEntity.status(HttpStatus.OK).body(payementInscriptionDto);
        } else {
            System.err.println("Paiement non trouvé pour le code : " + code);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}