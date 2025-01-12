package Sea.incubator.sgdeb.service.impl;

import Sea.incubator.sgdeb.dto.FiliereDTO;
import Sea.incubator.sgdeb.mapper.DepartementMapper;
import Sea.incubator.sgdeb.mapper.FiliereMapper;
import Sea.incubator.sgdeb.model.Departement;
import Sea.incubator.sgdeb.model.Filiere;
import Sea.incubator.sgdeb.model.enumType.Faculte;
import Sea.incubator.sgdeb.repository.DepartementRepository;
import Sea.incubator.sgdeb.repository.FiliereRepository;
import Sea.incubator.sgdeb.service.DepartementService;
import Sea.incubator.sgdeb.service.FiliereService;
import Sea.incubator.sgdeb.service.GrilleService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * The type Filiere service.
 * @author danilo
 */
@Service
public class FiliereServiceImpl implements FiliereService {
    private final GrilleService grilleService;
    private final DepartementRepository departementRepository;
    private final FiliereRepository filiereRepository;
    private final FiliereMapper filiereMapper;


    /**
     * Instantiates a new Filiere service.
     *
     * @param filiereRepository  the filiere repository
     * @param filiereMapper      the filiere mapper
     */
    public FiliereServiceImpl(GrilleService grilleService, DepartementRepository departementRepository, FiliereRepository filiereRepository, FiliereMapper filiereMapper) {
        this.grilleService = grilleService;
        this.departementRepository = departementRepository;
        this.filiereRepository = filiereRepository;
        this.filiereMapper = filiereMapper;

    }


    @Override
    public FiliereDTO createFiliere(FiliereDTO filiereDTO, UUID department_id) {
        Optional<Departement> optionaldepartement=departementRepository.findByIdAndDateSuppressionIsNull(department_id);
        if(optionaldepartement.isPresent()){
            Departement departement=optionaldepartement.get();
            filiereDTO.setDepartement(DepartementMapper.departementToDepartementDTO(departement));
            filiereDTO.makeCode();
            Filiere filiere=filiereRepository.save(filiereMapper.toEntity(filiereDTO));

            return filiereMapper.toDto(filiere);
        }
        else
            return null;
    }

    @Override
    public List<FiliereDTO> getAllFiliereByDepartment(UUID department_id) {
        List<Filiere> filieres = filiereRepository.findAllByDepartmentId(department_id);
        return filieres.stream()
                .map(filiereMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<FiliereDTO> getAllAsMarkedNotDeleted(UUID id) {
        List<Filiere> filieres = filiereRepository.findAllActiveByDepartmentId(id);
        return filieres.stream()
                .map(filiereMapper::toDto)
                .collect(Collectors.toList());
    }


    @Override
    public List<FiliereDTO> getAllFiliere() {
        List<Filiere> filieres = filiereRepository.findByDateSuppressionIsNull();
        return filieres.stream()
                .map(filiereMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public FiliereDTO getFiliere(String code) {
        Optional<Filiere> optionalFiliere=filiereRepository.findByCodeAndDateSuppressionIsNull(code);
        if(optionalFiliere.isPresent()){
            return filiereMapper.toDto(optionalFiliere.get());
        }
        else
            return null;

    }

    @Override
    public FiliereDTO getFiliere(UUID id) {
        Optional<Filiere> optionalFiliere=filiereRepository.findByIdAndDateSuppressionIsNull(id);

        if(optionalFiliere.isPresent()){
            return filiereMapper.toDto(optionalFiliere.get());
        }
        else
            return null;
    }

    @Override
    public FiliereDTO updateFiliere(FiliereDTO filiereDTO, UUID id) {
        Optional<Filiere> optionalFiliere=filiereRepository.findByIdAndDateSuppressionIsNull(id);
        if(optionalFiliere.isPresent()){
            Filiere filiere=optionalFiliere.get();
            filiereDTO.makeCode();
            filiere.setCode(filiereDTO.getCode());
            filiere.setLibelle(filiereDTO.getLibelle());
            filiereRepository.save(filiere);
            FiliereDTO finalFiliere=filiereMapper.toDto(filiere);
            return finalFiliere;
        }
        else
            return null;
    }

    @Override
    public FiliereDTO updateName(FiliereDTO filiereDTO, UUID id) {
        Optional<Filiere> optionalFiliere=filiereRepository.findByIdAndDateSuppressionIsNull(id);
        if(optionalFiliere.isPresent()) {
            Filiere filiere=optionalFiliere.get();
            filiere.setLibelle(filiereDTO.getLibelle());
            filiereDTO.makeCode();
            filiereRepository.save(filiere);
            FiliereDTO finalFiliere=filiereMapper.toDto(filiere);
            return finalFiliere;
        }
        else
            return null;
    }

    @Override
    public FiliereDTO getFiliereByLibelle(String libelle) {
        Optional<Filiere> optionalFiliere=filiereRepository.findByLibelleAndDateSuppressionIsNull(libelle);
        if(optionalFiliere.isPresent()){
        return filiereMapper.toDto(optionalFiliere.get());}
        else
            return  null;
    }

    @Override
    public FiliereDTO updateCode(FiliereDTO filiereDTO, UUID id) {
        Optional<Filiere> optionalFiliere=filiereRepository.findByIdAndDateSuppressionIsNull(id);
        if(optionalFiliere.isPresent()) {
            Filiere filiere=optionalFiliere.get();
            filiere.setCode(filiereDTO.getCode());
            filiereRepository.save(filiere);
            FiliereDTO finalFiliere=filiereMapper.toDto(filiere);
            return finalFiliere;
        }
        else
            return null;
    }

    @Override
    public boolean deleteFiliere(UUID id) {
        Optional<Filiere> optionalFiliere=filiereRepository.findByIdAndDateSuppressionIsNull(id);
        if(optionalFiliere.isPresent()) {
            Filiere filiere=optionalFiliere.get();
            grilleService.markAsDeletedGrille(filiere);
            filiere.setDateSuppression(LocalDateTime.now());
            filiereRepository.save(filiere);
            return true;
        }
        else
            return false;
    }

    @Override
    public void markAsDeleteFilieres(Departement departement) {
        List<Filiere> filieres= filiereRepository.findByDepartement(departement);

        for(Filiere filiere:filieres){
            filiere.setDateSuppression(LocalDateTime.now());
            filiereRepository.save(filiere);
        }

    }

    @Override
    public List<FiliereDTO> getAllByFaculte(String faculte) {
        Faculte faculte1=Faculte.valueOf(faculte);
        List<Filiere> filieres = filiereRepository.findByFaculteAndDateSuppressionIsNull(faculte1);
        return filieres.stream()
                .map(filiereMapper::toDto)
                .collect(Collectors.toList());

    }

    @Override
    public void updateEffectif(FiliereDTO filiereDTO, String code) {
        Optional<Filiere> optionalfiliere=filiereRepository.findByCodeAndDateSuppressionIsNull(code);
        if(optionalfiliere.isPresent()){
            Filiere filiere=optionalfiliere.get();
            filiere.setEffectif(filiereDTO.getEffectif());
        }

    }
}
