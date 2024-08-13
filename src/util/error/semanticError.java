package util.error;

public class semanticError extends error {
    public semanticError(String msg) {
        super("Semantic Error: " + msg);
    }

}
