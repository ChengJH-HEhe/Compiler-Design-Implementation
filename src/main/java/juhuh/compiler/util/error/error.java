package juhuh.compiler.util.error;
import juhuh.compiler.util.position;

public class error extends RuntimeException {
    private String msg;

    public error(String msg) {
        this.msg = msg;
    }
    public error(String msg, position pos) {
        this.msg = msg;
    }

    public String toString() {
        return msg;
        //+ ": " + (pos == null ? "":pos.toString());
    }
}
