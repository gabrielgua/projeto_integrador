package service;



import java.util.List;

import javax.ejb.Stateless;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;

import model.*;

/**
 * Session Bean implementation class UsuarioService
 */
@Stateless
public class UsuarioService {
	
	@PersistenceContext
	private EntityManager em;

    /**
     * Default constructor. 
     */
    public UsuarioService() {
        // TODO Auto-generated constructor stub
    }
    
    
    public Usuario persistir(Usuario usuario) {
    	
    	Usuario u = new Usuario();
    	try {
			
    		String cpf = em.createNativeQuery("SELECT VALIDA_CPF("+usuario.getCpf()+") FROM DUAL").getSingleResult().toString();
    		String email = em.createNativeQuery("SELECT VALIDA_EMAIL('"+usuario.getEmail()+"') FROM DUAL").getSingleResult().toString();
    		
    		if (Integer.parseInt(cpf) != 1 && Integer.parseInt(email) != 1) {    			
    			u = (Usuario) em.createQuery("SELECT u FROM Usuario u WHERE u.email = :email")
    				.setParameter("email", usuario.getEmail().toUpperCase())
    				.getSingleResult();
    			
    			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Email já cadastrado!", null);
    	    	FacesContext.getCurrentInstance().addMessage(null, msg);
    			
    	    	return u;
    		} 
    		
    		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Email ou CPF inválidos!", null);
    		FacesContext.getCurrentInstance().addMessage(null, msg);
    		
    		return u;
    	} catch (NoResultException e) {
    		em.persist(usuario);
    		return null;    		
    	} 
    }
    
    public Boolean remover(Long id) {
    	
    	try {    		

    		Jogo j = (Jogo) em.createQuery("SELECT j FROM Jogo j WHERE j.usuario.id = :id")
    				.setParameter("id", id)
    				.getSingleResult();

    		if (j != null) {
    			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Jogos atrelados à conta!", null);
        		FacesContext.getCurrentInstance().addMessage(null, msg);
    		}
    	
    		return true;
    		
    	} catch(NonUniqueResultException es) {
    		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Jogos atrelados à conta!", null);
    		FacesContext.getCurrentInstance().addMessage(null, msg);
    		return true;
    		
    	} catch (NoResultException e) {
    		em.remove(em.getReference(Usuario.class, id));
    		return false;
    		
    	}
    	
    }
    
    public List<Usuario> listar() {
    	return em.createQuery("FROM Usuario u", Usuario.class).getResultList();
    }
    
    public Usuario logar(String email, String senha) {
    	
    	try {
    		Usuario u = (Usuario) em.createQuery("SELECT u FROM Usuario u WHERE u.email = :email AND u.senha =:senha")
    				.setParameter("email", email.toUpperCase())
    				.setParameter("senha", senha)
    				.getSingleResult();
    		return u;
    	} catch(NoResultException e) {
    		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro de login!", null);
    		FacesContext.getCurrentInstance().addMessage(null, msg);
    		return null;
    	}
    	
    }
    
    public Usuario ver(Long id) {
    	Usuario usuario = em.find(Usuario.class, id);
    	return usuario;
    }
    
    public Usuario editar(Usuario u) {  
    	
    	Usuario usuario = new Usuario();
    	try {
    		u.setEmail(u.getEmail().toUpperCase());    	
    		String cpf = em.createNativeQuery("SELECT VALIDA_CPF("+u.getCpf()+") FROM DUAL").getSingleResult().toString();
    		String email = em.createNativeQuery("SELECT VALIDA_EMAIL('"+u.getEmail()+"') FROM DUAL").getSingleResult().toString();
    		
    		if (Integer.parseInt(cpf) != 1 && Integer.parseInt(email) != 1) {    			
    			usuario = (Usuario) em.createQuery("SELECT u FROM Usuario u WHERE u.email = :email AND u.id != :id")
    					.setParameter("email", u.getEmail().toUpperCase())
    					.setParameter("id", u.getUsuario_id())
    					.getSingleResult();
    			
    			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Email já cadastrado!", null);
    	    	FacesContext.getCurrentInstance().addMessage(null, msg);
    			
    	    	return usuario;
    		} 
    		
    		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Email ou CPF Inválidos!", null);
    		FacesContext.getCurrentInstance().addMessage(null, msg);
    		
    		return usuario;			
		} catch (NoResultException e) {
			em.merge(u); 
			return null;
		}
	}
}
