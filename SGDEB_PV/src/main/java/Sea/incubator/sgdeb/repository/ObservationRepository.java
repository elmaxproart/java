package Sea.incubator.sgdeb.repository;

import Sea.incubator.sgdeb.model.Observation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ObservationRepository extends JpaRepository<Observation, Long> {
}
