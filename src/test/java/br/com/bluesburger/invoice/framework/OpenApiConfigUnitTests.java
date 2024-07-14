package br.com.bluesburger.invoice.framework;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.bluesburger.invoice.framework.OpenApiConfig;

@ExtendWith(MockitoExtension.class)
class OpenApiConfigUnitTests {

	@Test
	void shouldInstance() {
		assertThat(new OpenApiConfig()).isNotNull();
	}
}