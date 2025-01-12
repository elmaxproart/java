package Sea.incubator.sgdeb.service.impl;

import Sea.incubator.sgdeb.dto.DepartementDTO;
import Sea.incubator.sgdeb.mapper.DepartementMapper;
import Sea.incubator.sgdeb.model.Departement;
import Sea.incubator.sgdeb.repository.DepartementRepository;
import Sea.incubator.sgdeb.service.DepartementService;
import Sea.incubator.sgdeb.service.FiliereService;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * The type Department service.
 */
@Service
@Data
public class DepartmentServiceImpl implements DepartementService {
    private  final DepartementRepository departementRepository;
    private  final DepartementMapper departementMapper;
    private final FiliereService filiereService;

    /**
     * Instantiates a new Department service.
     *
     * @param departementRepository the departement repository
     * @param departementMapper     the departement mapper
     */
    public DepartmentServiceImpl(DepartementRepository departementRepository, DepartementMapper departementMapper, FiliereService filiereService) {
        this.departementRepository = departementRepository;
        this.departementMapper = departementMapper;
        this.filiereService = filiereService;
    }


    @Override
    public List<DepartementDTO> getDepartementList() {
        List<DepartementDTO> listFinal=new LinkedList<>();
        List<Departement> list=departementRepository.findAll();
        for(Departement departement: list){
            DepartementDTO departementDTO=departementMapper.departementToDepartementDTO(departement);
            listFinal.add(departementDTO);
        }
        return listFinal;
    }

    @Override
    public List<DepartementDTO> getDapartmentListMarkedAsNotDeleted() {

        List<DepartementDTO> listFinal=new LinkedList<>();
        List<Departement> list=departementRepository.findByDateSuppressionIsNull();
        for(Departement departement: list){
            DepartementDTO departementDTO=departementMapper.departementToDepartementDTO(departement);
            listFinal.add(departementDTO);
        }
        return listFinal;
    }

    @Override
    public DepartementDTO getDepartement(UUID id) {
        Optional<Departement> departement=departementRepository.findByIdAndDateSuppressionIsNull(id);
        return departement.map(DepartementMapper::departementToDepartementDTO).orElse(null);
    }

    @Override
    public DepartementDTO getDepartement(String nomD) {
        Optional<Departement> departement=departementRepository.findByNomDepartmentAndDateSuppressionIsNull(nomD);
        return departement.map(DepartementMapper::departementToDepartementDTO).orElse(null);
    }

    @Override
    public DepartementDTO createDepartement(DepartementDTO departementDTO) {
        Departement departement=departementRepository.save(departementMapper.departementToDepartementDTO(departementDTO));

        return departementMapper.departementToDepartementDTO(departement);
    }

    @Override
    public DepartementDTO updateDepartement(DepartementDTO newDepartement, UUID id) {
        Optional<Departement> Optdepartement=departementRepository.findByIdAndDateSuppressionIsNull(id);

        if(Optdepartement.isPresent()){
            Departement departement=Optdepartement.get();
            departement.setNomDepartment(newDepartement.getNomDepartment());
            Departement departement1=departementRepository.save(departement);
            return departementMapper.departementToDepartementDTO(departement1);
        }
        else
            return null;
    }

    @Override
    public boolean deleteDepartement(UUID id) {
        Optional<Departement> departement=departementRepository.findByIdAndDateSuppressionIsNull(id);
        if(departement.isPresent()){
            Departement departement1=departement.get();
            departement1.setDateSuppression(LocalDateTime.now());
            filiereService.markAsDeleteFilieres(departement1);
            departementRepository.save(departement1);
            return  true;
        }
        else
            return false;
    }
}
