package br.com.bluesburger.invoice.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Invoice {

	private Long id;
	
	private InvoiceStatus status;
	
	private String orderId;
}
