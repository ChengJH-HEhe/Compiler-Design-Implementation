package juhuh.compiler.util;

@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter
public class Info {
    private String name;
    Info(String name){
        this.name = name;
    }
    public boolean equals(Object obj){
        if(obj instanceof FuncInfo)
            return ((FuncInfo)obj).getRetType().equals(this);
        else if(obj instanceof typeinfo)
            return ((typeinfo)obj).equals(this);
        else  return false;
    }
    @Override
    public String toString(){
        return name;
    }
}
