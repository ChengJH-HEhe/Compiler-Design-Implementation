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
        if(obj instanceof Info){
            if(this instanceof typeinfo)
                return ((typeinfo)this).equals(obj);
            else return false;
        }
        return false;
    }
    @Override
    public String toString(){
        return name;
    }
}
