package br.ufrn.searchsystem.repositories.repository3;

import br.ufrn.searchsystem.entities.Word;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WordsRepository3 extends JpaRepository<Word, Long> {
    Optional<Word> findByWord(String word);
}