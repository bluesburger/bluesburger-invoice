package br.com.bluesburger.invoice;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import br.com.bluesburger.invoice.support.ApplicationIntegrationSupport;

class BluesBurguerOrderApplicationIntegrationTests extends ApplicationIntegrationSupport {

	@Test
	void contextLoad() {
		assertThat(super.hashCode()).isNotZero();
	}
}
