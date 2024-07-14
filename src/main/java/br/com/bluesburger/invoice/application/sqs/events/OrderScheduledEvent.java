package br.com.bluesburger.invoice.application.sqs.events;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.bluesburger.invoice.domain.entity.InvoiceStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@ToString(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderScheduledEvent extends OrderEvent {
	
	public static final String EVENT_NAME = "ENTREGA_AGENDADA";

	private static final long serialVersionUID = 7702500048926979660L;
	
	@JsonCreator
	public OrderScheduledEvent(@JsonProperty("orderId") String orderId, @JsonProperty("status") InvoiceStatus status) {
		super(orderId, status);
	}
	
	@Override
	public String getEventName() {
		return EVENT_NAME;
	}
}