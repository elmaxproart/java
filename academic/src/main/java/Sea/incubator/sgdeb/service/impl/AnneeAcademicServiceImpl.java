package Sea.incubator.sgdeb.service.impl;
import Sea.incubator.sgdeb.dto.AnneeAcademicDto;
import Sea.incubator.sgdeb.mapper.AnneeAcademicMapper;
import Sea.incubator.sgdeb.model.AnneeAcademic;
import Sea.incubator.sgdeb.repository.AnneeAcademicRepository;
import Sea.incubator.sgdeb.service.AnneeAcademicService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * The type Annee academic service.
 */
@Service
public class AnneeAcademicServiceImpl implements AnneeAcademicService {

    private final AnneeAcademicRepository anneeAcademicRepository;
    private final AnneeAcademicMapper anneeAcademicMapper;

    /**
     * Instantiates a new Annee academic service.
     *
     * @param anneeAcademicRepository the annee academic repository
     * @param anneeAcademicMapper     the annee academic mapper
     */
    public AnneeAcademicServiceImpl(AnneeAcademicRepository anneeAcademicRepository, AnneeAcademicMapper anneeAcademicMapper) {
        this.anneeAcademicRepository = anneeAcademicRepository;
        this.anneeAcademicMapper = anneeAcademicMapper;
    }


    @Override
    public AnneeAcademicDto createAnneeAcademic(AnneeAcademicDto anneeAcademicDto) {
        anneeAcademicDto.setLibelle(anneeAcademicDto.makeLibelle());

        AnneeAcademic anneeAcademic = anneeAcademicMapper.toEntity(anneeAcademicDto);
        anneeAcademic = anneeAcademicRepository.save(anneeAcademic);
        return anneeAcademicMapper.toDto(anneeAcademic);

    }

    @Override
    public AnneeAcademicDto getAnneeAcademic(UUID id) {
        Optional<AnneeAcademic> optionalanneeAcademic = anneeAcademicRepository.findByIdAndDateSuppressionIsNull(id);
        if (optionalanneeAcademic.isPresent()) {
            AnneeAcademic anneeAcademic = optionalanneeAcademic.get();
            return anneeAcademicMapper.toDto(anneeAcademic);
        }
        else
            return null;
    }

    @Override
    public List<AnneeAcademicDto> getAllAnneesAcademiques() {
        List<AnneeAcademic> anneesAcademiques = anneeAcademicRepository.findAll();
        return anneesAcademiques.stream()
                .map(anneeAcademicMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<AnneeAcademicDto> getAllActiveAnneeAcademic() {
        List<AnneeAcademic> anneesAcademiques = anneeAcademicRepository.findByDateSuppressionIsNull();
        return anneesAcademiques.stream()
                .map(anneeAcademicMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public AnneeAcademicDto updateAnneeAcademic(UUID id, AnneeAcademicDto anneeAcademicDto) {
        Optional<AnneeAcademic> optionalanneeAcademic = anneeAcademicRepository.findByIdAndDateSuppressionIsNull(id);
        if (optionalanneeAcademic.isPresent()) {
            AnneeAcademic anneeAcademic = optionalanneeAcademic.get();

            anneeAcademic.setFin(anneeAcademicDto.getFin());
            anneeAcademic.setDebut(anneeAcademicDto.getDebut());
            anneeAcademic.setLibelle(anneeAcademicDto.makeLibelle());

            anneeAcademic = anneeAcademicRepository.save(anneeAcademic);
            return anneeAcademicMapper.toDto(anneeAcademic);
        }
        else
            return null;
    }

    @Override
    public boolean deleteAnneeAcademic(UUID id) {
        Optional<AnneeAcademic> optionalanneeAcademic = anneeAcademicRepository.findByIdAndDateSuppressionIsNull(id);
        if (optionalanneeAcademic.isPresent()) {
            AnneeAcademic anneeAcademic=optionalanneeAcademic.get();
            anneeAcademic.setDateSuppression(LocalDateTime.now());
            anneeAcademicRepository.save(anneeAcademic);
            return true;
        }
        else
            return false;
    }
}
