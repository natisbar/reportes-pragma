package co.com.reportes.api;

import co.com.reportes.model.solicitud.SolicitudPrestamo;
import co.com.reportes.usecase.GestionarConteoSolicitudesAprobadasUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class Handler {
    private final GestionarConteoSolicitudesAprobadasUseCase gestionarConteoSolicitudesAprobadasUseCase;

    public Mono<ServerResponse> listenGETUseCase(ServerRequest serverRequest) {
        // useCase.logic();
        return gestionarConteoSolicitudesAprobadasUseCase.ejecutar(SolicitudPrestamo.builder()
                .monto(BigDecimal.valueOf(1000000)).build())
                .then(ServerResponse.ok().bodyValue(""));
    }

    public Mono<ServerResponse> listenGETOtherUseCase(ServerRequest serverRequest) {
        // useCase2.logic();
        return ServerResponse.ok().bodyValue("");
    }

    public Mono<ServerResponse> listenPOSTUseCase(ServerRequest serverRequest) {
        // useCase.logic();
        return ServerResponse.ok().bodyValue("");
    }
}
