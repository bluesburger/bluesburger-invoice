package br.com.bluesburger.invoice.application.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.com.bluesburger.invoice.application.sqs.events.InvoiceIssueEvent;
import br.com.bluesburger.invoice.domain.entity.InvoiceStatus;
import br.com.bluesburger.invoice.domain.service.InvoicePort;
import br.com.bluesburger.invoice.infra.sqs.events.InvoiceIssueEventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class InvoiceGeneratorScheduler {
	
	private final InvoicePort invoicePort;
	
	private final InvoiceIssueEventPublisher invoiceIssueEventPublisher;
	
	@Scheduled(fixedRateString = "${scheduler.invoices.fixedRate:5000}")
	public void generateInvoices() {
		log.info("Buscando notas pendentes de geração");
		invoicePort.getAllByStatus(InvoiceStatus.PENDING).forEach(entity -> {
			try {
				log.info("Encontrada nota pendente de geração: {}", entity);
				// TODO: gerar nota
				invoicePort.updateStatus(entity.getId(), InvoiceStatus.ISSUED)
					.ifPresent(invoice -> {
						invoiceIssueEventPublisher
							.publish(InvoiceIssueEvent.builder()
									.orderId(invoice.getOrderId())
									.status(invoice.getStatus())
									.build());
					});
			} catch(Exception e) {
				log.error("Ocorreu uma falha ao tentar gerar a nota fiscal do pedido {}", entity.getId(), e);
				invoicePort.updateStatus(entity.getId(), InvoiceStatus.FAILED)
					.ifPresent(invoice -> {
						invoiceIssueEventPublisher
							.publish(InvoiceIssueEvent.builder()
									.orderId(invoice.getOrderId())
									.status(invoice.getStatus())
									.build());
					});
			}
		});
	}
}
