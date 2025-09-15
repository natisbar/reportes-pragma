package co.com.reportes.model.solicitud.enums;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum Estado {
    RECHAZADA("RECHAZADA", 2),
    APROBADO("APROBADO", 4);

    private final String valor;
    private final long id;

    Estado(String valor, long id) {
        this.valor = valor;
        this.id = id;
    }

    public static Estado obtenerPorId(long id){
        return Arrays.stream(Estado.values())
                .filter(estado -> estado.getId() == id)
                .findFirst()
                .orElse(null);
    }
}
