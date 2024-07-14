package br.com.bluesburger.invoice.domain.service;

import java.util.List;
import java.util.Optional;

import br.com.bluesburger.invoice.application.dto.order.OrderRequest;
import br.com.bluesburger.invoice.domain.entity.InvoiceStatus;
import br.com.bluesburger.invoice.infra.database.entity.InvoiceEntity;

public interface InvoicePort {
	
	List<InvoiceEntity> getAll();

	List<InvoiceEntity> getAllByStep(InvoiceStatus step);
	
	Optional<InvoiceEntity> getById(Long invoiceId);
	
	Optional<InvoiceEntity> createNewInvoice(OrderRequest command);

	Optional<InvoiceEntity> updateStatus(Long invoiceId, InvoiceStatus fase);
	
	Optional<InvoiceEntity> updateStatusByOrderId(String orderId, InvoiceStatus status);
}
