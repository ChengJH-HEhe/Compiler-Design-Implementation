package util.error;
import util.position;

public class error extends RuntimeException {
    private position pos;
    private String msg;

    public error(String msg) {
        this.msg = msg;
        this.pos = null;
    }
    public error(String msg, position pos) {
        this.pos = pos;
        this.msg = msg;
    }

    public String toString() {
        return msg + ": " + (pos == null ? "":pos.toString());
    }
}
