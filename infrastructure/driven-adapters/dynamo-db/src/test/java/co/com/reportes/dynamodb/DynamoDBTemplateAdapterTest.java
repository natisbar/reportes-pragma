package co.com.reportes.dynamodb;

import co.com.reportes.model.solicitud.ReporteSolicitudes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.reactivecommons.utils.ObjectMapper;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;
import software.amazon.awssdk.services.dynamodb.model.UpdateItemRequest;

import java.math.BigDecimal;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class DynamoDBTemplateAdapterTest {

    @Mock
    private DynamoDbEnhancedAsyncClient dynamoDbEnhancedAsyncClient;

    @Mock
    private ObjectMapper mapper;

    @Mock
    private DynamoDbAsyncClient dynamoDbAsyncClient;

    private DynamoDBTemplateAdapter adapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        adapter = new DynamoDBTemplateAdapter(dynamoDbEnhancedAsyncClient, mapper, dynamoDbAsyncClient);
    }

    @Test
    void agregarCantidadYMontoPrestamoAprobado_debeActualizarMontoYContador() {
        BigDecimal valor = BigDecimal.valueOf(1000);

        when(dynamoDbAsyncClient.updateItem(any(UpdateItemRequest.class)))
                .thenReturn(CompletableFuture.completedFuture(null));

        StepVerifier.create(adapter.agregarCantidadYMontoPrestamoAprobado(valor))
                .verifyComplete();

        ArgumentCaptor<UpdateItemRequest> captor = ArgumentCaptor.forClass(UpdateItemRequest.class);
        verify(dynamoDbAsyncClient, times(1)).updateItem(captor.capture());
        UpdateItemRequest req = captor.getValue();
        assertEquals(DynamoDBTemplateAdapter.NOMBRE_TABLA, req.tableName());
        assertEquals("total_solicitudes_aprobadas", req.key().get("id").s());

        assertEquals(valor.toPlainString(), req.expressionAttributeValues().get(":val").n());
    }

    @Test
    void obtenerReporteAprobadosPorId_delegaEnGetById() {
        String id = "id123";
        ReporteSolicitudes reporte = new ReporteSolicitudes();
        reporte.setId(id);

        DynamoDBTemplateAdapter spyAdapter = spy(adapter);
        doReturn(Mono.just(reporte)).when(spyAdapter).getById(id);

        StepVerifier.create(spyAdapter.obtenerReporteAprobadosPorId(id))
                .expectNext(reporte)
                .verifyComplete();

        verify(spyAdapter, times(1)).getById(id);
    }
}
