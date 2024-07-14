package br.com.bluesburger.invoice.domain.usecase;

import br.com.bluesburger.invoice.application.dto.order.InvoiceDto;
import br.com.bluesburger.invoice.application.dto.order.OrderRequest;

public interface RegistrarPedidoUseCase {

	InvoiceDto executar(OrderRequest orderRequest);
}
