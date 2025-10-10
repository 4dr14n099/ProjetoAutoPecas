package org.example.services.exeptions;

public class ValueBigForAtributeException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public ValueBigForAtributeException(String msg) {
        super("Consultar log para obter mais detalhes");
    }
}
