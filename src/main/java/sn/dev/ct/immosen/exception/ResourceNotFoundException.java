package sn.dev.ct.immosen.exception;

public class ResourceNotFoundException  extends RuntimeException{
    public ResourceNotFoundException(String entity, Object id) {
        super("Resource " + entity + " with id " + id + " not found");
    }
}
