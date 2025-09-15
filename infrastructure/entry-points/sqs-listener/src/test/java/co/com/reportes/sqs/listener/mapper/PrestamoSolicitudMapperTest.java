package co.com.reportes.sqs.listener.mapper;

import co.com.reportes.model.solicitud.SolicitudPrestamo;
import co.com.reportes.sqs.listener.model.SolicitudPrestamoDto;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class PrestamoSolicitudMapperTest {

    private PrestamoSolicitudMapper mapper = new PrestamoSolicitudMapper();

    @Test
    void debeConvertirDtoCorrectamente() {
        SolicitudPrestamoDto dto = new SolicitudPrestamoDto();
        dto.setMonto(BigDecimal.valueOf(1000));
        dto.setEstadoId(4L);
        dto.setEstado("APROBADO");

        SolicitudPrestamo result = mapper.convertirDesde(dto);

        assertNotNull(result);
        assertEquals(BigDecimal.valueOf(1000), result.getMonto());
        assertEquals(4L, result.getEstadoId());
        assertEquals("APROBADO", result.getEstado().getValor());
    }

    @Test
    void debeRetornarNullCuandoDtoEsNull() {
        SolicitudPrestamo result = mapper.convertirDesde(null);
        assertNull(result);
    }

}
