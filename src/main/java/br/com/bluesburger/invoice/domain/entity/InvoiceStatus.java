package br.com.bluesburger.invoice.domain.entity;

public enum InvoiceStatus {
	PENDING,
	EMITTING,
	ISSUED,
	CANCELED,
	FAILED;
}
