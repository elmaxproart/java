package Sea.incubator.sgdeb.repository;

import Sea.incubator.sgdeb.model.PayementP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface PayementRepository_Preinscription extends JpaRepository<PayementP, Long> {

    Optional<PayementP> findByCodeBank(String codeBank);

}