package com.example.demo;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.example.demo.application.usecases.ConsultarUsuarioUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import java.util.Map;
import java.util.function.Function;

@SpringBootApplication(
		scanBasePackages = {
				"com.example.demo",
				"org.example.aws"
		},
		exclude = DataSourceAutoConfiguration.class
)
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean("consultarUsuarioFunction")
	public Function<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent>
	consultarUsuarioFunction(ConsultarUsuarioUseCase useCase,
							 ObjectMapper objectMapper) {
		return request -> {
			try {
				String id = objectMapper.readTree(request.getBody())
						.get("id").asText();
				var u = useCase.consultarPorId(id);
				return new APIGatewayProxyResponseEvent()
						.withStatusCode(200)
						.withHeaders(Map.of("Content-Type", "application/json"))
						.withBody(objectMapper.writeValueAsString(u));
			}
			catch (Exception e) {
				return new APIGatewayProxyResponseEvent()
						.withStatusCode(500)
						.withBody("{\"error\":\""+e.getMessage()+"\"}");
			}
		};
	}

}
