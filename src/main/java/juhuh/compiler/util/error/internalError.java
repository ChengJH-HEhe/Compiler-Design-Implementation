package juhuh.compiler.util.error;
import juhuh.compiler.util.position;

public class internalError extends error {

    public internalError(String msg, position pos) {
        super("Internal Error");//:" + msg, pos);
    }

}
