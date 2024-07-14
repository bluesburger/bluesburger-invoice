package br.com.bluesburger.invoice.infra.database;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.bluesburger.invoice.application.dto.order.OrderRequest;
import br.com.bluesburger.invoice.domain.entity.InvoiceStatus;
import br.com.bluesburger.invoice.domain.service.InvoicePort;
import br.com.bluesburger.invoice.infra.database.entity.InvoiceEntity;
import jakarta.persistence.EntityExistsException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(Transactional.TxType.SUPPORTS)
public class InvoiceAdapter implements InvoicePort {

	private final InvoiceRepository invoiceRepository;

	public List<InvoiceEntity> getAll() {
		return invoiceRepository.findAll();
	}
	
	public List<InvoiceEntity> getAllByStep(InvoiceStatus status) {
		return invoiceRepository.findAllByStatusOrderByCreatedTime(status);
	}

	public Optional<InvoiceEntity> getById(Long invoiceId) {
		return invoiceRepository.findById(invoiceId);
	}

	@Transactional(dontRollbackOn = EntityExistsException.class)
	public Optional<InvoiceEntity> createNewInvoice(OrderRequest command) {
		
		var newInvoice = new InvoiceEntity(InvoiceStatus.PENDING, command.getOrderId());		
		var savedOrder = invoiceRepository.save(newInvoice);

		return Optional.of(savedOrder);
	}

	@Transactional(
			rollbackOn = IllegalArgumentException.class, 
			dontRollbackOn = EntityExistsException.class)
	@Override
	public Optional<InvoiceEntity> updateStatus(Long invoiceId, InvoiceStatus status) {
		return getById(invoiceId)
			.map(order -> {
				order.setStatus(status);
				return invoiceRepository.save(order);
			});
	}
	
	@Override
	public Optional<InvoiceEntity> updateStatusByOrderId(String orderId, InvoiceStatus status) {
		return invoiceRepository.findFirstByOrderIdAndStatusOrderByCreatedTimeDesc(orderId, InvoiceStatus.PENDING)
			.map(invoice -> {
				invoice.setStatus(status);
				return invoiceRepository.save(invoice);
			});
	}
}
