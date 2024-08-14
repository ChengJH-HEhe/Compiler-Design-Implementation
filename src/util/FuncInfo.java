package util;

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
}
