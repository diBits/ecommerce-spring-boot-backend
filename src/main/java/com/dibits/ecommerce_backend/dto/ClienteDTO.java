package com.dibits.ecommerce_backend.dto;

import java.io.Serializable;

import org.hibernate.validator.constraints.Length;

import com.dibits.ecommerce_backend.domain.Cliente;
import com.dibits.ecommerce_backend.services.validation.ClienteUpdate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@ClienteUpdate
public class ClienteDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	
	@NotBlank(message = "Obrigatório")
	@Length(min = 5, max = 120, message = "Tamanho entre 5 e 120 caracteres")
	private String nome;
	
	@NotBlank(message = "Obrigatório")
	@Email(message = "Email Inválido")
	private String email;

	public ClienteDTO() {

	}

	public ClienteDTO(Cliente obj) {
		
		id = obj.getId();
		nome = obj.getNome();
		email = obj.getEmail();		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
