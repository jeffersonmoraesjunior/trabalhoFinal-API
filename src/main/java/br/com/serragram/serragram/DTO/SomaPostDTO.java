package br.com.serragram.serragram.DTO;

import java.math.BigInteger;

public interface SomaPostDTO {
	
	public Long getUserId();
	public String getNome();
	public String getSobreNome();
	public BigInteger getTotalPosts();
	public BigInteger getTotalComments();

}

