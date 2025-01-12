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

public class PayementPDTO {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long payementId;

        @Column(unique = true)
        private String codeBank;
        private long codePaiement;
        private String banque;
        private BigDecimal montant;
        private String nomEtudiant;
        private LocalDateTime datecreation;
        private String anneeAcademique;
        private String niveau;
        @JoinColumn(name = "idEtudiant")
        private Etudiant etudiant;

        // Getters and setters


}
