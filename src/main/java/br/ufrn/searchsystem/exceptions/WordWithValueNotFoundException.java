package br.ufrn.searchsystem.exceptions;

public class WordWithValueNotFoundException extends RuntimeException {
    public WordWithValueNotFoundException(String word) {
        super("Could not find word " + word);
    }
}