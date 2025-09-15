package co.com.reportes.dynamodb;

import co.com.reportes.dynamodb.helper.TemplateAdapterOperations;
import co.com.reportes.model.solicitud.ReporteSolicitudes;
import co.com.reportes.model.solicitud.gateway.SolicitudPrestamoGateway;
import org.reactivecommons.utils.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryEnhancedRequest;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.UpdateItemRequest;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Repository
public class DynamoDBTemplateAdapter extends TemplateAdapterOperations<ReporteSolicitudes, String, ReporteSolicitudesEntity> implements SolicitudPrestamoGateway {

    private final DynamoDbAsyncClient dynamoDbAsyncClient;
    private static final Logger log = LoggerFactory.getLogger(DynamoDBTemplateAdapter.class);
    private static final String NOMBRE_TABLA = "reportes_solicitudes";

    public DynamoDBTemplateAdapter(DynamoDbEnhancedAsyncClient connectionFactory, ObjectMapper mapper, DynamoDbAsyncClient dynamoDbAsyncClient) {
        super(connectionFactory, mapper, d -> mapper.map(d, ReporteSolicitudes.class), NOMBRE_TABLA);
        this.dynamoDbAsyncClient = dynamoDbAsyncClient;
    }

    public Mono<List<ReporteSolicitudes>> getEntityBySomeKeys(String partitionKey, String sortKey) {
        QueryEnhancedRequest queryExpression = generateQueryExpression(partitionKey, sortKey);
        return query(queryExpression);
    }

    public Mono<List<ReporteSolicitudes>> getEntityBySomeKeysByIndex(String partitionKey, String sortKey) {
        QueryEnhancedRequest queryExpression = generateQueryExpression(partitionKey, sortKey);
        return queryByIndex(queryExpression);
    }

    private QueryEnhancedRequest generateQueryExpression(String partitionKey, String sortKey) {
        return QueryEnhancedRequest.builder()
                .queryConditional(QueryConditional.keyEqualTo(Key.builder().partitionValue(partitionKey).build()))
                .queryConditional(QueryConditional.sortGreaterThanOrEqualTo(Key.builder().sortValue(sortKey).build()))
                .build();
    }

    @Override
    public Mono<Void> agregarCantidadYMontoPrestamoAprobado(BigDecimal valor) {
      return agregarPrestamoYMonto(valor)
              .doOnSuccess(attributes -> log.info("Cantidad y monto actualizado"));
    }

    @Override
    public Mono<ReporteSolicitudes> obtenerReporteAprobadosPorId(String id) {
        return this.getById(id);
    }

    private Mono<Void> agregarPrestamoYMonto(BigDecimal valor) {
        Map<String, AttributeValue> expressionValues = new HashMap<>();
        expressionValues.put(":zero", AttributeValue.builder().n("0").build());
        expressionValues.put(":inc", AttributeValue.builder().n("1").build());
        expressionValues.put(":val", AttributeValue.builder().n(valor.toPlainString()).build());

        UpdateItemRequest request = UpdateItemRequest.builder()
                .tableName(NOMBRE_TABLA)
                .key(Map.of("id", AttributeValue.builder().s("total_solicitudes_aprobadas").build()))
                .updateExpression("SET contador = if_not_exists(contador, :zero) + :inc, " +
                        "monto = if_not_exists(monto, :zero) + :val")
                .expressionAttributeValues(expressionValues)
                .build();

        return Mono.fromFuture(() -> dynamoDbAsyncClient.updateItem(request))
                .then();
    }
}
