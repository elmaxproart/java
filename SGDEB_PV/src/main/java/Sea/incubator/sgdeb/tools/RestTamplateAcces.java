package Sea.incubator.sgdeb.tools;

import Sea.incubator.sgdeb.external.*;
import lombok.AllArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor

@Service
public class RestTamplateAcces {
    private final RestTemplate restTemplate;

    public EtudiantDTO studentExitByMatricule(String matricule) {
        String url = "http://localhost:4000/api/i/etudiants/" + matricule;
        try {
            ResponseEntity<EtudiantDTO> response = restTemplate.getForEntity(url, EtudiantDTO.class);
            return response.getStatusCode() == HttpStatus.OK ? response.getBody() : null;
        } catch (HttpClientErrorException.NotFound e) {
            // Gestion de l'erreur si l'étudiant n'existe pas
            return null;
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la vérification de l'étudiant", e);
        }
    }
    public InscriptionDTO inscriptionExitByMatricul(String matricule) {
        String url = "http://localhost:4000/api/i/inscriptions/matricule/" + matricule;
        try {
            ResponseEntity<InscriptionDTO> response = restTemplate.getForEntity(url, InscriptionDTO.class);
            return response.getStatusCode() == HttpStatus.OK ? response.getBody() : null;
        } catch (HttpClientErrorException.NotFound e) {
            // Gestion de l'erreur si l'inscription n'existe pas ou si l'étudiant n'a pas payé
            return null;
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la vérification de l'inscription", e);
        }
    }


    //recupere les notes avec anonimat
    public List<NoteDTO> getAllGradeWithAnonymat() {
        String url = "http://localhost:8090/api/note/saisie/sansAnonimat";
        ResponseEntity<List<NoteDTO>> response = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<>() {
        });
        return response.getBody();
    }

    public List<NoteDTO> getAllGradeWith() {
        String url = "http://localhost:8090/api/note/saisie/sansAnonimat";
        ResponseEntity<List<NoteDTO>> response = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<>() {
        });
        return response.getBody();
    }

    public List<UEDTO> getAllUEWithGrille(UUID grilleId) {
        String url = "http://localhost:1111/api/academics/ues/grille/" + grilleId;
        ResponseEntity<List<UEDTO>> response = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<>() {
        });
        return response.getBody();
    }

    public GrilleDTO getGrille(String libelle) {
        String url = "http://localhost:1111/api/academics/grille/" + libelle + "/libelleGet";
        ResponseEntity<GrilleDTO> response = restTemplate.getForEntity(url, GrilleDTO.class);
        return response.getBody();
    }
}
