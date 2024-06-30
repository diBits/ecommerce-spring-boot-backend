package com.dibits.ecommerce_backend.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dibits.ecommerce_backend.domain.ItemPedido;
import com.dibits.ecommerce_backend.domain.PagamentoComBoleto;
import com.dibits.ecommerce_backend.domain.Pedido;
import com.dibits.ecommerce_backend.domain.Produto;
import com.dibits.ecommerce_backend.domain.enums.EstadoPagamento;
import com.dibits.ecommerce_backend.repositories.ClienteRepository;
import com.dibits.ecommerce_backend.repositories.ItemPedidoRepository;
import com.dibits.ecommerce_backend.repositories.PagamentoRepository;
import com.dibits.ecommerce_backend.repositories.PedidoRepository;
import com.dibits.ecommerce_backend.repositories.ProdutoRepository;
import com.dibits.ecommerce_backend.services.exception.ObjectNotFoundException;

import jakarta.validation.Valid;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repo;
	
	@Autowired
	private BoletoService boletoService;
	
	@Autowired
	private PagamentoRepository pgtoRepo;
	
	@Autowired
	private ProdutoRepository prodRepo;
	
	@Autowired
	private ItemPedidoRepository itemPedRepo;
	
	@Autowired
	private ClienteRepository clirepo;

	public Pedido find(Integer id) {

		Pedido obj = repo.findById(id).orElse(null);
		if (obj == null) {

			throw new ObjectNotFoundException("Objeto inexistente! Id: " + id + ", Pacote: " + Pedido.class.getName());
		}
		return obj;
	}

	@Transactional
	public @Valid Pedido insert(@Valid Pedido obj) {
		// TODO Auto-generated method stub
		obj.setId(null);
		obj.setInstante(new Date());
		obj.setCliente(clirepo.findById(obj.getCliente().getId()).orElseThrow(() -> new RuntimeException("Cliente não encontrado")));
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);

		if (obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pgto = (PagamentoComBoleto) obj.getPagamento();
			boletoService.preencherPagamentoComBoleto(pgto, obj.getInstante());
		}

		obj = repo.save(obj);
		pgtoRepo.save(obj.getPagamento());
		for (ItemPedido ip : obj.getItens()) {
		    ip.setDesconto(0.0);
		    
		    // Use findById e obtenha o produto se ele existir
		    Optional<Produto> produtoOpt = prodRepo.findById(ip.getProduto().getId());
		    if (produtoOpt.isPresent()) {
		        Produto produto = produtoOpt.get();
		        ip.setProduto(produto);
		        ip.setPreco(produto.getPreco());
		    } else {
		        // Lida com o caso em que o produto não é encontrado
		        throw new RuntimeException("Produto não encontrado: " + ip.getProduto().getId());
		    }

		    ip.setPedido(obj);
		}

		itemPedRepo.saveAll(obj.getItens());
		
		System.out.println(obj);

		return obj;
	}

}
