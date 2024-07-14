package br.com.bluesburger.invoice.application.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.bluesburger.invoice.application.dto.order.InvoiceDto;
import br.com.bluesburger.invoice.application.dto.order.InvoiceMapper;
import br.com.bluesburger.invoice.application.sqs.events.InvoiceIssueEvent;
import br.com.bluesburger.invoice.domain.entity.InvoiceStatus;
import br.com.bluesburger.invoice.domain.exception.OrderNotFoundException;
import br.com.bluesburger.invoice.infra.database.InvoiceAdapter;
import br.com.bluesburger.invoice.infra.sqs.events.OrderEventPublisher;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/invoice")
@RequiredArgsConstructor
public class InvoiceController {
	
	private final InvoiceAdapter orderAdapter;
	
	private final InvoiceMapper orderMapper;
	
	private final OrderEventPublisher<InvoiceIssueEvent> invoiceIssueEventPublisher;
	
	@ApiResponses(value = { 
	  @ApiResponse(responseCode = "200", description = "List of orders", 
	    content = { @Content(mediaType = "application/json", 
	      schema = @Schema(allOf = InvoiceDto.class)) })
	})
	@GetMapping
	public List<InvoiceDto> getAll() {
		return orderAdapter.getAll().stream()
			.map(orderMapper::toOrderDto)
			.toList();
	}

	@ApiResponses(value = { 
	  @ApiResponse(responseCode = "200", description = "Invoice found with id", 
	    content = {@Content(
	    		mediaType = "application/json", 
	    		schema = @Schema(implementation = InvoiceDto.class))}),
	  @ApiResponse(responseCode = "404", description = "Invoice not found",
	  	content = { @Content(mediaType = "application/json") })
	})
	@GetMapping("/{invoiceId}")
	public InvoiceDto getById(@PathVariable Long invoiceId) {
		return orderAdapter.getById(invoiceId)
			.map(orderMapper::toOrderDto)
			.orElseThrow(OrderNotFoundException::new);
	}
	
	@ApiResponses(value = { 
	  @ApiResponse(responseCode = "200", description = "Invoice updated with id", 
	    content = {@Content(
	    		mediaType = "application/json", 
	    		schema = @Schema(implementation = InvoiceDto.class))}),
	  @ApiResponse(responseCode = "404", description = "Invoice not found",
	  	content = { @Content(mediaType = "application/json") })
	})
	@PutMapping("/{invoiceId}/{status}")
	public InvoiceDto getById(@PathVariable Long invoiceId, @PathVariable InvoiceStatus status) {
		var invoice = orderAdapter.getById(invoiceId).orElseThrow(OrderNotFoundException::new);
		
		var dto = orderAdapter.updateStatus(invoice.getId(), status)
				.map(orderMapper::toOrderDto)
				.orElseThrow(OrderNotFoundException::new);
		
		invoiceIssueEventPublisher
			.publish(InvoiceIssueEvent.builder()
					.orderId(invoice.getOrderId())
					.status(status)
					.build());
		return dto;
				
	}
}
