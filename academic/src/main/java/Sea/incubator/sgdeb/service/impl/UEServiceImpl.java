package Sea.incubator.sgdeb.service.impl;

import Sea.incubator.sgdeb.dto.UEDTO;
import Sea.incubator.sgdeb.mapper.GrilleMapper;
import Sea.incubator.sgdeb.mapper.UEMapper;
import Sea.incubator.sgdeb.model.Grille;
import Sea.incubator.sgdeb.model.UE;
import Sea.incubator.sgdeb.repository.GrilleRepository;
import Sea.incubator.sgdeb.repository.UERepository;
import Sea.incubator.sgdeb.service.ECService;
import Sea.incubator.sgdeb.service.GrilleService;
import Sea.incubator.sgdeb.service.UEService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * The type Ue service.
 * @author danilo
 */
@Service
public class UEServiceImpl implements UEService {
    private final UERepository ueRepository;
    private final GrilleRepository grilleRepository;
    private final GrilleMapper grilleMapper;
    private final UEMapper ueMapper;
    private final ECService ecService;


    /**
     * Instantiates a new Ue service.
     *
     * @param ueRepository  the ue repository
     * @param ueMapper      the ue mapper
     */
    public UEServiceImpl(UERepository ueRepository, GrilleRepository grilleRepository, GrilleMapper grilleMapper, UEMapper ueMapper, ECService ecService) {
        this.ueRepository = ueRepository;
        this.grilleRepository = grilleRepository;
        this.grilleMapper = grilleMapper;
        this.ueMapper = ueMapper;
        this.ecService = ecService;
    }


    @Override
    public List<UEDTO> getAllUEByGrille(UUID grilleId) {
        List<UE> ues = ueRepository.findAllByGrilleId(grilleId);
        return ues.stream()
                .map(ueMapper::toUEDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<UEDTO> getAllActiveUEByGrille(UUID grilleId) {
        List<UE> ues = ueRepository.findAllActiveByGrilleId(grilleId);
        return ues.stream()
                .map(ueMapper::toUEDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UEDTO getUE(UUID ueId) {
        Optional<UE> optionalue = ueRepository.findByIdAndDateSuppressionIsNull(ueId);
        if (optionalue.isPresent()) {
            UE ue = optionalue.get();
            return ueMapper.toUEDTO(ue);
        }
        else
            return null;
    }

    @Override
    public UEDTO createUE(UEDTO ueDTO, UUID grilleId) {
        Optional<Grille> optionalGrille=grilleRepository.findByIdAndDateSuppressionIsNull(grilleId);
        if(optionalGrille.isPresent()) {
            Grille grille = optionalGrille.get();
            ueDTO.setGrille(grilleMapper.toDto(grille));
            ueDTO.makePourcentage(ueDTO.getPourcentageTp(), ueDTO.getPourcentageCC(), ueDTO.getPourcentageEE());
            UE ue=ueRepository.save(ueMapper.toUE(ueDTO));
            return ueMapper.toUEDTO(ue);
        }
        else
            return null;
    }

    @Override
    public UEDTO getUE(String code) {
        Optional<UE> optionalue = ueRepository.findByCodeAndDateSuppressionIsNull(code);
        if (optionalue.isPresent()) {
            UE ue = optionalue.get();
            return ueMapper.toUEDTO(ue);
        }
        else
            return null;
    }

    @Override
    public UEDTO updateUE(UEDTO ueDTO, UUID ueId) {
        Optional<UE> optionalue = ueRepository.findByIdAndDateSuppressionIsNull(ueId);
        if (optionalue.isPresent()) {
            UE ue = optionalue.get();
            ueDTO.makePourcentage(ueDTO.getPourcentageTp(), ueDTO.getPourcentageCC(), ueDTO.getPourcentageEE());
            ue.setLibelle(ueDTO.getLibelle());
            ue.setCode(ueDTO.getCode());
            ue.setCredits(ueDTO.getCredits());
            ue.setVolumeHoraire(ueDTO.getVolumeHoraire());
            ue.setSemestre(ueDTO.getSemestre());
            ue.setTypeUE(ueDTO.getTypeUE());
            ue.setPourcentageTp(ueDTO.getPourcentageTp());
            ue.setPourcentageCC(ueDTO.getPourcentageCC());
            ue.setPourcentageSN(ueDTO.getPourcentageEE());
            ue = ueRepository.save(ue);
            return ueMapper.toUEDTO(ue);
        }
        else
            return null;

    }

    @Override
    public UEDTO updateLibelle(UEDTO ueDTO, UUID ueId) {
        Optional<UE> optionalue = ueRepository.findByIdAndDateSuppressionIsNull(ueId);
        if (optionalue.isPresent()) {
            UE ue = optionalue.get();

            ue.setLibelle(ueDTO.getLibelle());
            ue = ueRepository.save(ue);
            return ueMapper.toUEDTO(ue);
        }
        else
            return null;
    }

    @Override
    public UEDTO updateCode(UEDTO ueDTO, UUID ueId) {
        Optional<UE> optionalue = ueRepository.findByIdAndDateSuppressionIsNull(ueId);
        if (optionalue.isPresent()) {
            UE ue = optionalue.get();

            ue.setCode(ueDTO.getCode());
            ue = ueRepository.save(ue);
            return ueMapper.toUEDTO(ue);
        }
        else
            return null;
    }

    @Override
    public boolean deleteUE(UUID ueId) {
        Optional<UE> optionalue = ueRepository.findByIdAndDateSuppressionIsNull(ueId);
        if (optionalue.isPresent()) {
            UE ue=optionalue.get();
            ecService.markAsDeletedEc(ue);
            ue.setDateSuppression(LocalDateTime.now());
            ueRepository.save(ue);
            return true;
        }
        else
            return false;
    }

    @Override
    public void markAsDeleteUE(Grille grille) {
        List<UE> ues= ueRepository.findByGrille(grille);

        for (UE ue : ues) {
            ue.setDateSuppression(LocalDateTime.now()); // Marque la date de suppression
            ueRepository.save(ue); // Enregistre les changements
        }
    }
}
