package Sea.incubator.sgdeb.service.impl;

import Sea.incubator.sgdeb.dto.CorrectionDto;
import Sea.incubator.sgdeb.external.GrilleExamenDTO;
import Sea.incubator.sgdeb.external.NoteDTO;
import Sea.incubator.sgdeb.mapper.CorrectionMapper;
import Sea.incubator.sgdeb.model.Correction;
import Sea.incubator.sgdeb.repository.CorrectionRepository;
import Sea.incubator.sgdeb.service.CorrectionService;
import Sea.incubator.sgdeb.service.ExamenService;
import Sea.incubator.sgdeb.service.ParticpeService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * The type Correction service.
 */
@Service
public class CorrectionServiceImpl implements CorrectionService {
    private final CorrectionRepository correctionRepository;
    private final ExamenService examenService;
    private final CorrectionMapper correctionMapper;
    private final ParticpeService particpeService;

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Instantiates a new Correction service.
     *
     * @param correctionRepository the correction repository
     * @param examenService        the examen service
     * @param correctionMapper     the correction mapper
     * @param particpeService      the particpe service
     */
    public CorrectionServiceImpl(CorrectionRepository correctionRepository, ExamenService examenService, CorrectionMapper correctionMapper, ParticpeService particpeService) {
        this.correctionRepository = correctionRepository;
        this.examenService = examenService;
        this.correctionMapper = correctionMapper;
        this.particpeService = particpeService;
    }

    /**
     * Generate anonymat int.
     *
     * @return the int
     */
    public int generateAnonymat() {
        Integer maxAnonymat = (Integer) entityManager.createQuery("SELECT COALESCE(MAX(c.anonymat), 0) FROM Correction c")
                .getSingleResult();
        return maxAnonymat + 1; // Incrémentez la valeur maximale trouvée
    }
    @Override
    public CorrectionDto createCorrection(CorrectionDto correctionDto, UUID examen_id, UUID participe_id) {

        GrilleExamenDTO examenDTO=examenService.getExamen(examen_id);

        NoteDTO participeDTO=particpeService.getParticipe(participe_id);

        if(examenDTO!=null && participeDTO!=null){
            correctionDto.setAnonymat(generateAnonymat());
            correctionDto.setSemestre(examenDTO.getUEdto().getSemestre());
            correctionDto.setUe(examenDTO.getUEdto().getCode());
            //on enregistre une correction selon l'objet de la requete et on change ce qu'il y a a changer puis on charge les informations correctes
            switch (correctionDto.getNature()){
                case NOM:
                    participeDTO.setNom(correctionDto.getNom());
                    correctionDto.setNote(participeDTO.getNotes());
                    correctionDto.setMatricule(participeDTO.getMatricule());
                    Correction correction=correctionRepository.save(correctionMapper.convertToEntity(correctionDto));
                    correctionDto=correctionMapper.convertToDto(correction);
                    particpeService.updateNom(participeDTO,participe_id);
                    break;
                case MATRICULE:
                    participeDTO.setMatricule(correctionDto.getMatricule());
                    correctionDto.setNom(participeDTO.getNom());
                    correctionDto.setNote(participeDTO.getNotes());
                    Correction correction1=correctionRepository.save(correctionMapper.convertToEntity(correctionDto));
                    correctionDto=correctionMapper.convertToDto(correction1);
                    particpeService.updateMatricule(participeDTO,participe_id);
                    break;
                case NOTE:
                    participeDTO.setNotes(correctionDto.getNote());
                    correctionDto.setNom(participeDTO.getNom());
                    correctionDto.setMatricule(participeDTO.getMatricule());
                    Correction correction2=correctionRepository.save(correctionMapper.convertToEntity(correctionDto));
                    correctionDto=correctionMapper.convertToDto(correction2);
                    particpeService.updateNote(participeDTO,participe_id);
                    break;
            }

            return correctionDto;
        }
        else
            return null;
    }

    @Override
    public CorrectionDto getCorrection(UUID correction_id) {
        Optional<Correction> optionalcorrection=correctionRepository.findByIdAndDateSuppressionIsNull(correction_id);
        if(optionalcorrection.isPresent()){
            Correction correction= optionalcorrection.get();
            return correctionMapper.convertToDto(correction);
        }
        else
            return null;
    }

    @Override
    public CorrectionDto getCorrection(int anonimat) {
        Optional<Correction> optionalCorrection=correctionRepository.findByAnonymatAndDateSuppressionIsNull(anonimat);
        if(optionalCorrection.isPresent()){
            Correction correction= optionalCorrection.get();
            return correctionMapper.convertToDto(correction);
        }
        else
            return null;
    }

    @Override
    public CorrectionDto updateCorrection(CorrectionDto correctionDto, UUID correction_id, UUID participe_id) {
        NoteDTO participeDTO=particpeService.getParticipe(participe_id);
        Optional<Correction> optionalcorrection=correctionRepository.findByIdAndDateSuppressionIsNull(correction_id);
        if(optionalcorrection.isPresent() && participeDTO!=null){
            Correction correction= optionalcorrection.get();
            correction.setNote(correctionDto.getNote());
            correction.setNom(correctionDto.getNom());
            correction.setNature(correctionDto.getNature());
            correction.setMatricule(correctionDto.getMatricule());
            correction.setStatus(correctionDto.isStatus());
            switch (correctionDto.getNature()){
                case NOM:
                    participeDTO.setNom(correctionDto.getNom());
                    correction.setMatricule(participeDTO.getMatricule());
                    correction.setNote(participeDTO.getNotes());
                    correctionRepository.save(correction);
                    correctionDto=correctionMapper.convertToDto(correction);
                    particpeService.updateNom(participeDTO,participe_id);
                case MATRICULE:
                    participeDTO.setMatricule(correctionDto.getMatricule());
                    correction.setNote(participeDTO.getNotes());
                    correction.setNom(participeDTO.getNom());
                    correctionRepository.save(correction);
                    correctionDto=correctionMapper.convertToDto(correction);
                    particpeService.updateMatricule(participeDTO,participe_id);
                case NOTE:
                    participeDTO.setNotes(correctionDto.getNote());
                    correction.setNom(participeDTO.getNom());
                    correction.setMatricule(participeDTO.getMatricule());
                    correctionRepository.save(correction);
                    correctionDto=correctionMapper.convertToDto(correction);
                    particpeService.updateNote(participeDTO,participe_id);

            }
            return correctionDto;
        }
        else
            return null;
    }

    @Override
    public CorrectionDto updateStatut(CorrectionDto correctionDto, UUID correction_id) {
        Optional<Correction> optionalcorrection=correctionRepository.findByIdAndDateSuppressionIsNull(correction_id);
        if(optionalcorrection.isPresent()){
            Correction correction= optionalcorrection.get();
            correction.setStatus(true);
            correctionRepository.save(correction);

            return correctionMapper.convertToDto(correction);
        }
        else
            return null;
    }

    @Override
    public boolean deleteCorrection(UUID correction_id) {
        Optional<Correction> optionalcorrection=correctionRepository.findByIdAndDateSuppressionIsNull(correction_id);
        if(optionalcorrection.isPresent()) {
            Correction correction = optionalcorrection.get();
            correction.setDateSuppression(LocalDateTime.now());
            correctionRepository.save(correction);
            return true;
        }
        else
            return false;
    }

    @Override
    public List<CorrectionDto> getCorrectionList() {
        List<Correction> corrections=correctionRepository.findByDateSuppressionIsNull();
        List<CorrectionDto> listCorrection=new LinkedList<>();
        for(Correction correction:corrections){
            listCorrection.add(correctionMapper.convertToDto(correction));
        }
        return listCorrection;
    }
}
