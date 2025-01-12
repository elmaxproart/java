package Sea.incubator.sgdeb.repository;

import Sea.incubator.sgdeb.model.PvUE;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PvRepository extends JpaRepository<PvUE, Integer> {
}
