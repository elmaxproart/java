package Sea.incubator.sgdeb.service;

import Sea.incubator.sgdeb.dto.GrilleDTO;
import Sea.incubator.sgdeb.model.Filiere;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * The interface Grille service.
 */
@Service
public interface GrilleService {
    /**
     * Create grille grille dto.
     *
     * @param grilleDTO les informations de la grille
     * @param id        de la filiere a laquelle elle appartient
     * @param idA       de l'annee academique
     * @return la grille creee
     */
    GrilleDTO createGrille(GrilleDTO grilleDTO, UUID id,UUID idA);

    /**
     * Gets all by filiere.
     *
     * @param id de la filiere a laquelle cette grille appartient
     * @return la liste des filieres par la filiere
     */
    List<GrilleDTO> getAllByFiliere(UUID id);
    List<GrilleDTO> getAllActiveByFiliere(UUID id);
    /**
     * Gets by libelle.
     *
     * @param libelle de la grille
     * @return la grille recherchee
     */
    GrilleDTO getByLibelle(String libelle);

    /**
     * }
     *
     * @param id de la grille
     * @return la grille recherchee
     */
    GrilleDTO getById(UUID id);

    /**
     * Update filiere grille dto.
     *
     * @param filiereDTO les nouvelles informations de la grille
     * @param id         de la grillle a changer
     * @return la nouvelle grille
     */
    GrilleDTO updateGrille(GrilleDTO filiereDTO, UUID id);

    /**
     * Update libelle grille dto.
     *
     * @param grilleDTO le nouveau nom y sera
     * @param id        de la filiere a modifier
     * @return la nouvelle grille
     */
    GrilleDTO updateLibelle (GrilleDTO grilleDTO, UUID id);

    /**
     * Update code grille dto.
     *
     * @param grilleDTO le nouveau code y sera
     * @param id        de la filiere a modifier
     * @return la nouvelle grille
     */
    GrilleDTO updateCode(GrilleDTO grilleDTO, UUID id);

    /**
     * Update annee academic grille dto.
     *
     * @param grille_id the grille id
     * @param annee_id  the new existing annee id
     * @return the grille dto
     */
    GrilleDTO updateAnneeAcademic(UUID grille_id,UUID annee_id);

    /**
     * Delete grille boolean.
     *
     * @param id de la filiere a supprimer
     * @return oui si la suppression est un succes et non s'il y a eu un probleme
     */
    boolean deleteGrille(UUID id);
    void markAsDeletedGrille(Filiere filiere);
}
