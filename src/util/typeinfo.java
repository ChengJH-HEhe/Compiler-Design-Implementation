package util;

import java.util.HashMap;

@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter
public class typeinfo {
  private String Name;
  private boolean isbuiltin;
  private int dim;
  public HashMap<String, typeinfo> members = null;

  public typeinfo(String typeName, int arrayDepth) {
    this.Name = typeName;
    this.isbuiltin = typeName.equals("int") || typeName.equals("bool") || typeName.equals("string")
        || typeName.equals("void") || typeName.equals("null") || typeName.equals("this");
    this.dim = arrayDepth;
  }

  public boolean equals(Object otherInfo) {
    if (!(otherInfo instanceof typeinfo)) {
      return false;
    }
    var other = (typeinfo) otherInfo;
    if (this.getName().equals("null")) {
      return other.getName().equals("null") || other.dim > 0 || !other.isbuiltin;
    }
    if (other.getName().equals("null")) {
      return this.dim > 0 || !this.isbuiltin;
    }
    return this.getName().equals(other.getName()) && this.dim == other.dim;
  }
}
