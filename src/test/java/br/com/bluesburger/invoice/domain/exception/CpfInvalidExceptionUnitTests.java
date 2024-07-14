package br.com.bluesburger.invoice.domain.exception;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CpfInvalidExceptionUnitTests {

	@Test
	void shouldInstance() {
		assertThat(new CpfInvalidException()).isNotNull();
	}
	
	@Test
	void shouldInstanceWithAnotherException() {
		var ex = new Exception("Exceção XYZ");
		assertThat(new CpfInvalidException(ex)).isNotNull();
	}
}
