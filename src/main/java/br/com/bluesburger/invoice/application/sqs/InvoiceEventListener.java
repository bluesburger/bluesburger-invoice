package br.com.bluesburger.invoice.application.sqs;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.aws.messaging.listener.Acknowledgment;
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import br.com.bluesburger.invoice.application.dto.order.OrderRequest;
import br.com.bluesburger.invoice.application.sqs.commands.CancelInvoiceCommand;
import br.com.bluesburger.invoice.application.sqs.commands.IssueInvoiceCommand;
import br.com.bluesburger.invoice.application.sqs.events.InvoiceIssueEvent;
import br.com.bluesburger.invoice.domain.entity.InvoiceStatus;
import br.com.bluesburger.invoice.domain.service.InvoicePort;
import br.com.bluesburger.invoice.infra.sqs.events.IOrderEventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@ConditionalOnProperty(name = "cloud.aws.sqs.listener.auto-startup", havingValue = "true")
public class InvoiceEventListener {
	
	private final InvoicePort orderPort;
	private final IOrderEventPublisher<InvoiceIssueEvent> orderScheduledEventPublisher;
	
	@SqsListener(value = "${queue.issue-invoice-command:queue-invoice-command.fifo}", deletionPolicy = SqsMessageDeletionPolicy.NEVER)
	public void handle(@Payload IssueInvoiceCommand command, Acknowledgment ack) {
		log.info("Command received: {}", command.toString());
		
		var event = orderPort.createNewInvoice(new OrderRequest(command.getOrderId()))
				.map(invoice -> new InvoiceIssueEvent(command.getOrderId(), InvoiceStatus.PENDING))
				.orElse(new InvoiceIssueEvent(command.getOrderId(), InvoiceStatus.FAILED));
		
		orderScheduledEventPublisher.publish(event)
			.map(id -> ack.acknowledge());
	}

	@SqsListener(value = "${queue.cancel-invoice-command:queue-cancel-invoice-command.fifo}", deletionPolicy = SqsMessageDeletionPolicy.NEVER)
	public void handle(@Payload CancelInvoiceCommand command, Acknowledgment ack) {
		log.info("Command received: {}", command.toString());
		orderPort.updateStatusByOrderId(command.getOrderId(), InvoiceStatus.CANCELED)
			.ifPresent(invoice -> ack.acknowledge());
	}
}
