package co.com.sofka.model.ex;
import java.util.function.Supplier;

public class BusinessException extends ApplicationException{

    private static final long serialVersionUID = 1L;

    public enum Type {
        ERROR_BASE_DATOS("ERROR EN LA BASE DE DATOS"),
        ERROR_EL_PRODUCTO_NO_EXISTES("ESTE PRODUCTO NO SE ENCUENTRA REGISTRADO EN LA BD"),
        ERROR_EN_LA_CANTIDAD("POR FAVOR REVISA LAS CANTIDADES MINIMAS Y MAXIMAS DE PRODUCTO"),
        NO_HAY_EXISTENCIAS("LO SENTIMOS NO CONTAMOS CON ESE NUMERO DE EXISTENCIAS EN BODEGA"),
        NO_HAY_COMPRAS_POR_EL_USUARIO("LO SENTIMOS ESTE USUARIO NO A REALIZADO COMPRAS HASTA EL MOMENTO");


        private final String message;

        public String getMessage() {
            return message;
        }

        public BusinessException build() {
            return new BusinessException(this);
        }

        public Supplier<Throwable> defer() {
            return () -> new BusinessException(this);
        }

        Type(String message) {
            this.message = message;
        }

    }

    private final Type type;

    public BusinessException(Type type) {
        super(type.message);
        this.type = type;
    }

    public BusinessException(Type type, String message) {
        super(message);
        this.type = type;
    }

    @Override
    public String getCode() {
        return type.name();
    }

}
