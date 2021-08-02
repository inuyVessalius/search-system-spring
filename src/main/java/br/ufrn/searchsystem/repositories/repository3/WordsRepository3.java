package br.ufrn.searchsystem.repositories.repository3;

import br.ufrn.searchsystem.entities.Word;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WordsRepository3 extends JpaRepository<Word, Long> {
}