package util.error;
import util.position;

abstract public class error extends RuntimeException {
    private position pos;
    private String msg;

    public error(String msg, position pos) {
        this.pos = pos;
        this.msg = msg;
    }

    public String toString() {
        return msg + ": " + pos.toString();
    }
}
