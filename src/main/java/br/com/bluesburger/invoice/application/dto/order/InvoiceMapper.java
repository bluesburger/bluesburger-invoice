package br.com.bluesburger.invoice.application.dto.order;

import org.springframework.stereotype.Component;

import br.com.bluesburger.invoice.domain.entity.Invoice;
import br.com.bluesburger.invoice.infra.database.entity.InvoiceEntity;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class InvoiceMapper {
	
	public Invoice toOrder(InvoiceEntity entity) {
		return new Invoice(entity.getId(), entity.getStatus(), entity.getOrderId());
	}

	public InvoiceDto toOrderDto(InvoiceEntity invoice) {
		return new InvoiceDto(invoice.getId(), invoice.getStatus(), invoice.getOrderId());
	}
	
	public InvoiceEntity toOrderEntity(InvoiceDto orderDto) {
		return new InvoiceEntity(orderDto.getId(), null, null, orderDto.getStatus(), orderDto.getOrderId());
	}
}
