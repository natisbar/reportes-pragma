package co.com.reportes.api;

import co.com.reportes.api.dto.ReporteSolicitudesDto;
import co.com.reportes.api.excepcion.ManejadorGlobalErrores;
import co.com.reportes.api.seguridad.TestSecurityConfig;
import co.com.reportes.api.seguridad.config.SecurityHeadersConfig;
import co.com.reportes.model.solicitud.ReporteSolicitudes;
import co.com.reportes.model.solicitud.common.ex.NegocioException;
import co.com.reportes.usecase.ObtenerReporteSolicitudesAprobadasUseCase;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {RouterRest.class, Handler.class, ObtenerReporteSolicitudesAprobadasUseCase.class,
        ManejadorGlobalErrores.class, SecurityHeadersConfig.class, ObjectMapper.class})
@WebFluxTest
@Import(TestSecurityConfig.class)
class RouterRestTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockitoBean
    private ObtenerReporteSolicitudesAprobadasUseCase obtenerReporteSolicitudesAprobadasUseCase;

    @MockitoBean
    private ObjectMapper objectMapper;

    @Test
    void testListenGETUseCase() {
        ReporteSolicitudes reporteSolicitudes = new ReporteSolicitudes().toBuilder()
                .monto(BigDecimal.valueOf(1000000))
                .contador(3L)
                .build();

        ReporteSolicitudesDto dto = new ReporteSolicitudesDto().toBuilder()
                .monto(BigDecimal.valueOf(1000000))
                .contador(3L)
                .build();

        when(obtenerReporteSolicitudesAprobadasUseCase.ejecutar()).thenReturn(Mono.just(reporteSolicitudes));
        when(objectMapper.map(any(ReporteSolicitudes.class), eq(ReporteSolicitudesDto.class))).thenReturn(dto);

        webTestClient.get()
                .uri("/v1/reportes")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-Security-Policy",
                        "default-src 'self'; frame-ancestors 'self'; form-action 'self'")
                .expectHeader().valueEquals("Strict-Transport-Security", "max-age=31536000;")
                .expectHeader().valueEquals("X-Content-Type-Options", "nosniff")
                .expectHeader().valueEquals("Server", "")
                .expectHeader().valueEquals("Cache-Control", "no-store")
                .expectHeader().valueEquals("Pragma", "no-cache")
                .expectHeader().valueEquals("Referrer-Policy", "strict-origin-when-cross-origin")
                .expectBody(ReporteSolicitudesDto.class)
                .value(reporteDto -> {
                            Assertions.assertThat(reporteDto.getContador()).isEqualTo(dto.getContador());
                            Assertions.assertThat(reporteDto.getMonto().compareTo(dto.getMonto())).isZero();
                        }
                );
    }

    @Test
    void testListenGETUseCase_conError400() {

        when(obtenerReporteSolicitudesAprobadasUseCase.ejecutar()).thenReturn(Mono.error(new NegocioException("error negocio")));

        webTestClient.get()
                .uri("/v1/reportes")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody()
                .jsonPath("$.estado").isEqualTo(400)
                .jsonPath("$.mensaje").isEqualTo("error negocio");
    }

    @Test
    void testListenGETUseCase_conError500() {

        when(obtenerReporteSolicitudesAprobadasUseCase.ejecutar()).thenReturn(Mono.error(new RuntimeException("error")));

        webTestClient.get()
                .uri("/v1/reportes")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().is5xxServerError()
                .expectBody()
                .jsonPath("$.estado").isEqualTo(500)
                .jsonPath("$.mensaje").isEqualTo("Ocurrió un error inesperado, por favor comuniquese comuniquese con el administrador");
    }
}
