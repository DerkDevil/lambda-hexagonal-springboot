package com.example.demo.application.handlers;

import com.example.demo.application.usecases.ConsultarUsuarioUseCase;
import com.example.demo.domain.model.Usuario;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.function.Function;

@Component("consultarUsuarioFunction")
@RequiredArgsConstructor
public class ConsultarUsuarioFunction
        implements Function<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private final ConsultarUsuarioUseCase useCase;
    private final ObjectMapper objectMapper;

    @Override
    public APIGatewayProxyResponseEvent apply(APIGatewayProxyRequestEvent input) {
        try {
            String id = objectMapper.readTree(input.getBody()).get("id").asText();
            Usuario u = useCase.consultarPorId(id);

            return new APIGatewayProxyResponseEvent()
                    .withStatusCode(200)
                    .withHeaders(Map.of("Content-Type", "application/json"))
                    .withBody(objectMapper.writeValueAsString(u));

        } catch (Exception e) {
            return new APIGatewayProxyResponseEvent()
                    .withStatusCode(500)
                    .withBody("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }
}
