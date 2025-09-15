package co.com.reportes.usecase;

import co.com.reportes.model.solicitud.SolicitudPrestamo;
import co.com.reportes.model.solicitud.enums.Estado;
import co.com.reportes.model.solicitud.gateway.SolicitudPrestamoGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GestionarConteoSolicitudesAprobadasUseCaseTest {
    @InjectMocks
    GestionarConteoSolicitudesAprobadasUseCase gestionarConteoSolicitudesAprobadasUseCase;
    @Mock
    SolicitudPrestamoGateway solicitudPrestamoGateway;

    @Test
    void ejecutar_cuandoEstadoAprobado_debeLlamarGateway() {
        // Arrange
        SolicitudPrestamo solicitud = new SolicitudPrestamo();
        solicitud.setEstado(Estado.APROBADO);
        solicitud.setMonto(BigDecimal.valueOf(1000));

        when(solicitudPrestamoGateway.agregarCantidadYMontoPrestamoAprobado(any()))
                .thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(gestionarConteoSolicitudesAprobadasUseCase.ejecutar(solicitud))
                .verifyComplete();

        verify(solicitudPrestamoGateway, times(1))
                .agregarCantidadYMontoPrestamoAprobado(BigDecimal.valueOf(1000));
    }

    @Test
    void ejecutar_cuandoEstadoNoAprobado_noDebeLlamarGateway() {
        // Arrange
        SolicitudPrestamo solicitud = new SolicitudPrestamo();
        solicitud.setEstado(Estado.RECHAZADA);
        solicitud.setMonto(BigDecimal.valueOf(1000));

        // Act & Assert
        StepVerifier.create(gestionarConteoSolicitudesAprobadasUseCase.ejecutar(solicitud))
                .verifyComplete();

        verifyNoInteractions(solicitudPrestamoGateway);
    }
}
