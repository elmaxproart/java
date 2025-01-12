package Sea.incubator.sgdeb.service;

import Sea.incubator.sgdeb.external.FiliereDTO;
import Sea.incubator.sgdeb.external.UEDTO;
import Sea.incubator.sgdeb.model.Importation;
import Sea.incubator.sgdeb.model.Note;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service
public interface ImportationService {




        UEDTO findByCodeUeAndFiliere(String codeUE, FiliereDTO filiere);
        FiliereDTO findByCode(String codeFil);
        List<Note> importationFromExel(MultipartFile file, UUID idExamen);


        UEDTO getUEByCode(String codeUE);

    Importation importationDetails(UUID idExamen);
}
