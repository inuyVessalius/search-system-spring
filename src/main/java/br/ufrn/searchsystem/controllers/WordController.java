package br.ufrn.searchsystem.controllers;

import br.ufrn.searchsystem.entities.Word;
import br.ufrn.searchsystem.exceptions.WordNotFoundException;
import br.ufrn.searchsystem.repositories.repository1.WordsRepository1;
import br.ufrn.searchsystem.repositories.repository2.WordsRepository2;
import br.ufrn.searchsystem.repositories.repository3.WordsRepository3;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@AllArgsConstructor
public class WordController {
    @Autowired
    private final WordsRepository1 repository;
    private final WordsRepository2 repository2;
    private final WordsRepository3 repository3;
    
    @GetMapping("/words")
    Map<String, List<Word>> getAll() {
        Map<String, List<Word>> result = new HashMap<>();
        
        List<Word> words = repository.findAll();
        
        
        if (!words.isEmpty())
            result.put("repository 1", words);
        
        
        words = repository2.findAll();
        if (!words.isEmpty())
            result.put("repository 2", words);
        
        
        words = repository3.findAll();
        if (!words.isEmpty())
            result.put("repository 3", words);
        
        return result;
    }
    
    @PostMapping("/word")
    Map<String, Word> addWord(@RequestBody Word word) {
        int repoNumber = new Random().nextInt(4);
        
        if (repoNumber == 0) {
            return Collections.singletonMap("repository 1", repository.save(word));
        } else if (repoNumber == 1) {
            return Collections.singletonMap("repository 2", repository2.save(word));
        } else {
            return Collections.singletonMap("repository 3", repository3.save(word));
        }
    }
    
    @GetMapping("/word/{id}/{repo}")
    Word getWord(@PathVariable Long id, @PathVariable Integer repo) {
        if (repo == 1)
            return repository.findById(id).orElseThrow(() -> new WordNotFoundException(id));
        else if (repo == 2)
            return repository2.findById(id).orElseThrow(() -> new WordNotFoundException(id));
        else if (repo == 3)
            return repository3.findById(id).orElseThrow(() -> new WordNotFoundException(id));
        else
            throw new WordNotFoundException(id);
    }
    
    @PutMapping("/words/{id}/{repo}")
    Word replaceWord(@RequestBody Word newWord, @PathVariable Long id, @PathVariable Integer repo) {
        if (repo == 1)
            return repository.findById(id).map(word -> {
                word.setWord(newWord.getWord());
                return repository.save(word);
            }).orElseGet(() -> {
                newWord.setId(id);
                return repository.save(newWord);
            });
        else if (repo == 2)
            return repository2.findById(id).map(word -> {
                word.setWord(newWord.getWord());
                return repository2.save(word);
            }).orElseGet(() -> {
                newWord.setId(id);
                return repository2.save(newWord);
            });
        else if (repo == 3)
            return repository3.findById(id).map(word -> {
                word.setWord(newWord.getWord());
                return repository3.save(word);
            }).orElseGet(() -> {
                newWord.setId(id);
                return repository3.save(newWord);
            });
        else
            throw new WordNotFoundException(id);
    }
    
    @DeleteMapping("/word/{id}/{repo}")
    void deleteWord(@PathVariable Long id, @PathVariable Integer repo) {
        if (repo == 1)
            repository.deleteById(id);
        else if (repo == 2)
            repository2.deleteById(id);
        else if (repo == 3)
            repository3.deleteById(id);
        else
            throw new WordNotFoundException(id);
    }
    
    // TODO put, get, delete from id
}