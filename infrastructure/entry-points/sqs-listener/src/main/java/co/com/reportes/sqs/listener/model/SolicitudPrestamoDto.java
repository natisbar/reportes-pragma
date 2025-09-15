package co.com.reportes.sqs.listener.model;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class SolicitudPrestamoDto {
    private Long id;
    private BigDecimal monto;
    private Integer plazo;
    private String correo;
    private Long estadoId;
    private Long tipoPrestamoId;
    private String estado;
}
