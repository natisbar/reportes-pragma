package co.com.reportes.model.solicitud;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class ReporteSolicitudes {
    private String id;
    private Long contador;
    private Long monto;
}
