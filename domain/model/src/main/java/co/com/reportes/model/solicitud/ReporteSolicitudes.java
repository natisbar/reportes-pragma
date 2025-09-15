package co.com.reportes.model.solicitud;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class ReporteSolicitudes {
    private String id;
    private Long contador;
    private BigDecimal monto;
}
