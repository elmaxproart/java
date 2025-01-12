package Sea.incubator.sgdeb.mapper;

import Sea.incubator.sgdeb.dto.GrilleDTO;
import Sea.incubator.sgdeb.model.Grille;
import org.springframework.stereotype.Component;

/**
 * The type Grille mapper.
 */
@Component
public class GrilleMapper {

    private  final FiliereMapper filiereMapper;
    private  final AnneeAcademicMapper anneeAcademicMapper;

    /**
     * Instantiates a new Grille mapper.
     *
     * @param filiereMapper       the filiere mapper
     * @param anneeAcademicMapper the annee academic mapper
     */
    public GrilleMapper(FiliereMapper filiereMapper, AnneeAcademicMapper anneeAcademicMapper) {
        this.filiereMapper = filiereMapper;
        this.anneeAcademicMapper = anneeAcademicMapper;
    }

    /**
     * To dto grille dto.
     *
     * @param grille the grille
     * @return the grille dto
     */
    public  GrilleDTO toDto(Grille grille) {
        if (grille == null) {
            return null;
        }

        GrilleDTO grilleDTO = new GrilleDTO();
        grilleDTO.setId(grille.getId());
        grilleDTO.setLibelle(grille.getLibelle());
        grilleDTO.setFiliere(filiereMapper.toDto(grille.getFiliere()));
        grilleDTO.setAnneeAcademic(anneeAcademicMapper.toDto(grille.getAnneeAcademic()));
        grilleDTO.setCode(grille.getCode());
        grilleDTO.setNiveau(grille.getNiveau());

        return grilleDTO;
    }

    /**
     * To entity grille.
     *
     * @param grilleDTO the grille dto
     * @return the grille
     */
    public  Grille toEntity(GrilleDTO grilleDTO) {
        if (grilleDTO == null) {
            return null;
        }

        Grille grille = new Grille();
        grille.setId(grilleDTO.getId());
        grille.setLibelle(grilleDTO.getLibelle());
        grille.setFiliere(filiereMapper.toEntity(grilleDTO.getFiliere()));
        grille.setAnneeAcademic(anneeAcademicMapper.toEntity(grilleDTO.getAnneeAcademic()));
        grille.setCode(grilleDTO.getCode());
        grille.setNiveau(grilleDTO.getNiveau());
        return grille;
    }


}
