package co.com.reportes.model.solicitud.gateway;

import co.com.reportes.model.solicitud.ReporteSolicitudes;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

public interface SolicitudPrestamoGateway {
    Mono<Void> agregarCantidadYMontoPrestamoAprobado(BigDecimal valor);
    Mono<ReporteSolicitudes> obtenerReporteAprobadosPorId(String id);
}
