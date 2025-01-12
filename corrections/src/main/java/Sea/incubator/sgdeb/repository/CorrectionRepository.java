package Sea.incubator.sgdeb.repository;

import Sea.incubator.sgdeb.model.Correction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * The interface Correction repository.
 */
public interface CorrectionRepository extends JpaRepository<Correction, UUID> {

  /**
   * Find by id and date suppression is null optional.
   *
   * @param uuid the uuid
   * @return the optional
   */
  Optional<Correction> findByIdAndDateSuppressionIsNull(UUID uuid);

  /**
   * Find date suppression is null list.
   *
   * @return the list
   */
  List<Correction> findByDateSuppressionIsNull();

  /**
   * Find by anonymat and date suppression is null optional.
   *
   * @param anonymat the anonymat
   * @return the optional
   */
  Optional<Correction> findByAnonymatAndDateSuppressionIsNull(int anonymat);
}