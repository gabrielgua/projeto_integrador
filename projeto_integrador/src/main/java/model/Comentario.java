package model;

import java.io.Serializable;
import java.lang.Long;
import java.lang.String;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Comentario
 *
 */
@Entity
@Table(name="comentario")
public class Comentario implements Serializable {
   
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="comentario_SEQ")
	@SequenceGenerator(name="comentario_SEQ", sequenceName="comentario_sequence")
	@Column(name="comentario_id")
	private Long comentario_id;
	
	@ManyToOne
	@JoinColumn(name="jogo_id")
	private Jogo jogo;
	
	@ManyToOne
	@JoinColumn(name="usuario_id")
	private Usuario usuario;

	@Column
	private String texto;
		
	@Column
	private String data_postagem;
	
	
	private static final long serialVersionUID = 1L;

	public Comentario() {
		super();
		jogo = new Jogo();
		usuario = new Usuario();
	}

	public Long getComentario_id() {
		return comentario_id;
	}

	public void setComentario_id(Long comentario_id) {
		this.comentario_id = comentario_id;
	}

	public Jogo getJogo() {
		return jogo;
	}

	public void setJogo(Jogo jogo) {
		this.jogo = jogo;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public String getData_postagem() {
		return data_postagem;
	}

	public void setData_postagem(String data_postagem) {
		this.data_postagem = data_postagem;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((comentario_id == null) ? 0 : comentario_id.hashCode());
		result = prime * result + ((data_postagem == null) ? 0 : data_postagem.hashCode());
		result = prime * result + ((jogo == null) ? 0 : jogo.hashCode());
		result = prime * result + ((texto == null) ? 0 : texto.hashCode());
		result = prime * result + ((usuario == null) ? 0 : usuario.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Comentario other = (Comentario) obj;
		if (comentario_id == null) {
			if (other.comentario_id != null)
				return false;
		} else if (!comentario_id.equals(other.comentario_id))
			return false;
		if (data_postagem == null) {
			if (other.data_postagem != null)
				return false;
		} else if (!data_postagem.equals(other.data_postagem))
			return false;
		if (jogo == null) {
			if (other.jogo != null)
				return false;
		} else if (!jogo.equals(other.jogo))
			return false;
		if (texto == null) {
			if (other.texto != null)
				return false;
		} else if (!texto.equals(other.texto))
			return false;
		if (usuario == null) {
			if (other.usuario != null)
				return false;
		} else if (!usuario.equals(other.usuario))
			return false;
		return true;
	}
}
