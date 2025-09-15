package co.com.reportes.api.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class ReporteSolicitudesDto {
    private String id;
    private Long contador;
    private Long monto;
}
