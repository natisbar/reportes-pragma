package co.com.reportes.usecase;

import co.com.reportes.model.solicitud.ReporteSolicitudes;
import co.com.reportes.model.solicitud.gateway.SolicitudPrestamoGateway;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

@AllArgsConstructor
public class ObtenerReporteSolicitudesAprobadasUseCase {

    private final SolicitudPrestamoGateway solicitudPrestamoGateway;
    private static final String ID_REPORTE = "total_solicitudes_aprobadas";

    public Mono<ReporteSolicitudes> ejecutar(){
        return solicitudPrestamoGateway.obtenerReporteAprobadosPorId(ID_REPORTE);
    }
}
