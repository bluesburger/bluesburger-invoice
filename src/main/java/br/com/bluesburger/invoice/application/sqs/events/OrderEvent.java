package br.com.bluesburger.invoice.application.sqs.events;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.bluesburger.invoice.domain.entity.InvoiceStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@ToString
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public abstract class OrderEvent implements Serializable {
	
	private static final long serialVersionUID = -3527498526085380774L;
	
	@NonNull
	@JsonProperty
	protected String orderId;
	@NonNull
	@JsonProperty
	protected InvoiceStatus status;
	
	@JsonCreator
	protected OrderEvent(@JsonProperty("orderId") String orderId, @JsonProperty("status") InvoiceStatus status) {
		Objects.requireNonNull(orderId);
		Objects.requireNonNull(status);
		this.orderId = orderId;
		this.status = status;
	}
	
	public abstract String getEventName();
}