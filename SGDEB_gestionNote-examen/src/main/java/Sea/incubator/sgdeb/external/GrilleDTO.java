package Sea.incubator.sgdeb.external;

import Sea.incubator.sgdeb.external.enumType.Niveau;
import lombok.Data;

import java.util.UUID;

@Data
public class GrilleDTO {
  private UUID id;
  private String libelle;
  private FiliereDTO filiere;
  private AnneeAcademicDto anneeAcademic;
  private String code;
  private Niveau niveau;
}