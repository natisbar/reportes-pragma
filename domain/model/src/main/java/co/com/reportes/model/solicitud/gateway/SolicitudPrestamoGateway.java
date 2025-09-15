package co.com.reportes.model.solicitud.gateway;

import reactor.core.publisher.Mono;

import java.math.BigDecimal;

public interface SolicitudPrestamoGateway {
    Mono<Void> agregarCantidadYMontoPrestamoAprobado(BigDecimal valor);
}
