package util;

@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter
public class typeinfo extends Info{
  private boolean builtin;
  private int dim;

  public typeinfo(String typeName, int arrayDepth) {
    super(typeName);
    this.builtin = typeName.equals("int") || typeName.equals("bool") || typeName.equals("string")
        || typeName.equals("void") || typeName.equals("null") || typeName.equals("this");
    this.dim = arrayDepth;
  }

  public boolean equals(Object otherInfo) {
    if (!(otherInfo instanceof typeinfo)) {
      return false;
    }
    var other = (typeinfo) otherInfo;
    if (getName().equals("null")) {
      return other.getName().equals("null") || other.dim > 0 || !other.builtin;
    }
    if (other.getName().equals("null")) {
      return this.dim > 0 || !this.builtin;
    }
    return this.getName().equals(other.getName()) && this.dim == other.dim;
  }
}
