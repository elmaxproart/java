package Sea.incubator.sgdeb.controleur;

import Sea.incubator.sgdeb.dto.AuthentificationDTO;
import Sea.incubator.sgdeb.entite.Utilisateur;
import Sea.incubator.sgdeb.securite.JwtService;
import Sea.incubator.sgdeb.service.UtilisateurService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE , path = "api/u")
public class UtilisateurControleur {

    private AuthenticationManager authenticationManager;
    private UtilisateurService utilisateurService;
    private JwtService jwtService;

    /**
     * Inscription d'un nouvel utilisateur dans la base de données.
     *
     * Cette méthode permet d'inscrire un nouvel utilisateur dans la base de données.
     * Elle prend en paramètre un objet Utilisateur contenant les informations de l'utilisateur à inscrire,
     * et renvoie une réponse HTTP 201 si l'inscription est réussie, ou une réponse HTTP 400 si une erreur est rencontrée.
     *
     * @param utilisateur l'objet Utilisateur contenant les informations de l'utilisateur à inscrire
     * @return une réponse HTTP contenant un message de succès si l'inscription est réussie, ou un message d'erreur si une erreur est rencontrée
     */
    @PostMapping(path = "inscription")
    @Operation(summary = "Inscription d'un nouvel utilisateur",
            description = "Permet d'inscrire un nouvel utilisateur dans la base de données.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Inscription réussie"),
            @ApiResponse(responseCode = "400", description = "Erreur lors de l'inscription")
    })
    public ResponseEntity<String> inscription(@RequestBody Utilisateur utilisateur) {
        log.info("Inscription");
        try {
            this.utilisateurService.inscription(utilisateur);
            return new ResponseEntity<>("Inscription réussie", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Erreur lors de l'inscription : " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(path = "activation")
    @Operation(summary = "Activer un utilisateur",
            description = "Active un utilisateur à l'aide d'un code d'activation.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Activation réussie"),
            @ApiResponse(responseCode = "400", description = "Erreur lors de l'activation")
    })
    public ResponseEntity<String> activation(@RequestBody Map<String, String> activation) {
        try {
            this.utilisateurService.activation(activation);
            return new ResponseEntity<>("Activation réussie", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Erreur lors de l'activation : " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(path = "modifier-mot-de-passe")
    @Operation(summary = "Modifier le mot de passe",
            description = "Permet de modifier le mot de passe d'un utilisateur.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Mot de passe modifié avec succès"),
            @ApiResponse(responseCode = "400", description = "Erreur lors de la modification du mot de passe")
    })
    public ResponseEntity<String> modifierMotDePasse(@RequestBody Map<String, String> activation) {
        try {
            this.utilisateurService.modifierMotDePasse(activation);
            return new ResponseEntity<>("Mot de passe modifié avec succès", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Erreur lors de la modification du mot de passe : " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(path = "nouveau-mot-de-passe")
    @Operation(summary = "Créer un nouveau mot de passe",
            description = "Crée un nouveau mot de passe pour un utilisateur.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Nouveau mot de passe créé avec succès"),
            @ApiResponse(responseCode = "400", description = "Erreur lors de la création du nouveau mot de passe")
    })
    public ResponseEntity<String> nouveauMotDePasse(@RequestBody Map<String, String> activation) {
        try {
            this.utilisateurService.nouveauMotDePasse(activation);
            return new ResponseEntity<>("Nouveau mot de passe créé avec succès", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Erreur lors de la création du nouveau mot de passe : " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(path = "refresh-token")
    @Operation(summary = "Rafraîchir le token",
            description = "Permet de rafraîchir le token JWT.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Token rafraîchi avec succès")
    })
    public ResponseEntity<Map<String, String>> refreshToken(@RequestBody Map<String, String> refreshTokenRequest) {
        Map<String, String> newToken = this.jwtService.refreshToken(refreshTokenRequest);
        return new ResponseEntity<>(newToken, HttpStatus.OK);
    }

    @PostMapping(path = "deconnexion")
    @Operation(summary = "Déconnexion de l'utilisateur",
            description = "Permet de déconnecter l'utilisateur.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Déconnexion réussie")
    })
    public ResponseEntity<String> deconnexion() {
        this.jwtService.deconnexion();
        return new ResponseEntity<>("Déconnexion réussie", HttpStatus.OK);
    }

    @PostMapping(path = "connexion")
    @Operation(summary = "Connexion de l'utilisateur",
            description = "Permet à un utilisateur de se connecter et de recevoir un token JWT.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Connexion réussie"),
            @ApiResponse(responseCode = "401", description = "Échec de l'authentification")
    })
    public Map<String, String> connexion(@RequestBody AuthentificationDTO authentificationDTO) {
        final Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authentificationDTO.username(), authentificationDTO.password())
        );

        if (authenticate.isAuthenticated()) {
            return this.jwtService.generate(authentificationDTO.username());
        }
        return null;
    }
}