package model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Participante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Column(unique = true, nullable = false)
    private String login;

    @Column(nullable = false)
    private String senha;

    @ManyToMany(mappedBy = "participantes", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Palestra> palestras = new ArrayList<>();

    public Participante() {}

    public Participante(String nome, String login, String senha) {
        this.nome = nome;
        this.login = login;
        this.senha = senha;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public List<Palestra> getPalestras() { return palestras; }
    public void setPalestras(List<Palestra> palestras) { this.palestras = palestras; }

    public void inscrever(Palestra palestra) {
        if (!palestras.contains(palestra)) {
            palestras.add(palestra);
            palestra.getParticipantes().add(this);
        }
    }

    public void cancelarInscricao(Palestra palestra) {
        if (palestras.contains(palestra)) {
            palestras.remove(palestra);
            palestra.getParticipantes().remove(this);
        }
    }

    public List<Palestra> getPalestrasInscritas() {
        return palestras;
    }
}
