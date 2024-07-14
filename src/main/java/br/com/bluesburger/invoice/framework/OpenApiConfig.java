package br.com.bluesburger.invoice.framework;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
		info = @Info(
				title = "Bluesburguer Invoice",
				version = "v2.0"
		),
		servers = @Server(url = "http://localhost:${server.port}")
)
public class OpenApiConfig {

}
