package Sea.incubator.sgdeb.repository;

import Sea.incubator.sgdeb.external.enumType.TypeEval;
import Sea.incubator.sgdeb.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

public interface NoteRepository extends JpaRepository<Note, UUID> {

    Optional<Note> findByAnonimat(String string);

    Optional<Note> findByMatriculeAndAnonimat(String matricule, String anonimat);

    Note findByIdGrilleExamen_TypeEval(TypeEval typeEval);
}
