// Participante.java
package model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Participante {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String login;
    private String senha;

    @ManyToMany
    @JoinTable(name = "participante_palestra",
        joinColumns = @JoinColumn(name = "participante_id"),
        inverseJoinColumns = @JoinColumn(name = "palestra_id"))
    private List<Palestra> palestras = new ArrayList<>();

    // Getters e Setters
}
