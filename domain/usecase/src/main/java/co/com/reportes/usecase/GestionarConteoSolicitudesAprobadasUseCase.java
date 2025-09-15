package co.com.reportes.usecase;

import co.com.reportes.model.solicitud.SolicitudPrestamo;
import co.com.reportes.model.solicitud.enums.Estado;
import co.com.reportes.model.solicitud.gateway.SolicitudPrestamoGateway;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

@AllArgsConstructor
public class GestionarConteoSolicitudesAprobadasUseCase {

    private final SolicitudPrestamoGateway solicitudPrestamoGateway;

    public Mono<Void> ejecutar(SolicitudPrestamo solicitudPrestamo){
        return Mono.just(solicitudPrestamo)
                .filter(solicitud -> Estado.APROBADO.equals(solicitudPrestamo.getEstado()))
                .flatMap(solicitud -> solicitudPrestamoGateway.agregarCantidadYMontoPrestamoAprobado(solicitudPrestamo.getMonto()))
                .then();
    }
}
