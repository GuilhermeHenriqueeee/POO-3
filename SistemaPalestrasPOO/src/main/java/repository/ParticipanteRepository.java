package repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Persistence;
import model.Participante;
import model.Palestra;

import java.util.List;

public class ParticipanteRepository {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("meuPU");

    public void cadastrar(Participante participante) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(participante);
        em.getTransaction().commit();
        em.close();
    }

    public void atualizar(Participante participante) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(participante);
        em.getTransaction().commit();
        em.close();
    }

    public Participante buscarPorId(Long id) {
        EntityManager em = emf.createEntityManager();
        Participante p = em.find(Participante.class, id);
        em.close();
        return p;
    }

    public Participante buscarPorIdComPalestras(Long id) {
        EntityManager em = emf.createEntityManager();
        Participante p = em.createQuery(
                "SELECT p FROM Participante p LEFT JOIN FETCH p.palestras WHERE p.id = :id",
                Participante.class)
                .setParameter("id", id)
                .getSingleResult();
        em.close();
        return p;
    }

    public Participante buscarPorLogin(String login) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery(
                    "SELECT p FROM Participante p WHERE p.login = :login",
                    Participante.class)
                    .setParameter("login", login)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    public List<Participante> listarTodos() {
        EntityManager em = emf.createEntityManager();
        List<Participante> list = em.createQuery("SELECT p FROM Participante p", Participante.class)
                .getResultList();
        em.close();
        return list;
    }

    public void inscrever(Participante participante, Palestra palestra) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Participante p = em.find(Participante.class, participante.getId());
        Palestra pl = em.find(Palestra.class, palestra.getId());

        // Inicializa a coleção para evitar LazyInitializationException
        p.getPalestras().size();

        if (!p.getPalestras().contains(pl)) {
            p.getPalestras().add(pl);
            pl.getParticipantes().add(p);
        }

        em.getTransaction().commit();
        em.close();
    }

    public void cancelarInscricao(Participante participante, Palestra palestra) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Participante p = em.find(Participante.class, participante.getId());
        Palestra pl = em.find(Palestra.class, palestra.getId());

        // Inicializa a coleção
        p.getPalestras().size();

        if (p.getPalestras().contains(pl)) {
            p.getPalestras().remove(pl);
            pl.getParticipantes().remove(p);
        }

        em.getTransaction().commit();
        em.close();
    }
}
