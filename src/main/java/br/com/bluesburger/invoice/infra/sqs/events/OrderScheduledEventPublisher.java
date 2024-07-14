package br.com.bluesburger.invoice.infra.sqs.events;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.bluesburger.invoice.application.sqs.events.OrderScheduledEvent;
import lombok.ToString;

@ToString(callSuper = true)
@Service
public class OrderScheduledEventPublisher extends OrderEventPublisher<OrderScheduledEvent> {

	public OrderScheduledEventPublisher(@Value("${queue.order.scheduled-event:order-scheduled-event.fifo}") String queueName) {
		super(queueName);
	}

}
