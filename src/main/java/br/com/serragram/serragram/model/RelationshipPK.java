package br.com.serragram.serragram.model;

import java.util.Objects;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class RelationshipPK {
	
	@ManyToOne
	@JoinColumn(name = "id_user_seguidor")
	private User userSeguidor;
	
	@ManyToOne
	@JoinColumn(name = "id_user_seguido")
	private User userSeguido;

	public User getUserSeguidor() {
		return userSeguidor;
	}

	public void setUserSeguidor(User userSeguidor) {
		this.userSeguidor = userSeguidor;
	}

	public User getUserSeguido() {
		return userSeguido;
	}

	public void setUserSeguido(User userSeguido) {
		this.userSeguido = userSeguido;
	}

	@Override
	public int hashCode() {
		return Objects.hash(userSeguido, userSeguidor);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RelationshipPK other = (RelationshipPK) obj;
		return Objects.equals(userSeguido, other.userSeguido) && Objects.equals(userSeguidor, other.userSeguidor);
	}

	
}
