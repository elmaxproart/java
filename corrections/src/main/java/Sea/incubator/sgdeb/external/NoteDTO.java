package Sea.incubator.sgdeb.external;

import lombok.Data;

import java.util.UUID;

@Data
public class NoteDTO {

    private UUID id;
    private double notes;
    private String nom;
    private String matricule;
    private String anonimat;
    private int anonimazeAble;

}
