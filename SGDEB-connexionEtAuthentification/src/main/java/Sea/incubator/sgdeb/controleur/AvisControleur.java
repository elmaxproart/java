package Sea.incubator.sgdeb.controleur;

import Sea.incubator.sgdeb.entite.Avis;
import Sea.incubator.sgdeb.service.AvisService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RequestMapping("api/u/avis")
@RestController
public class AvisControleur {
    private final AvisService avisService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    @Operation(summary = "Créer un avis",
            description = "Permet de créer un nouvel avis.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Avis créé avec succès"),
            @ApiResponse(responseCode = "400", description = "Erreur lors de la création de l'avis")
    })
    public void creer(@RequestBody Avis avis) {
        this.avisService.creer(avis);
    }
}