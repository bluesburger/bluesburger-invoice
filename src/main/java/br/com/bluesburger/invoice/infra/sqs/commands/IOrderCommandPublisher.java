package br.com.bluesburger.invoice.infra.sqs.commands;

import java.util.Optional;

import br.com.bluesburger.invoice.application.sqs.commands.OrderCommand;

public interface IOrderCommandPublisher<T extends OrderCommand> {

	Optional<String> publish(T command);
}
