package Sea.incubator.sgdeb.model;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Code {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Identifiant unique pour chaque code

    @Column(unique = true, nullable = false)
    private Long codePaiement; // Code unique de 13 chiffres

    private long dateExpiration; // Timestamp d'expiration

    private boolean utilise; // Indique si le code a été utilisé

}