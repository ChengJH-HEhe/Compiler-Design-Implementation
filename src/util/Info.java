package util;

@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter
public class Info {
    private String name;
    Info(String name){
        this.name = name;
    }
}
