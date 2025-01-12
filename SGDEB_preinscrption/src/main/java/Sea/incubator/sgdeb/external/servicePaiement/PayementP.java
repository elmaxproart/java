package Sea.incubator.sgdeb.external.servicePaiement;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "paiementPreinscrit")
@Component
public class PayementP {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long payementId;

    @Column(unique = true)
    private String codeBank;

    private String banque;
    private BigDecimal montant;
    private String nomEtudiant;
    private LocalDateTime datecreation;
    private String anneeAcademique;
    private String niveau;


    // Getters and setters
}