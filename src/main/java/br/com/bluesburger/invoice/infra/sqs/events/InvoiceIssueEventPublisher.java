package br.com.bluesburger.invoice.infra.sqs.events;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.bluesburger.invoice.application.sqs.events.InvoiceIssueEvent;
import lombok.ToString;

@ToString(callSuper = true)
@Service
public class InvoiceIssueEventPublisher extends OrderEventPublisher<InvoiceIssueEvent> {

	public InvoiceIssueEventPublisher(@Value("${queue.invoice-issued-event:invoice-issued-event.fifo}") String queueName) {
		super(queueName);
	}

}
