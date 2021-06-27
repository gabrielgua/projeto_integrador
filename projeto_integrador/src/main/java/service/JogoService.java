package service;

import java.util.List;

import javax.ejb.Stateless;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import model.*;

/**
 * Session Bean implementation class JogoService
 */
@Stateless
public class JogoService {

	@PersistenceContext
	private EntityManager em;
	
    /**
     * Default constructor. 
     */
    public JogoService() {
        // TODO Auto-generated constructor stub
    }

    public Jogo persistir(Jogo jogo) {
    	try {
    		
    		Jogo j = (Jogo) em.createQuery("SELECT j FROM Jogo j WHERE j.titulo = :titulo")
    				.setParameter("titulo", jogo.getTitulo().toUpperCase())
    				.getSingleResult();
    		
    		FacesMessage msg1 = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Jogo já cadastrado!", null);
    		FacesContext.getCurrentInstance().addMessage(null, msg1);
    		
    		
    		return j;
    	} catch (NoResultException e) {
    		em.persist(jogo);
    		return null;
    	} 
    }
    
    public Jogo VerificarJogo(Long id) {
    	try {
    		
    		Jogo j = (Jogo) em.createQuery("SELECT j FROM Jogo j WHERE j.usuario.id = :id_user")
    				.setParameter("id_user", id)
    				.getSingleResult();
    		return j;
    		
    	} catch (NoResultException e) {    		
    		return null;
    	}
    }
    
    public Jogo ver(Long id) {
    	Jogo jogo = em.find(Jogo.class, id);
    	return jogo;
    }
    
    
    public void remover(Long id) {	
    	@SuppressWarnings("unchecked")
		List<Comentario> p = (List<Comentario>) em.createQuery("SELECT c FROM Comentario c WHERE c.jogo.id = :id")
    			.setParameter("id", id)	
    			.getResultList();	
    	
    	if (p.size() != 0) em.createQuery("DELETE Comentario c WHERE c.jogo.id = :id").setParameter("id", id);
    	
    	Jogo j = em.getReference(Jogo.class, id);
    	em.remove(j);
    }
    
    public Jogo editar(Jogo l) {  
    	
    	l.setTitulo(l.getTitulo().toUpperCase());
    	try {
    		
    		Jogo loja = em.createQuery("SELECT l FROM Jogo l WHERE l.titulo = :nome AND l.jogo_id != :id", Jogo.class)
    				.setParameter("nome", l.getTitulo())
    				.setParameter("id", l.getJogo_id())
    				.getSingleResult();
    

    		FacesMessage msg1 = new FacesMessage(FacesMessage.SEVERITY_WARN, "Jogo já cadastrado!", null);
			FacesContext.getCurrentInstance().addMessage(null, msg1);
    		
    		return loja;
    		
		} catch (NoResultException e) {
			
			em.merge(l);
			return null;
		}
    	
	}
    
    public List<Jogo> listar() {
    	return em.createQuery("FROM Jogo j", Jogo.class).getResultList();
    }
    		 
    
}
