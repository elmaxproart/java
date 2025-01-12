package Sea.incubator.sgdeb.dto;

import Sea.incubator.sgdeb.external.enumType.TypeEval;
import Sea.incubator.sgdeb.external.enumType.TypeUE;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Data
public class ImportationDto {
    private MultipartFile file;
    private TypeEval typeEval;
    private String codeUE;
    private String codeFil;
    private UUID enseignantId;

}