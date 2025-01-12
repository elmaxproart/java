package Sea.incubator.sgdeb.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
public class EC {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false,unique = true)
    private String intitule;
    @Column(nullable = false,unique = true)
    private String codeEc;
    @ManyToOne
    @JoinColumn(name = "UE_id",nullable = false)
    private  UE UE;

    @Column(nullable = false,updatable = false)
    @CreationTimestamp
    private LocalDateTime dateCreation;
    @UpdateTimestamp
    private LocalDateTime dateModification;
    private LocalDateTime dateSuppression;
}
