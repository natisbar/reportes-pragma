package co.com.reportes.dynamodb.helper;

import co.com.reportes.dynamodb.DynamoDBTemplateAdapter;
import co.com.reportes.dynamodb.ReporteSolicitudesEntity;
import co.com.reportes.model.solicitud.ReporteSolicitudes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.reactivecommons.utils.ObjectMapper;
import reactor.test.StepVerifier;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbAsyncTable;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.math.BigDecimal;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;


class TemplateAdapterOperationsTest {

    @Mock
    private DynamoDbEnhancedAsyncClient dynamoDbEnhancedAsyncClient;

    @Mock
    private ObjectMapper mapper;

    @Mock
    private DynamoDbAsyncTable<ReporteSolicitudesEntity> customerTable;

    @Mock
    private DynamoDbAsyncClient dynamoDbAsyncClient;

    private ReporteSolicitudesEntity modelEntity;
    private ReporteSolicitudes model;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        when(dynamoDbEnhancedAsyncClient.table(anyString(), eq(TableSchema.fromBean(ReporteSolicitudesEntity.class))))
                .thenReturn(customerTable);

        modelEntity = new ReporteSolicitudesEntity();
        modelEntity.setId("id");
        modelEntity.setContador(1L);
        modelEntity.setMonto(BigDecimal.TEN);
        model = new ReporteSolicitudes();
        model.setId("id");
        model.setContador(1L);
        model.setMonto(BigDecimal.TEN);
    }

    @Test
    void modelEntityPropertiesMustNotBeNull() {
        ReporteSolicitudesEntity modelEntityUnderTest = new ReporteSolicitudesEntity("id", 1L, BigDecimal.TEN);

        assertNotNull(modelEntityUnderTest.getId());
        assertNotNull(modelEntityUnderTest.getContador());
        assertNotNull(modelEntityUnderTest.getMonto());
    }

    @Test
    void testSave() {
        when(customerTable.putItem(any(ReporteSolicitudesEntity.class)))
                .thenReturn(CompletableFuture.completedFuture(null));

        when(mapper.map(any(ReporteSolicitudes.class), eq(ReporteSolicitudesEntity.class))).thenReturn(modelEntity);

        DynamoDBTemplateAdapter dynamoDBTemplateAdapter =
                new DynamoDBTemplateAdapter(dynamoDbEnhancedAsyncClient, mapper, dynamoDbAsyncClient);

        StepVerifier.create(dynamoDBTemplateAdapter.save(model))
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    void testGetById() {
        String id = "id";

        when(customerTable.getItem(
                Key.builder().partitionValue(AttributeValue.builder().s(id).build()).build()))
                .thenReturn(CompletableFuture.completedFuture(modelEntity));
        when(mapper.map(any(ReporteSolicitudesEntity.class), eq(ReporteSolicitudes.class))).thenReturn(model);

        DynamoDBTemplateAdapter dynamoDBTemplateAdapter =
                new DynamoDBTemplateAdapter(dynamoDbEnhancedAsyncClient, mapper, dynamoDbAsyncClient);

        StepVerifier.create(dynamoDBTemplateAdapter.getById("id"))
                .expectNext(model)
                .verifyComplete();
    }

    @Test
    void testDelete() {
        when(mapper.map(any(ReporteSolicitudes.class), eq(ReporteSolicitudesEntity.class))).thenReturn(modelEntity);
        when(mapper.map(any(ReporteSolicitudesEntity.class), eq(ReporteSolicitudes.class))).thenReturn(model);

        when(customerTable.deleteItem(modelEntity))
                .thenReturn(CompletableFuture.completedFuture(modelEntity));

        DynamoDBTemplateAdapter dynamoDBTemplateAdapter =
                new DynamoDBTemplateAdapter(dynamoDbEnhancedAsyncClient, mapper, dynamoDbAsyncClient);

        StepVerifier.create(dynamoDBTemplateAdapter.delete(model))
                .expectNext(model)
                .verifyComplete();
    }
}