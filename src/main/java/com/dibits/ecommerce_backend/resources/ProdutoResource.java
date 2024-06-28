package com.dibits.ecommerce_backend.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dibits.ecommerce_backend.domain.Produto;
import com.dibits.ecommerce_backend.dto.ProdutoDTO;
import com.dibits.ecommerce_backend.resources.utils.URL;
import com.dibits.ecommerce_backend.services.ProdutoService;

@RestController
@RequestMapping("/produtos")
public class ProdutoResource {

	@Autowired
	private ProdutoService service;

	@GetMapping("/{id}")
	public ResponseEntity<Produto> find(@PathVariable Integer id) {
		System.out.println("Método find foi chamado com id: " + id); // Adicione este log
		Produto obj = service.find(id);
		return ResponseEntity.ok().body(obj);

	}
	
	@GetMapping("/lista")
	public ResponseEntity<Page<ProdutoDTO>> findPage(
			@RequestParam(value = "nome", defaultValue = "") String nome,
			@RequestParam(value = "categorias", defaultValue = "") String categorias,
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "24")Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "nome")String orderBy,
			@RequestParam(value = "direction", defaultValue = "ASC")String direction) {
		
		System.out.println("Método findPage foi chamado.");
	    System.out.println("Parâmetro nome: " + nome);
	    System.out.println("Parâmetro categorias: " + categorias);
		
		String nomeDecoded = URL.decodeParam(nome);
		List<Integer> ids = URL.decodeIntList(categorias);
		Page<Produto> list = service.search(nomeDecoded, ids, page, linesPerPage, orderBy, direction);
		Page<ProdutoDTO> listDto = list.map(obj -> new ProdutoDTO(obj));
		return ResponseEntity.ok().body(listDto);
		
	}
}
