package Sea.incubator.sgdeb.dto;



import Sea.incubator.sgdeb.model.enumType.Faculte;
import lombok.Data;

import java.util.Locale;
import java.util.UUID;

/**
 * The type Filiere dto.
 */
@Data
public class FiliereDTO {
    private UUID id;
    private String libelle;
    private DepartementDTO departement;
    private String code;
    private Faculte faculte;
    private int effectif;
    /**
     * Make code 4 firsts letters on UpperCase form.
     */
    public void makeCode(){
        code=libelle.substring(0,4).toUpperCase(Locale.ROOT);
    }

}
