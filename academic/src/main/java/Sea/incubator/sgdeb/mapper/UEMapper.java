package Sea.incubator.sgdeb.mapper;

import Sea.incubator.sgdeb.dto.UEDTO;
import Sea.incubator.sgdeb.model.UE;
import org.springframework.stereotype.Component;

/**
 * The type Ue mapper.
 */
@Component
public class UEMapper {
    private final GrilleMapper grilleMapper;

    /**
     * Instantiates a new Ue mapper.
     *
     * @param grilleMapper the grille mapper
     */
    public UEMapper(GrilleMapper grilleMapper) {
        this.grilleMapper = grilleMapper;

    }

    /**
     * To uedto uedto.
     *
     * @param ue the ue
     * @return the uedto
     */
    public  UEDTO toUEDTO(UE ue) {
        if (ue == null) {
            return null;
        }

        UEDTO ueDTO = new UEDTO();
        ueDTO.setId(ue.getId());
        ueDTO.setLibelle(ue.getLibelle());
        ueDTO.setCode(ue.getCode());
        ueDTO.setCredits(ue.getCredits());
        ueDTO.setVolumeHoraire(ue.getVolumeHoraire());
        ueDTO.setSemestre(ue.getSemestre());
        ueDTO.setTypeUE(ue.getTypeUE());
        ueDTO.setPourcentageTp(ue.getPourcentageTp());
        ueDTO.setPourcentageCC(ue.getPourcentageCC());
        ueDTO.setPourcentageEE(ue.getPourcentageSN());
        ueDTO.setGrille(grilleMapper.toDto(ue.getGrille()));

        return ueDTO;
    }

    /**
     * To ue ue.
     *
     * @param ueDTO the ue dto
     * @return the ue
     */
    public  UE toUE(UEDTO ueDTO) {
        if (ueDTO == null) {
            return null;
        }

        UE ue = new UE();
        ue.setId(ueDTO.getId());
        ue.setLibelle(ueDTO.getLibelle());
        ue.setCode(ueDTO.getCode());
        ue.setCredits(ueDTO.getCredits());
        ue.setVolumeHoraire(ueDTO.getVolumeHoraire());
        ue.setSemestre(ueDTO.getSemestre());
        ue.setTypeUE(ueDTO.getTypeUE());
        ue.setPourcentageTp(ueDTO.getPourcentageTp());
        ue.setPourcentageCC(ueDTO.getPourcentageCC());
        ue.setPourcentageSN(ueDTO.getPourcentageEE());
        ue.setGrille(grilleMapper.toEntity(ueDTO.getGrille()));


        return ue;
    }


}
