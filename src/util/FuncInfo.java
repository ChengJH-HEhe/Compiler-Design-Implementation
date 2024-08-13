package util;
// rettype == null -> Void
@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter
public class FuncInfo extends Info {
    public typeinfo retType;
    public vector<typeinfo> argsType;
}
