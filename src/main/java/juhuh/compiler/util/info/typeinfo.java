package juhuh.compiler.util.info;

@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter
public class typeinfo extends Info{
  private boolean builtin;
  private int dim;
  private boolean dim_;
  public typeinfo(typeinfo other){
    super(other.getName());
    this.builtin = other.builtin;
    this.dim = other.dim;
    this.dim_ = other.dim_;
  }
  public typeinfo(String typeName, int arrayDepth) {
    super(typeName);
    this.builtin = typeName.equals("int") || typeName.equals("bool") || typeName.equals("string")
        || typeName.equals("void") || typeName.equals("null") || typeName.equals("this");
    this.dim = arrayDepth;
    this.dim_ = false;
  }
  public typeinfo(String typeName, int arrayDepth, boolean dim_) {
    super(typeName);
    this.builtin = typeName.equals("int") || typeName.equals("bool") || typeName.equals("string")
        || typeName.equals("void") || typeName.equals("null") || typeName.equals("this");
    this.dim = arrayDepth;
    this.dim_ = dim_;
  }
  public boolean equals(Object otherInfo) {
    if(otherInfo instanceof FuncInfo)
      return equals(((FuncInfo)otherInfo).getRetType());
    if (!(otherInfo instanceof typeinfo)) {
      return false;
    }
    var other = (typeinfo) otherInfo;
    // //System.err.println("this = ("+ this.dim + ")" + this.getName());
    // //System.err.println("other = ("+ other.dim + ")" + other.getName());
    if (getName().equals("null")) {
      return other.getName().equals("null") || other.dim > 0 || !other.builtin;
    }
    if (other.getName().equals("null")) {
      return this.dim > 0 || !this.builtin;
    }
    return this.getName().equals(other.getName()) && (this.dim == other.dim || 
    (this.dim_ && this.dim <= other.dim) || (other.dim_ && other.dim <= this.dim));
  }
  @Override
  public String toString() {
    return super.toString() + (dim>0 ? "[]" : "");
  }
}
