package br.ufrn.searchsystem.repositories.repository1;

import br.ufrn.searchsystem.entities.Word;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WordsRepository1 extends JpaRepository<Word, Long> {
}