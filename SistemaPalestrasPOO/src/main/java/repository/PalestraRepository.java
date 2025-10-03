package repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import model.Palestra;
import model.Participante;
import model.Palestrante;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PalestraRepository {

    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("meuPU");

    public void cadastrar(Palestra palestra) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(palestra);
        em.getTransaction().commit();
        em.close();
    }

    public void atualizar(Palestra palestra) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(palestra);
        em.getTransaction().commit();
        em.close();
    }

    public void remover(Palestra palestra) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Palestra p = em.find(Palestra.class, palestra.getId());
        if (p != null) {
            em.remove(p);
        }
        em.getTransaction().commit();
        em.close();
    }

    public Palestra buscarPorId(Long id) {
        EntityManager em = emf.createEntityManager();
        Palestra p = em.find(Palestra.class, id);
        em.close();
        return p;
    }

    public Palestra buscarPorIdComParticipantes(Long id) {
        EntityManager em = emf.createEntityManager();
        Palestra p = null;
        try {
            // Usa getResultList() para evitar a NoResultException
        List<Palestra> resultados = em.createQuery(
                "SELECT p FROM Palestra p LEFT JOIN FETCH p.participantes WHERE p.id = :id",
                Palestra.class)
                .setParameter("id", id)
                .getResultList();

        // Verifica se encontrou a palestra
        if (!resultados.isEmpty()) {
            p = resultados.get(0);
            }
        } finally {
            // Garante que o EntityManager seja fechado, mesmo se houver erro
            em.close();
        }
        return p; // Retorna a palestra ou null se não for encontrada
}

    public List<Palestra> listarTodas() {
        EntityManager em = emf.createEntityManager();
        List<Palestra> list = em.createQuery(
                "SELECT DISTINCT p FROM Palestra p LEFT JOIN FETCH p.participantes",
                Palestra.class)
                .getResultList();
        em.close();
        return list;
    }

    public List<Palestra> buscarPorNome(String nome) {
        EntityManager em = emf.createEntityManager();
        List<Palestra> resultados = em.createQuery(
                "SELECT p FROM Palestra p WHERE LOWER(p.nome) LIKE :nome",
                Palestra.class)
                .setParameter("nome", "%" + nome.toLowerCase() + "%")
                .getResultList();
        em.close();
        return resultados;
    }

    public List<Palestra> buscarPorEvento(String evento) {
        EntityManager em = emf.createEntityManager();
        List<Palestra> resultados = em.createQuery(
                "SELECT p FROM Palestra p WHERE LOWER(p.evento) LIKE :evento",
                Palestra.class)
                .setParameter("evento", "%" + evento.toLowerCase() + "%")
                .getResultList();
        em.close();
        return resultados;
    }

    public List<Palestra> buscarPorData(String dataStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate data = LocalDate.parse(dataStr, formatter);

        EntityManager em = emf.createEntityManager();
        List<Palestra> resultados = em.createQuery(
                "SELECT p FROM Palestra p WHERE p.data = :data",
                Palestra.class)
                .setParameter("data", data)
                .getResultList();
        em.close();
        return resultados;
    }

    public List<Palestra> buscarPorIntervaloDeData(LocalDate dataInicial, LocalDate dataFinal) {
        EntityManager em = emf.createEntityManager();
        List<Palestra> resultados = em.createQuery(
                "SELECT p FROM Palestra p WHERE p.data BETWEEN :dataInicial AND :dataFinal",
                Palestra.class)
                .setParameter("dataInicial", dataInicial)
                .setParameter("dataFinal", dataFinal)
                .getResultList();
        em.close();
        return resultados;
    }

    public void desvincularPalestrante(Palestra palestra) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        // Busca a palestra para garantir que seja uma entidade gerenciada (managed)
        Palestra p = em.find(Palestra.class, palestra.getId());

        if (p != null) {
            // Desvincula a relação: define o palestrante como null
            p.setPalestrante(null);

            // Persiste a mudança
            em.merge(p);
        }

        em.getTransaction().commit();
        em.close();
    }

    public void inscreverParticipante(Palestra palestra, Participante participante) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Palestra p = em.find(Palestra.class, palestra.getId());
        Participante part = em.find(Participante.class, participante.getId());

        if (!p.getParticipantes().contains(part)) {
            p.getParticipantes().add(part);
            part.getPalestras().add(p);
        }

        em.getTransaction().commit();
        em.close();
    }

    public void cancelarInscricao(Palestra palestra, Participante participante) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Palestra p = em.find(Palestra.class, palestra.getId());
        Participante part = em.find(Participante.class, participante.getId());

        if (p.getParticipantes().contains(part)) {
            p.getParticipantes().remove(part);
            part.getPalestras().remove(p);
        }

        em.getTransaction().commit();
        em.close();
    }
}