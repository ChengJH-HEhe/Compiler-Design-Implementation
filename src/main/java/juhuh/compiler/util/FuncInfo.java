package juhuh.compiler.util;

import java.util.stream.Collectors;

// rettype == null -> Void
@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter
public class FuncInfo extends Info {
  public typeinfo retType;
  public vector<typeinfo> argsType;
  public FuncInfo(String name, typeinfo type, typeinfo... args) {
    super(name);
    this.retType = type;
    this.argsType = new vector<typeinfo>();
    for (var param : args) {
      this.argsType.add(param);
    }
  }
  public FuncInfo(FuncInfo other) {
    super(other.getName());
    this.retType = other.getRetType();
    this.argsType = other.getArgsType();
  }
  @Override
  public String toString() {
    return super.toString() + "(" + argsType.stream().map(Object::toString).collect(Collectors.joining(", ")) + ")";
  }
}
