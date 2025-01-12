package Sea.incubator.sgdeb.dto;

import Sea.incubator.sgdeb.external.Etudiant;
import Sea.incubator.sgdeb.model.TypeTranche;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Data
@Component
public class PayementIDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long payementId;

    @Column(unique = true)
    private long codeBank;

    @Column(unique = true)
    private  String matriculeEtu;

    private String banque;
    private BigDecimal montant;

    @Enumerated(EnumType.STRING)
    private TypeTranche typeTranche;

    private String nomEtudiant;
    private LocalDateTime datecreation;
    private String anneeAcademique;


    @JoinColumn(name = "idEtudiant")
    private Etudiant etudiant;


}
