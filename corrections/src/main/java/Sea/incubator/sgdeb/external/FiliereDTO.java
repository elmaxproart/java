package Sea.incubator.sgdeb.external;

import lombok.Data;

import java.util.Locale;
import java.util.UUID;

@Data
public class FiliereDTO {
    private UUID id;
    private String libelle;
    private DepartementDTO departement;
    private String code;

    /**
     * Make code 4 firsts letters on UpperCase form.
     */
    public void makeCode(){
        code=libelle.substring(0,4).toUpperCase(Locale.ROOT);
    }

}
