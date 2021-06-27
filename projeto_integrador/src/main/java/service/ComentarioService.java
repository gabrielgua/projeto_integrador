package service;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import model.*;


/**
 * Session Bean implementation class ProdutoService
 */
@Stateless
public class ComentarioService {

	@PersistenceContext
	private EntityManager em;
	
    /**
     * Default constructor. 
     */
    public ComentarioService() {
        // TODO Auto-generated constructor stub
    }

    
    public void persistir(Comentario comentario) {
    	em.persist(comentario);
    }
    

    public void remover(Long id) {
    	em.remove(em.getReference(Comentario.class, id));
    }
    
    public List<Comentario> listar() {
    	return em.createQuery("FROM Comentario c", Comentario.class).getResultList();
    }
    
    public List<Comentario> listar(Jogo j) {
    	return em.createQuery("FROM Comentario c WHERE c.jogo.jogo_id = :id", Comentario.class)
    			.setParameter("id", j.getJogo_id())
    			.getResultList();
    }
    
    public Comentario ver(Long id) {
    	Comentario coment = em.find(Comentario.class, id);
    	return coment;
    }
    
    public Comentario editar(Comentario p) {  
    	em.merge(p); 	
		return null;
    	
	}
}
