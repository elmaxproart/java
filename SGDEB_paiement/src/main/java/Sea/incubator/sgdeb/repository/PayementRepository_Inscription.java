package Sea.incubator.sgdeb.repository;

import Sea.incubator.sgdeb.model.PayementI;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface  PayementRepository_Inscription extends JpaRepository<PayementI, Long> {

    Optional<PayementI> findByMatriculeEtu(String Matricule);
    Optional<PayementI> findBycodeBank(long code);


}