package co.com.reportes.model.solicitud;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class ReporteSolicitudes {
    private String idReporte;
    private Long contador;
    private Long monto;
}
