package br.com.bluesburger.invoice.infra.sqs.events;

import java.util.Optional;

import br.com.bluesburger.invoice.application.sqs.events.OrderEvent;

public interface IOrderEventPublisher<T extends OrderEvent> {

	Optional<String> publish(T event);
}
