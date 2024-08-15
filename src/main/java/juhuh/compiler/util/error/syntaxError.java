package juhuh.compiler.util.error;
import juhuh.compiler.util.position;

public class syntaxError extends error {
    public syntaxError(String msg, position pos) {
        super("Invalid Identifier");
    }
}
