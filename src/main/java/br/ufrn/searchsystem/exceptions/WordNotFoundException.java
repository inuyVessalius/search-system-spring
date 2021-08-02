package br.ufrn.searchsystem.exceptions;

public class WordNotFoundException extends RuntimeException {
    public WordNotFoundException(Long id) {
        super("Could not find word " + id);
    }
}