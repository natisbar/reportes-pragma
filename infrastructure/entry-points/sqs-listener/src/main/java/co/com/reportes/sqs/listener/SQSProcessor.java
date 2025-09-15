package co.com.reportes.sqs.listener;

import co.com.reportes.sqs.listener.mapper.PrestamoSolicitudMapper;
import co.com.reportes.sqs.listener.model.SolicitudPrestamoDto;
import co.com.reportes.usecase.GestionarConteoSolicitudesAprobadasUseCase;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.services.sqs.model.Message;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class SQSProcessor implements Function<Message, Mono<Void>> {
     private final GestionarConteoSolicitudesAprobadasUseCase gestionarConteoSolicitudesAprobadasUseCase;
     private final PrestamoSolicitudMapper mapper;
     private final ObjectMapper objectMapper;

    @Override
    public Mono<Void> apply(Message message) {
        System.out.println(message.body());
        return Mono.just(message.body())
                .map(this::convertirStringASolicitud)
                .map(mapper::convertirDesde)
                .flatMap(gestionarConteoSolicitudesAprobadasUseCase::ejecutar);
    }

    private SolicitudPrestamoDto convertirStringASolicitud(String solicitudString){
        try {
            return objectMapper.readValue(solicitudString, SolicitudPrestamoDto.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
