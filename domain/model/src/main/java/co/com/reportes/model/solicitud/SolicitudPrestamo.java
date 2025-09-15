package co.com.reportes.model.solicitud;

import co.com.reportes.model.solicitud.enums.Estado;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class SolicitudPrestamo {
    private Long id;
    private BigDecimal monto;
    private Integer plazo;
    private String correo;
    private Long estadoId;
    private Long tipoPrestamoId;
    private Estado estado;
}
