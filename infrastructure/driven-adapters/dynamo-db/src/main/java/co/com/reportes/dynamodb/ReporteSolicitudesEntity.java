package co.com.reportes.dynamodb;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.*;

import java.math.BigDecimal;


@DynamoDbBean
public class ReporteSolicitudesEntity {

    private String id;
    private Long contador;
    private BigDecimal monto;

    public ReporteSolicitudesEntity() {
    }

    public ReporteSolicitudesEntity(String id, Long contador, BigDecimal monto) {
        this.id = id;
        this.contador = contador;
        this.monto = monto;
    }

    @DynamoDbPartitionKey
    @DynamoDbAttribute("id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @DynamoDbAttribute("contador")
    public Long getContador() {
        return contador;
    }

    public void setContador(Long contador) {
        this.contador = contador;
    }

    @DynamoDbAttribute("monto")
    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }
}
