package co.com.reportes.usecase;

import co.com.reportes.model.solicitud.ReporteSolicitudes;
import co.com.reportes.model.solicitud.gateway.SolicitudPrestamoGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@ExtendWith(MockitoExtension.class)
class ObtenerReporteSolicitudesAprobadasUseCaseTest {
    @InjectMocks
    ObtenerReporteSolicitudesAprobadasUseCase obtenerReporteSolicitudesAprobadasUseCase;
    @Mock
    SolicitudPrestamoGateway solicitudPrestamoGateway;

    @Test
    void debeObtenerElReporte() {
        ReporteSolicitudes reporteSolicitudes = ReporteSolicitudes.builder().monto(BigDecimal.valueOf(1000000)).contador(1L).build();

        when(solicitudPrestamoGateway.obtenerReporteAprobadosPorId(anyString())).thenReturn(Mono.just(reporteSolicitudes));

        Mono<ReporteSolicitudes> result = obtenerReporteSolicitudesAprobadasUseCase.ejecutar();

        StepVerifier.create(result)
                .expectNextMatches(respuesta -> {
                    assertAll(
                            () -> assertEquals("no coincide", reporteSolicitudes.getContador(), respuesta.getContador()),
                            () -> assertEquals("no coincide",reporteSolicitudes.getMonto().compareTo(respuesta.getMonto()), 0)
                    );
                    return true;
                })
                .verifyComplete();
    }
}
