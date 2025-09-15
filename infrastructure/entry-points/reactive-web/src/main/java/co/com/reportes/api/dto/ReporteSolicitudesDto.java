package co.com.reportes.api.dto;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class ReporteSolicitudesDto {
    private String id;
    private Long contador;
    private BigDecimal monto;
}
