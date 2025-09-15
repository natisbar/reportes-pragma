package co.com.reportes.api.excepcion;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Error {
    protected static final String MENSAJE_ERROR_DEFECTO = "Ocurrió un error inesperado, por favor comuniquese comuniquese con el administrador";
    protected static final int ERROR_DEFECTO = 500;

    private int estado;
    private String mensaje;

    public Error(){
        this(ERROR_DEFECTO, MENSAJE_ERROR_DEFECTO);
    }

    public Error(int estado, String mensaje){
        this.estado = estado;
        this.mensaje = mensaje;
    }
}
