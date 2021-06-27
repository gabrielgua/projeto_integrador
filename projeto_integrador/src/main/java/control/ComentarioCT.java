package control;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import model.*;
import service.*;

import java.io.Serializable;
import java.util.List;

@Named("ComentarioCT")
@ConversationScoped
public class ComentarioCT implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5755340462771616908L;

	private Comentario comentario = new Comentario();
	private List<Comentario> comentarios;
	
	@Inject
	private ComentarioService service;
	
	@Inject 
	private JogoCT jogoCT;
	
	@Inject 
	private JogoService jogoService;
		
	public ComentarioCT() {
		// TODO Auto-generated constructor stub
	}

	@PostConstruct
	public void init() {
		setComentarios(service.listar());
	}
	
	public String novo() {
		return "/comentario/cadastrar.xhtml?faces-redirect=true";
	}
	
	public String cadastrar(Jogo jogo, Usuario u) {
		
		comentario.setComentario_id(null);
		comentario.setJogo(jogo);
		comentario.setUsuario(u);
		jogoCT.getJogo().setComentarios(service.listar(jogo));
		
		setComentarios(service.listar());
		service.persistir(comentario);
		comentario.setTexto("");
		jogoCT.setJogos(jogoService.listar());
		jogoCT.setJogo(jogoService.ver(jogo.getJogo_id()));
		
		return "/usuario/main.xhtml?faces-redirect=true";	
		
	}
	
	public String remover(Long id, Long id_usuario) {
		
		service.remover(id);
		setComentarios(service.listar());
		jogoCT.jogos(id_usuario);
		return "/jogo/index.xhtml?faces-redirect=true"; 
	}
	
	public String editar(Long id) {	
		comentario = service.ver(id);
		return "/comentario/editar.xhtml";
	}
	
	public String aplicar(Long id_usuario) {		
		
		if (service.editar(comentario) == null) {
			comentarios = service.listar();	
			return "/jogo/index.xhtml?faces-redirect=true";			
		}
		return "/comentario/editar.xhtml";
		
	}
	
	public String ver(Long id) {
		setComentario(service.ver(id));
		return "/comentario/ver.xhtml?faces-redirect=true";
	}

	public Comentario getComentario() {
		return comentario;
	}

	public void setComentario(Comentario comentario) {
		this.comentario = comentario;
	}

	public List<Comentario> getComentarios() {
		return comentarios;
	}

	public void setComentarios(List<Comentario> comentarios) {
		this.comentarios = comentarios;
	}

}
