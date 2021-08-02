package br.ufrn.searchsystem.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "words", schema = "public")
public class Word {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    
    
    @Column(name = "word", nullable = false)
    private String word;
    
    @Override
    public String toString() {
        return "{" + "id: " + id + ", word: " + word + '}';
    }
}