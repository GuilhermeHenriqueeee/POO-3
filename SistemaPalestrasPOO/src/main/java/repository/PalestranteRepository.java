package repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import model.Palestrante;

import java.util.List;

public class PalestranteRepository {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("meuPU");

    public void cadastrar(Palestrante palestrante) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(palestrante);
        em.getTransaction().commit();
        em.close();
    }

    public void atualizar(Palestrante palestrante) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(palestrante);
        em.getTransaction().commit();
        em.close();
    }

    public void remover(Palestrante palestrante) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Palestrante p = em.find(Palestrante.class, palestrante.getId());
        if (p != null) {
            em.remove(p);
        }
        em.getTransaction().commit();
        em.close();
    }

    public Palestrante buscarPorId(Long id) {
        EntityManager em = emf.createEntityManager();
        Palestrante p = em.find(Palestrante.class, id);
        em.close();
        return p;
    }

    public List<Palestrante> listarTodos() {
        EntityManager em = emf.createEntityManager();
        List<Palestrante> list = em.createQuery("SELECT p FROM Palestrante p", Palestrante.class)
                                   .getResultList();
        em.close();
        return list;
    }

    public List<Palestrante> buscarPorNome(String nome) {
        EntityManager em = emf.createEntityManager();
        List<Palestrante> resultados = em.createQuery(
                "SELECT p FROM Palestrante p WHERE LOWER(p.nome) LIKE :nome", Palestrante.class)
                .setParameter("nome", "%" + nome.toLowerCase() + "%")
                .getResultList();
        em.close();
        return resultados;
    }

    // Carrega palestrante com todas as palestras (para evitar LazyInitializationException)
    public Palestrante buscarPorIdComPalestras(Long id) {
        EntityManager em = emf.createEntityManager();
        Palestrante p = null;
        try {
            List<Palestrante> result = em.createQuery(
                    "SELECT p FROM Palestrante p LEFT JOIN FETCH p.palestras WHERE p.id = :id", Palestrante.class)
                    .setParameter("id", id)
                    .getResultList(); // retorna lista em vez de getSingleResult()
            if (!result.isEmpty()) {
                p = result.get(0);
            }
        } finally {
            em.close();
        }
        return p; // retorna null se não encontrar
    }

    public List<Palestrante> listarTodosComPalestras() {
        EntityManager em = emf.createEntityManager();
        List<Palestrante> list = em.createQuery(
                "SELECT DISTINCT p FROM Palestrante p LEFT JOIN FETCH p.palestras", Palestrante.class)
                .getResultList();
        em.close();
        return list;
    }
}
