package co.com.reportes.model.solicitud.enums;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class EstadoTest {

    @Test
    void debeObtenerEstadoPorId() {
        assertEquals(Estado.RECHAZADA, Estado.obtenerPorId(2));
        assertEquals(Estado.APROBADO, Estado.obtenerPorId(4));
        assertNull(Estado.obtenerPorId(99)); // no existe
    }
}
