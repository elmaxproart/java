package Sea.incubator.sgdeb.service.impl;

import Sea.incubator.sgdeb.dto.AnneeAcademicDto;
import Sea.incubator.sgdeb.dto.GrilleDTO;
import Sea.incubator.sgdeb.mapper.AnneeAcademicMapper;
import Sea.incubator.sgdeb.mapper.FiliereMapper;
import Sea.incubator.sgdeb.mapper.GrilleMapper;
import Sea.incubator.sgdeb.model.AnneeAcademic;
import Sea.incubator.sgdeb.model.Filiere;
import Sea.incubator.sgdeb.model.Grille;
import Sea.incubator.sgdeb.repository.AnneeAcademicRepository;
import Sea.incubator.sgdeb.repository.FiliereRepository;
import Sea.incubator.sgdeb.repository.GrilleRepository;
import Sea.incubator.sgdeb.service.AnneeAcademicService;
import Sea.incubator.sgdeb.service.FiliereService;
import Sea.incubator.sgdeb.service.GrilleService;
import Sea.incubator.sgdeb.service.UEService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * The type Grille service.
 * @author danilo
 */
@Service
public class GrilleServiceImpl implements GrilleService {
    private final FiliereMapper filiereMapper;
    private final FiliereRepository filiereRepository;
    private final AnneeAcademicRepository anneeAcademicRepository;
    private  final GrilleRepository grilleRepository;
    private  final GrilleMapper grilleMapper;
    private final AnneeAcademicMapper anneeAcademicMapper;
    private final UEService ueService;

    /**
     * Instantiates a new Grille service.
     *
     * @param grilleRepository     the grille repository
     * @param grilleMapper         the grille mapper
     * @param anneeAcademicMapper  the annee academic mapper
     */
    public GrilleServiceImpl(FiliereMapper filiereMapper, FiliereRepository filiereRepository, AnneeAcademicRepository anneeAcademicRepository, GrilleRepository grilleRepository, GrilleMapper grilleMapper, AnneeAcademicMapper anneeAcademicMapper, UEService ueService) {
        this.filiereMapper = filiereMapper;
        this.filiereRepository = filiereRepository;
        this.anneeAcademicRepository = anneeAcademicRepository;
        this.grilleRepository = grilleRepository;
        this.grilleMapper = grilleMapper;
        this.anneeAcademicMapper = anneeAcademicMapper;
        this.ueService = ueService;
    }

    @Override
    public GrilleDTO createGrille(GrilleDTO grilleDTO, UUID id,UUID idA) {
        Optional<Filiere> optionalfiliere=filiereRepository.findByIdAndDateSuppressionIsNull(id);
        Optional<AnneeAcademic> optionalAnneeAcademic=anneeAcademicRepository.findByIdAndDateSuppressionIsNull(idA);
        if(optionalfiliere.isPresent() && optionalAnneeAcademic.isPresent()){
            AnneeAcademic anneeAcademic=optionalAnneeAcademic.get();
            Filiere filiere=optionalfiliere.get();
            grilleDTO.setFiliere(filiereMapper.toDto(filiere));
            grilleDTO.setAnneeAcademic(anneeAcademicMapper.toDto(anneeAcademic));
            Grille grille=grilleRepository.save(grilleMapper.toEntity(grilleDTO));
            return grilleMapper.toDto(grille);
        }
        else
            return null;
    }

    @Override
    public List<GrilleDTO> getAllByFiliere(UUID id) {
        List<Grille> grilles=grilleRepository.findAllByFiliereId(id);

        return grilles.stream()
                .map(grilleMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<GrilleDTO> getAllActiveByFiliere(UUID id) {
        List<Grille> grilles=grilleRepository.findAllActiveByFiliereId(id);

        return grilles.stream()
                .map(grilleMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public GrilleDTO getByLibelle(String libelle) {
        Optional<Grille> optionalGrille=grilleRepository.findByLibelleAndDateSuppressionIsNull(libelle);
        if(optionalGrille.isPresent()) {
            Grille grille = optionalGrille.get();
            return grilleMapper.toDto(grille);
        }
        else
            return  null;
    }

    @Override
    public GrilleDTO getById(UUID id) {
        Optional<Grille> optionalGrille=grilleRepository.findByIdAndDateSuppressionIsNull(id);
        if ((optionalGrille.isPresent())){
            Grille grille=optionalGrille.get();
            return grilleMapper.toDto(grille);
        }
        else
            return null;
    }

    @Override
    public GrilleDTO updateGrille(GrilleDTO grilleDTO, UUID id) {
        Optional<Grille> optionalGrille=grilleRepository.findByIdAndDateSuppressionIsNull(id);
        if ((optionalGrille.isPresent())){
            Grille grille=optionalGrille.get();
            grille.setLibelle(grilleDTO.getLibelle());
            grille.setNiveau(grilleDTO.getNiveau());
            grille.setCode(grilleDTO.getCode());
            grilleRepository.save(grille);
            return grilleMapper.toDto(grille);
        }
        else
            return null;
    }

    @Override
    public GrilleDTO updateLibelle(GrilleDTO grilleDTO, UUID id) {
        Optional<Grille> optionalGrille=grilleRepository.findByIdAndDateSuppressionIsNull(id);
        if ((optionalGrille.isPresent())){
            Grille grille=optionalGrille.get();
            grille.setLibelle(grilleDTO.getLibelle());
            grilleRepository.save(grille);
            return grilleMapper.toDto(grille);
        }
        else
            return null;
    }

    @Override
    public GrilleDTO updateCode(GrilleDTO grilleDTO, UUID id) {
        Optional<Grille> optionalGrille=grilleRepository.findByIdAndDateSuppressionIsNull(id);
        if ((optionalGrille.isPresent())){
            Grille grille=optionalGrille.get();
            grille.setCode(grilleDTO.getCode());
            grilleRepository.save(grille);
            return grilleMapper.toDto(grille);
        }
        else
            return null;
    }

    @Override
    public GrilleDTO updateAnneeAcademic(UUID grille_id, UUID annee_id) {
        Optional<AnneeAcademic> optionalAnneeAcademic=anneeAcademicRepository.findByIdAndDateSuppressionIsNull(annee_id);
        Optional<Grille> optionalGrille=grilleRepository.findByIdAndDateSuppressionIsNull(grille_id);
        if(optionalGrille.isPresent() && optionalAnneeAcademic.isPresent()){
            Grille grille=optionalGrille.get();
            AnneeAcademic anneeAcademic=optionalAnneeAcademic.get();
            grille.setAnneeAcademic(anneeAcademicMapper.toEntity(anneeAcademicMapper.toDto(anneeAcademic)));
            GrilleDTO grilleDTO=grilleMapper.toDto(grilleRepository.save(grille));
            return grilleDTO;
        }
        else
            return null;

    }

    @Override
    public boolean deleteGrille(UUID id) {
        Optional<Grille> optionalGrille=grilleRepository.findByIdAndDateSuppressionIsNull(id);
        if ((optionalGrille.isPresent())){
            Grille grille=optionalGrille.get();

            grille.setDateSuppression(LocalDateTime.now());
            grilleRepository.save(grille);
            return true;
        }
        else
            return false;
    }

    @Override
    public void markAsDeletedGrille(Filiere filiere) {
        List<Grille> grilles= grilleRepository.findByFiliere(filiere);
        for(Grille grille:grilles){
            ueService.markAsDeleteUE(grille);
            grille.setDateSuppression(LocalDateTime.now());
            grilleRepository.save(grille);
        }
    }
}
