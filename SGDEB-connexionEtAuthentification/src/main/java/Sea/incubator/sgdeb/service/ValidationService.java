package Sea.incubator.sgdeb.service;

import Sea.incubator.sgdeb.entite.Utilisateur;
import Sea.incubator.sgdeb.entite.Validation;
import Sea.incubator.sgdeb.repository.ValidationRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


import java.time.Instant;
import java.util.Random;

import static java.time.temporal.ChronoUnit.MINUTES;
@Slf4j
@Transactional
@AllArgsConstructor
@Service
public class ValidationService {

    private ValidationRepository validationRepository;
    private NotificationService notificationService;

    /**
     * Enregistre une nouvelle validation pour l'utilisateur.
     *
     * Cette méthode crée une nouvelle validation pour l'utilisateur donné, avec une durée de vie de 10 minutes.
     * La validation est enregistrée dans la base de données et un courriel est envoyé à l'utilisateur avec son code d'activation.
     *
     * @param utilisateur l'utilisateur pour lequel une validation est créée
     * @throws IllegalArgumentException si l'utilisateur est null
     */
    public void enregistrer(Utilisateur utilisateur) {
        Validation validation = new Validation();
        validation.setUtilisateur(utilisateur);
        Instant creation = Instant.now();
        validation.setCreation(creation);
        Instant expiration = creation.plus(10, MINUTES);
        validation.setExpiration(expiration);
        Random random = new Random();
        int randomInteger = random.nextInt(999999);
        String code = String.format("%06d", randomInteger);

        validation.setCode(code);
        this.validationRepository.save(validation);
        this.notificationService.envoyer(validation);
    }

    public Validation lireEnFonctionDuCode(String code) {
        return this.validationRepository.findByCode(code).orElseThrow(() -> new RuntimeException("Votre code est invalide"));
    }
    /**
     * Cette méthode est exécutée toutes les 30 minutes (par défaut) et supprime les tokens dont la date d'expiration est passée.
     *
     * Cette méthode est destinée à être exécutée par Spring à l'aide de l'annotation {@link org.springframework.scheduling.annotation.Scheduled}
     * et du paramètre {@code cron} de cette annotation.
     *
     * Il est possible de modifier l'expression cron pour changer la fréquence de l'exécution de cette méthode. Par exemple, si l'on souhaite
     * exécuter cette méthode toutes les heures, on peut utiliser l'expression {@code 0 0 * * * *}
     *
     * @see org.springframework.scheduling.annotation.Scheduled
     * @see org.springframework.scheduling.annotation.Scheduled#cron()
     */
    @Scheduled(cron = "*/30 * * * * *")
    public void nettoyerTable() {
        final Instant now = Instant.now();
        log.info("Suppression des token à {}", now);
        this.validationRepository.deleteAllByExpirationBefore(now);
    }
}
