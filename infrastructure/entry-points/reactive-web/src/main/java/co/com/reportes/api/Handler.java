package co.com.reportes.api;

import co.com.reportes.model.solicitud.SolicitudPrestamo;
import co.com.reportes.model.solicitud.enums.Estado;
import co.com.reportes.usecase.GestionarConteoSolicitudesAprobadasUseCase;
import co.com.reportes.usecase.ObtenerReporteSolicitudesAprobadasUseCase;
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
    private final ObtenerReporteSolicitudesAprobadasUseCase obtenerReporteSolicitudesAprobadasUseCase;

    public Mono<ServerResponse> listenGETUseCase(ServerRequest serverRequest) {
        // useCase.logic();
        return gestionarConteoSolicitudesAprobadasUseCase.ejecutar(SolicitudPrestamo.builder()
                .monto(BigDecimal.valueOf(1000000)).estado(Estado.APROBADO).build())
                .then(ServerResponse.ok().bodyValue(""));
    }

    public Mono<ServerResponse> listenGETUseCase2(ServerRequest serverRequest) {
        // useCase.logic();
        return obtenerReporteSolicitudesAprobadasUseCase.ejecutar()
                .flatMap(reporte -> ServerResponse.ok().bodyValue(reporte));
    }
}
