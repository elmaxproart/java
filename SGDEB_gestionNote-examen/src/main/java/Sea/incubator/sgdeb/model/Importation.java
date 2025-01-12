package Sea.incubator.sgdeb.model;

import Sea.incubator.sgdeb.external.enumType.TypeEval;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
public class Importation {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private Date dateImportation;
    private int noteSur;
    @ManyToOne
    @JoinColumn(name = "grille_id")
    private GrilleExamen grille;
    private String cheminAcces;
    private TypeEval typeEval;
    private String codeUE;
    private String codeFil;
    private String anneeAcademic;
    public static Object builder() {

        return null;
    }
}
