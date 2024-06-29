package com.dibits.ecommerce_backend.services;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.dibits.ecommerce_backend.domain.PagamentoComBoleto;

@Service
public class BoletoService {
	
	public void preencherPagamentoComBoleto(PagamentoComBoleto pgto, Date InstanteDoPedido) {
		
		//Trocar futuramente para webService que gere o boleto;
		Calendar cal = Calendar.getInstance();
		cal.setTime(InstanteDoPedido);
		cal.add(Calendar.DAY_OF_MONTH, 7);
		pgto.setDataVencimento(cal.getTime());
	}

}
