package br.ufrn.searchsystem.controllers;

import br.ufrn.searchsystem.entities.Word;
import br.ufrn.searchsystem.exceptions.WordNotFoundException;
import br.ufrn.searchsystem.exceptions.WordWithValueNotFoundException;
import br.ufrn.searchsystem.repositories.repository1.WordsRepository1;
import br.ufrn.searchsystem.repositories.repository2.WordsRepository2;
import br.ufrn.searchsystem.repositories.repository3.WordsRepository3;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@AllArgsConstructor
public class WordController {
    private final WordsRepository1 repository;
    private final WordsRepository2 repository2;
    private final WordsRepository3 repository3;
    
    
    @PostMapping("/word/{value}")
    Map<String, Word> addWord(@PathVariable String value) {
        int repoNumber = new Random().nextInt(4);
        
        Word word = new Word();
        word.setWord(value);
        
        if (repoNumber == 0) {
            word.setId((long) repository.findAll().size() + 1L);
            return Collections.singletonMap("repository 1", repository.save(word));
        } else if (repoNumber == 1) {
            word.setId((long) repository2.findAll().size() + 1L);
            return Collections.singletonMap("repository 2", repository2.save(word));
        } else {
            word.setId((long) repository3.findAll().size() + 1L);
            return Collections.singletonMap("repository 3", repository3.save(word));
        }
    }
    
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
    
    @GetMapping("/word/{id}/{repo}")
    Word getWordById(@PathVariable Long id, @PathVariable Integer repo) {
        if (repo == 1)
            return repository.findById(id).orElseThrow(() -> new WordNotFoundException(id));
        else if (repo == 2)
            return repository2.findById(id).orElseThrow(() -> new WordNotFoundException(id));
        else if (repo == 3)
            return repository3.findById(id).orElseThrow(() -> new WordNotFoundException(id));
        else
            throw new WordNotFoundException(id);
    }
    
    @GetMapping("/word/{word}")
    Map<String, Word> getWord(@PathVariable String word) {
        
        Map<String, Word> result = new HashMap<>();
        
        Optional<Word> w = repository.findByWord(word);
        
        if (w.isPresent()) {
            result.put("repository 1", w.get());
            return result;
        } else {
            w = repository2.findByWord(word);
            if (w.isPresent()) {
                result.put("repository 2", w.get());
                return result;
            } else {
                w = repository3.findByWord(word);
                if (w.isPresent()) {
                    result.put("repository 2", w.get());
                    return result;
                } else
                    throw new WordWithValueNotFoundException(word);
            }
        }
    }
    
    @PutMapping("/word/{id}/{repo}")
    Word replaceWordById(@RequestBody Word newWord, @PathVariable Long id, @PathVariable Integer repo) {
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
    
    @PutMapping("/word/{word}")
    Map<String, Word> replaceWord(@RequestBody Word newWord, @PathVariable String word) {
        Map<String, Word> result = new HashMap<>();
        
        Optional<Word> w = repository.findByWord(word);
        
        if (w.isPresent()) {
            Word toUpdate = w.get();
            toUpdate.setWord(newWord.getWord());
            result.put("repository 1", repository.save(toUpdate));
            return result;
        } else {
            w = repository2.findByWord(word);
            if (w.isPresent()) {
                Word toUpdate = w.get();
                toUpdate.setWord(newWord.getWord());
                result.put("repository 2", repository2.save(toUpdate));
                return result;
            } else {
                w = repository3.findByWord(word);
                if (w.isPresent()) {
                    Word toUpdate = w.get();
                    toUpdate.setWord(newWord.getWord());
                    result.put("repository 3", repository3.save(toUpdate));
                    return result;
                } else
                    throw new WordWithValueNotFoundException(word);
            }
        }
    }
    
    @DeleteMapping("/word/{id}/{repo}")
    void deleteWordById(@PathVariable Long id, @PathVariable Integer repo) {
        if (repo == 1)
            repository.deleteById(id);
        else if (repo == 2)
            repository2.deleteById(id);
        else if (repo == 3)
            repository3.deleteById(id);
        else
            throw new WordNotFoundException(id);
    }
    
    @DeleteMapping("/word/{word}")
    String deleteWord(@PathVariable String word) {
        Optional<Word> w = repository.findByWord(word);
        
        if (w.isPresent()) {
            repository.delete(w.get());
            return "removido " + word + " do repository 1";
        } else {
            w = repository2.findByWord(word);
            if (w.isPresent()) {
                repository2.delete(w.get());
                return "removido " + word + " do repository 2";
            } else {
                w = repository3.findByWord(word);
                if (w.isPresent()) {
                    repository3.delete(w.get());
                    return "removido " + word + " do repository 3";
                } else
                    throw new WordWithValueNotFoundException(word);
            }
        }
    }
}