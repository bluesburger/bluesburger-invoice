package br.com.bluesburger.invoice.infra.sqs.events;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.com.bluesburger.invoice.application.sqs.events.InvoiceIssueEvent;
import br.com.bluesburger.invoice.domain.entity.InvoiceStatus;
import br.com.bluesburger.invoice.infra.sqs.SqsQueueSupport;
import br.com.bluesburger.invoice.support.QueueIntegrationSupport;

class SqsQueueSupportIntegrationTests extends QueueIntegrationSupport {
	private static final UUID ORDER_ID = UUID.fromString("ddedf1ab-0b2f-4766-a9fc-104bedc98492");

	@Autowired
	private SqsQueueSupport<InvoiceIssueEvent> sqsQueueSupport;
	
	@Test
	void whenCreateRequestToFifoQueue() throws JsonProcessingException {
		// given
		String queueName = "queue-name.fifo";
		var event = new InvoiceIssueEvent(ORDER_ID.toString(), InvoiceStatus.PENDING);
		String messageGroupId = ORDER_ID.toString();
		
		// when
		var request = sqsQueueSupport.createRequest(event, queueName, messageGroupId);
		
		// then
		assertThat(request).isNotNull();
	}
	
	@Test
	void whenCreateRequestToDefaultQueue() throws JsonProcessingException {
		// given
		String queueName = "queue-name";
		var event = new InvoiceIssueEvent(ORDER_ID.toString(), InvoiceStatus.PENDING);
		String messageGroupId = ORDER_ID.toString();
		
		// when
		var request = sqsQueueSupport.createRequest(event, queueName, messageGroupId);
		
		// then
		assertThat(request).isNotNull();
	}
	
	@Test
	void whenBuildQueueUrl_ThenShouldReturn() throws JsonProcessingException {
		// given
		String queueName = "queue-name.fifo";
		
		// when
		var queueUrl = sqsQueueSupport.buildQueueUrl(queueName);
		
		// then
		
		assertThat(queueUrl).isNotNull()
			.isEqualTo(String.format("http://127.0.0.1:%s/%s/%s", getPort(), "000000000000", queueName));
	}
}
