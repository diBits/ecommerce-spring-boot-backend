package com.dibits.ecommerce_backend.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dibits.ecommerce_backend.domain.Categoria;
import com.dibits.ecommerce_backend.domain.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {

	@Transactional(readOnly = true)
	@Query("SELECT DISTINCT obj FROM Produto obj INNER JOIN obj.categorias cat WHERE obj.nome LIKE %:nome% AND cat IN :categorias")
	Page<Produto> search(
			@Param("nome") String nome,
			@Param("categorias") List<Categoria> categorias, 
			Pageable pageRequest);

	// findDistinctByNomeContainingAndCategoriasIn (colocando nome do metodo
	// conforme descrito, faz com que o Spring execute a query descrita sem necessidade de executar manualmente)

}