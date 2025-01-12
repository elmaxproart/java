package Sea.incubator.sgdeb.service.impl;

import Sea.incubator.sgdeb.external.FiliereDTO;
import Sea.incubator.sgdeb.external.UEDTO;
import Sea.incubator.sgdeb.external.enumType.TypeEval;
import Sea.incubator.sgdeb.model.GrilleExamen;
import Sea.incubator.sgdeb.model.Note;
import Sea.incubator.sgdeb.repository.GrilleExamenRepository;
import Sea.incubator.sgdeb.repository.NoteRepository;
import Sea.incubator.sgdeb.service.ExamenService;
import Sea.incubator.sgdeb.service.NoteService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class GrilleExamenServiceImp implements ExamenService {
    public  final GrilleExamenRepository grilleExamenRepository;
    private final RestTemplate restTemplate;
    private final NoteRepository noteRepository;

    public GrilleExamenServiceImp(GrilleExamenRepository grilleExamenRepository, RestTemplate restTemplate, NoteRepository noteRepository) {
        this.grilleExamenRepository = grilleExamenRepository;
        this.restTemplate = restTemplate;
        this.noteRepository = noteRepository;
    }


    @Override
    public GrilleExamen createExamen(GrilleExamen grilleExamenEntity, String codeUE) {
        UEDTO ue = getUeByCode(codeUE);

        if (ue != null) {
            FiliereDTO filiere = ue.getGrille().getFiliere();

            if (filiere != null) {
                grilleExamenEntity.setCodeUE(ue.getCode());
                grilleExamenEntity.setCredit(ue.getCredits());
                grilleExamenEntity.setCodeFiliere(filiere.getCode());
                grilleExamenEntity.setAnneeAcademic(ue.getGrille().getAnneeAcademic().getLibelle());

                Optional<GrilleExamen> existingExamFound = grilleExamenRepository.findByCodeUEAndCodeFiliereAndCreditAndNoteSurAndAnneeAcademicAndTypeEvalAndSessionAndSemestre(
                        ue.getCode(),
                        filiere.getCode(),
                        ue.getCredits(),
                        grilleExamenEntity.getNoteSur(),
                        ue.getGrille().getLibelle(),
                        grilleExamenEntity.getTypeEval(),
                        grilleExamenEntity.getSession(),
                        grilleExamenEntity.getSemestre()
                );

                if (existingExamFound.isPresent()) {
                    System.err.println("Examen déjà existant");
                    return null; // Ou lancer une exception personnalisée si besoin
                } else {
                    return grilleExamenRepository.save(grilleExamenEntity);
                }
            } else {
                throw new EntityNotFoundException("Filière non trouvée avec le code : " + filiere.getCode());
            }
        } else {
            throw new EntityNotFoundException("UE non trouvée avec le code : " + grilleExamenEntity.getCodeUE());
        }
    }


    @Override
    public GrilleExamen updateExamen(UUID id, GrilleExamen grilleExamen) {

        GrilleExamen existingDossierGrilleExamen = grilleExamenRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("examen not found with id: " + id));
        existingDossierGrilleExamen.setTypeEval(grilleExamen.getTypeEval());
        existingDossierGrilleExamen.setStatut(grilleExamen.getStatut());
        existingDossierGrilleExamen.setSession(grilleExamen.getSession());
        existingDossierGrilleExamen.setNoteSur(grilleExamen.getNoteSur());
        return grilleExamenRepository.save(existingDossierGrilleExamen);
    }

    @Override
    public List<GrilleExamen> getAllExamen() {
        return grilleExamenRepository.findAll();
    }

    @Override
    public GrilleExamen getExamenById(UUID id) {
        return grilleExamenRepository.findById(id).orElseThrow(
                null
        );
    }

    @Override
    public void deleteExamen(UUID id) {
        Optional<GrilleExamen> examenOptional = grilleExamenRepository.findById(id);
        if (examenOptional.isEmpty()) {
            throw new RuntimeException("Examen non trouvé : vérifier sa conformité");
        }

        List<Note> notes = noteRepository.findAll()
                .stream()
                .filter(note -> note.getIdGrilleExamen().getId().equals(id))
                .toList();

        if (!notes.isEmpty()) {
           noteRepository.deleteAll(notes);
        }

        grilleExamenRepository.deleteById(id);
    }



    @Override
    public UEDTO getUeByCode(String code) {
        String url = "http://localhost:1111/api/academics/ues/code/" + code;
        ResponseEntity<UEDTO> response = restTemplate.getForEntity(url, UEDTO.class);
        return response.getBody();
    }

    @Override
    public GrilleExamen getGrilles(TypeEval typeEval, String codeFiliere, String codeUE) {

        return grilleExamenRepository.findByCodeUEAndCodeFiliereAndTypeEval(codeFiliere,codeUE, typeEval);
    }




}
