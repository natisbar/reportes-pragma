package co.com.reportes.sqs.listener.mapper;

import co.com.reportes.model.solicitud.SolicitudPrestamo;
import co.com.reportes.model.solicitud.enums.Estado;
import co.com.reportes.sqs.listener.model.SolicitudPrestamoDto;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PrestamoSolicitudMapper {
    public SolicitudPrestamo convertirDesde(SolicitudPrestamoDto solicitudPrestamoDto) {
        return Optional.ofNullable(solicitudPrestamoDto)
                .map(dto -> SolicitudPrestamo.builder()
                        .id(dto.getId())
                        .plazo(dto.getPlazo())
                        .monto(dto.getMonto())
                        .correo(dto.getCorreo())
                        .tipoPrestamoId(dto.getTipoPrestamoId())
                        .estado(Estado.obtenerPorId(dto.getEstadoId()))
                        .estadoId(dto.getEstadoId())
                        .build())
                .orElse(null);
    }

}
