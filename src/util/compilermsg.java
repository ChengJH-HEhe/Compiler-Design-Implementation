package util;
// compiler error memmage txt
import ast.node.astNode;

public class compilermsg{
  String msg;
  public compilermsg(String msg) {
    this.msg = msg;
    if (!msg.equals(" ")) {
      this.msg = "Compile Error: " + msg;
    } else {
      this.msg = "";
    }
  }
  public compilermsg(String msg, position pos) {
    this.msg = "Compile Error: " + msg + "at" + pos.toString() + ".";
  }
  public compilermsg(String msg, astNode node) {
    this.msg = "Compile Error: " + msg + "in" + node.toString() + "at" + node.getPos().toString() + ".";
  }
  public void append(compilermsg newMsg){
    if(this.msg.equals("")) {
      this.msg = newMsg.msg;
    } else {
      this.msg += "\n" + newMsg.msg;
    }
  }
  public boolean empty() {
    return this.msg.equals("");
  }
  @Override
  public String toString() {
    return this.msg;
  }
}
