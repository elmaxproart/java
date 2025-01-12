package Sea.incubator.sgdeb.dto;


import lombok.Data;

import java.util.UUID;


/**
 * The type Ecdto.
 */
@Data
public class ECDTO {

    private UUID id;
    private String libelle;
    private String code;
    private UEDTO UE;
}
