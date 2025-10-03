package model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Palestrante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @OneToMany(mappedBy = "palestrante", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Palestra> palestras = new ArrayList<>();

    public Palestrante() {}

    public Palestrante(String nome) {
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Palestra> getPalestras() {
        return palestras;
    }
    public void setPalestras(List<Palestra> palestras) {
        this.palestras = palestras;
    }

    // métodos auxiliares
    public void adicionarPalestra(Palestra palestra) {
        palestras.add(palestra);
        palestra.setPalestrante(this);
    }

    public void removerPalestra(Palestra palestra) {
        palestras.remove(palestra);
        palestra.setPalestrante(null);
    }
}
