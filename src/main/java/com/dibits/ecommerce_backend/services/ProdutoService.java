package com.dibits.ecommerce_backend.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.dibits.ecommerce_backend.domain.Categoria;
import com.dibits.ecommerce_backend.domain.Produto;
import com.dibits.ecommerce_backend.repositories.CategoriaRepository;
import com.dibits.ecommerce_backend.repositories.ProdutoRepository;
import com.dibits.ecommerce_backend.services.exception.ObjectNotFoundException;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository repo;

	@Autowired
	private CategoriaRepository catRepo;

	public Produto find(Integer id) {

		Produto obj = repo.findById(id).orElse(null);
		if (obj == null) {

			throw new ObjectNotFoundException("Objeto inexistente! Id: " + id + ", Pacote: " + Produto.class.getName());
		}
		return obj;
	}

	public Page<Produto> search(String nome, List<Integer> ids, Integer page, Integer linesPerPage, String orderBy, String direction) {
		
		System.out.println("Método page produtoservice foi chamado."); // Adicione este log
	    PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
	    
	    List<Categoria> categorias = catRepo.findAllById(ids);
	    
	    
	    return repo.search(nome, categorias, pageRequest);
		
		
	}
}
