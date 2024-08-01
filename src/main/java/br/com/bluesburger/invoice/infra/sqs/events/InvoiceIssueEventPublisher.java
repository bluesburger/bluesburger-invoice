package br.com.bluesburger.invoice.infra.sqs.events;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.services.sqs.AmazonSQS;

import br.com.bluesburger.invoice.application.sqs.events.InvoiceIssueEvent;
import br.com.bluesburger.invoice.infra.sqs.SqsQueueSupport;
import lombok.ToString;

@ToString(callSuper = true)
@Service
public class InvoiceIssueEventPublisher extends OrderEventPublisher<InvoiceIssueEvent> {

	public InvoiceIssueEventPublisher(@Value("${queue.invoice-issued-event:invoice-issued-event.fifo}") String queueName,
			AmazonSQS amazonSQS, SqsQueueSupport<InvoiceIssueEvent> sqsQueueSupport) {
		super(queueName, amazonSQS, sqsQueueSupport);
	}

}
