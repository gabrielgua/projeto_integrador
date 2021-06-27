package control;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import model.*;
import service.*;

import java.io.Serializable;
import java.util.List;

@Named("JogoCT")
@ConversationScoped
public class JogoCT implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Jogo jogo = new Jogo();
	private List<Jogo> jogos;
	
	@Inject
	private JogoService service;
	
	@Inject
	private ComentarioCT comCT;
	
	@Inject
	private UsuarioCT usuarioCT;
	
	@Inject
	private UsuarioService usuarioService;
	
	@Inject
	private ComentarioService comentarioService;
	
	public JogoCT() {
		// TODO Auto-generated constructor stub
	}

	@PostConstruct
	public void init() {
		setJogos(service.listar());
		
	}

	public String jogos(Long id_usuario) {
		usuarioCT.setUsuario(usuarioService.ver(id_usuario));
		return "/jogo/index.xhtml?faces-redirect=true";
	}
	
	public String addJogoPagina() {
		jogo.setTitulo("");
		jogo.setGenero("");
		return "/jogo/addJogo.xhtml?faces-redirect=true";
	}
	
	public String editar(Long id) {	
		jogo = service.ver(id);
		return "/jogo/editar.xhtml";
	}
	
	public void like(Long id) {
	
		jogo.setLikes(jogo.getLikes() + 1);
		jogo.setComentarios(comentarioService.listar(jogo));
		service.editar(jogo);
		jogo = service.ver(id);
		jogos = service.listar();
		ver(jogo.getJogo_id());
	}
	
	public String aplicar() {		
		if (service.editar(jogo) == null) {
			jogos = service.listar();
			comCT.setComentarios(comentarioService.listar());
			usuarioCT.setUsuario(usuarioService.ver(jogo.getUsuario().getUsuario_id()));
			return "/jogo/index.xhtml?faces-redirect=true";
		}
		
		return "/jogo/editar.xhtml";
	}
	
	public String ver(Long id) {
		jogo = service.ver(id);
		jogo.setComentarios(comentarioService.listar(jogo));
		return "/jogo/ver.xhtml?faces-redirect-true";
	}
	
	public String removerPagina(Long id) {
		jogo = service.ver(id);
		return "/jogo/remover.xhtml?faces-redirect-true";
	}
	
	public String remover(Long id) {
		
		service.remover(id);
		setJogos(service.listar());
		comCT.setComentarios(comentarioService.listar());
		usuarioCT.setUsuario(usuarioService.ver(jogo.getUsuario().getUsuario_id()));
		usuarioCT.setUsuarios(usuarioService.listar());
		return "/jogo/index.xhtml?faces-redirect-true";
	}
	
	public String cadastrar(Usuario id_usuario) {
		jogo.setUsuario(id_usuario);
		jogo.setTitulo(jogo.getTitulo().toUpperCase());
		jogo.setJogo_id(null);
		jogo.setComentarios(comentarioService.listar(jogo));
		
		if (service.persistir(jogo) == null) {
			
			setJogos(service.listar());		
			usuarioCT.setUsuario(usuarioService.ver(id_usuario.getUsuario_id()));
			comCT.setComentarios(comentarioService.listar());
			jogo.setTitulo("");
			return "/jogo/index.xhtml?faces-redirect=true";
		} 
		
		return "addJogo.xhtml";
	}

	public Jogo getJogo() {
		return jogo;
	}

	public void setJogo(Jogo jogo) {
		this.jogo = jogo;
	}

	public List<Jogo> getJogos() {
		return jogos;
	}

	public void setJogos(List<Jogo> jogos) {
		this.jogos = jogos;
	}	
}
