package br.com.suauniversidade.biometria.repository;

import br.com.suauniversidade.biometria.model.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.List;

public class UsuarioRepository {
    private static final EntityManagerFactory emf;
    static{
        //biometria-pu é o nome dado no persistence.xml
        emf = Persistence.createEntityManagerFactory("biometria-pu");
    }

    //Salvar um novo usuário ou atualizar um existente
    public void salvar(Usuario usuario) {
        EntityManager em = emf.createEntityManager();

        try{
            em.getTransaction().begin();
            em.merge(usuario);//merge = se não existe, insere; se existe, atualiza
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();//desfaz em caso de erro
            e.printStackTrace();
        } finally {
            em.close();//fecha o EntityManager
        }
    }
    //Metodo para buscar um usuário pelo ID
    public Usuario buscarPorId(int id) {
        EntityManager em = emf.createEntityManager();

        try{
            return em.find(Usuario.class, id);
        } finally {
            em.close();
        }
    }

    //Metodo de listagem para todos os usuários
    public List<Usuario> listarTodos(){
        EntityManager em = emf.createEntityManager();

        try{
            //JPQL: parece SQL, mas opera sobre objetos(entidades)
            String jpql = "SELECT u FROM Usuario u";
            return em.createQuery(jpql, Usuario.class).getResultList();
        } finally {
            em.close();
        }
    }
}
