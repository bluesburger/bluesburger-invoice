package br.com.bluesburger.invoice.application.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

import br.com.bluesburger.invoice.domain.entity.InvoiceStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
@ToString
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class InvoiceDto {

	private Long id;
	
	private InvoiceStatus status;
	
	private String orderId;
}
