package Sea.incubator.sgdeb.repository;

import Sea.incubator.sgdeb.entite.Avis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;


public interface AvisRepository extends JpaRepository<Avis, Integer> {
}
