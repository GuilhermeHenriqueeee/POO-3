package model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Palestra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private LocalDate data;
    private String evento;

    @ManyToOne
    @JoinColumn(name = "palestrante_id")
    private Palestrante palestrante;

    @ManyToMany
    @JoinTable(
        name = "inscricoes",
        joinColumns = @JoinColumn(name = "palestra_id"),
        inverseJoinColumns = @JoinColumn(name = "participante_id")
    )
    private List<Participante> participantes = new ArrayList<>();

    public Palestra() {}

    public Palestra(String nome, LocalDate data, String evento) {
        this.nome = nome;
        this.data = data;
        this.evento = evento;
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

    public LocalDate getData() {
        return data;
    }

    // Setter correto
    public void setData(LocalDate data) {
        this.data = data;
    }

    // Sobrecarga opcional: recebe String no formato "dd/MM/yyyy"
    public void setData(String dataStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        this.data = LocalDate.parse(dataStr, formatter);
    }

    public String getEvento() {
        return evento;
    }
    public void setEvento(String evento) {
        this.evento = evento;
    }

    public Palestrante getPalestrante() {
        return palestrante;
    }
    public void setPalestrante(Palestrante palestrante) {
        this.palestrante = palestrante;
    }

    public List<Participante> getParticipantes() {
        return participantes;
    }
    public void setParticipantes(List<Participante> participantes) {
        this.participantes = participantes;
    }

    // métodos auxiliares
    public void adicionarParticipante(Participante participante) {
        if (!participantes.contains(participante)) {
            participantes.add(participante);
            participante.getPalestras().add(this);
        }
    }

    public void removerParticipante(Participante participante) {
        if (participantes.contains(participante)) {
            participantes.remove(participante);
            participante.getPalestras().remove(this);
        }
    }
}
