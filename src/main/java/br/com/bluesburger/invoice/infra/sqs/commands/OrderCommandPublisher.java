package br.com.bluesburger.invoice.infra.sqs.commands;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.amazonaws.services.sqs.AmazonSQS;
import com.fasterxml.jackson.core.JsonProcessingException;

import br.com.bluesburger.invoice.application.sqs.commands.OrderCommand;
import br.com.bluesburger.invoice.infra.sqs.SqsQueueSupport;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public abstract class OrderCommandPublisher<T extends OrderCommand> implements IOrderCommandPublisher<T> {

	private final String queueName;
	
	private final AmazonSQS amazonSQS;
	
	private final SqsQueueSupport<T> sqsQueueSupport;

    @Override
    public Optional<String> publish(T command) {
        try {
        	var request = sqsQueueSupport.createRequest(command, queueName, "order-commands");
        	log.info("Publishing command {} in SQS queue {}", command, request.getQueueUrl());
            var result = amazonSQS.sendMessage(request);
            return Optional.ofNullable(result.getMessageId());
        } catch (JsonProcessingException e) {
        	log.error("JsonProcessingException e : {} and stacktrace : {}", e.getMessage(), e);
        } catch (Exception e) {
        	log.error("Exception ocurred while pushing event to sqs : {} and stacktrace ; {}", e.getMessage(), e);
        }
        return Optional.empty();
    }
}
