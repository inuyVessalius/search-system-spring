package br.ufrn.searchsystem.repositories.repository2;

import br.ufrn.searchsystem.entities.Word;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WordsRepository2 extends JpaRepository<Word, Long> {
    Optional<Word> findByWord(String word);
}