package co.com.reportes.api;

import co.com.reportes.api.dto.ReporteSolicitudesDto;
import co.com.reportes.usecase.ObtenerReporteSolicitudesAprobadasUseCase;
import lombok.RequiredArgsConstructor;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class Handler {
    private final ObtenerReporteSolicitudesAprobadasUseCase obtenerReporteSolicitudesAprobadasUseCase;
    private final ObjectMapper objectMapper;

    public Mono<ServerResponse> listenGETUseCase(ServerRequest serverRequest) {
        return obtenerReporteSolicitudesAprobadasUseCase.ejecutar()
                .map(reporte -> objectMapper.map(reporte, ReporteSolicitudesDto.class))
                .flatMap(reporteDto -> ServerResponse.ok().bodyValue(reporteDto));
    }
}
