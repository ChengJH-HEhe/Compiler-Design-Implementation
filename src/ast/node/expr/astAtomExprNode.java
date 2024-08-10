package ast.node.expr;

import frontend.astVisitor;
import util.error.error;

@lombok.experimental.SuperBuilder
@lombok.Value
@lombok.EqualsAndHashCode(callSuper = true)
public class astAtomExprNode extends astExprNode {
    private AtomType atomtype;
    private String Value; // is value == 
    @Override
    public String toString() {
        // replace \n -> '\' + 'n'
        return "\"" + Value.replace("\\", "\\\\")
        .replace("\n", "\\n")
        .replace("\0", "")
        .replace("\t","\\t")
        .replace("\"", "\\\"") + "\"";
    }

    @Override
    public <T> T accept(astVisitor<T> visitor) throws error {
        return visitor.visit(this);
    }
}
