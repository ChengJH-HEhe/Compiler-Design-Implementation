package util;

public class FuncInfo {
    public String name;
    public typeinfo retType;
    public vector<typeinfo> args;

    public FuncInfo(typeinfo retType, vector<typeinfo> args) {
        this.retType = retType;
        this.args = args;
    }
}
