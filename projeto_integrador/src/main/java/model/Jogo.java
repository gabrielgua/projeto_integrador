package model;

import java.io.Serializable;
import java.lang.Long;
import java.lang.String;
import java.util.List;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Jogo
 *
 */
@Entity
@Table(name="jogo")
public class Jogo implements Serializable {

	   
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="jogo_sequence")
	@SequenceGenerator(name="jogo_sequence", sequenceName="jogo_sequence")
	@Column(name = "jogo_id")
	private Long jogo_id;
	
	@ManyToOne
	@JoinColumn(name = "usuario_id")
	private Usuario usuario;

	@Column
	private String titulo;
	
	@Column
	private int likes;
	
	@Column
	private double avaliacao;
	
	@Column
	private String genero;
	
	@OneToMany(mappedBy = "jogo", fetch = FetchType.EAGER, orphanRemoval = true,  cascade = CascadeType.DETACH)
	private List<Comentario> comentarios;
	
	private static final long serialVersionUID = 1L;

	public Jogo() {
		super();
		usuario = new Usuario();
	}

	public Long getJogo_id() {
		return jogo_id;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public int getLikes() {
		return likes;
	}

	public void setLikes(int likes) {
		this.likes = likes;
	}

	public double getAvaliacao() {
		return avaliacao;
	}

	public void setAvaliacao(double avaliacao) {
		this.avaliacao = avaliacao;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public List<Comentario> getComentarios() {
		return comentarios;
	}

	public void setComentarios(List<Comentario> comentarios) {
		this.comentarios = comentarios;
	}

	public void setJogo_id(Long jogo_id) {
		this.jogo_id = jogo_id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(avaliacao);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((comentarios == null) ? 0 : comentarios.hashCode());
		result = prime * result + ((genero == null) ? 0 : genero.hashCode());
		result = prime * result + ((jogo_id == null) ? 0 : jogo_id.hashCode());
		result = prime * result + likes;
		result = prime * result + ((titulo == null) ? 0 : titulo.hashCode());
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
		Jogo other = (Jogo) obj;
		if (Double.doubleToLongBits(avaliacao) != Double.doubleToLongBits(other.avaliacao))
			return false;
		if (comentarios == null) {
			if (other.comentarios != null)
				return false;
		} else if (!comentarios.equals(other.comentarios))
			return false;
		if (genero == null) {
			if (other.genero != null)
				return false;
		} else if (!genero.equals(other.genero))
			return false;
		if (jogo_id == null) {
			if (other.jogo_id != null)
				return false;
		} else if (!jogo_id.equals(other.jogo_id))
			return false;
		if (likes != other.likes)
			return false;
		if (titulo == null) {
			if (other.titulo != null)
				return false;
		} else if (!titulo.equals(other.titulo))
			return false;
		if (usuario == null) {
			if (other.usuario != null)
				return false;
		} else if (!usuario.equals(other.usuario))
			return false;
		return true;
	}

	
	
}
