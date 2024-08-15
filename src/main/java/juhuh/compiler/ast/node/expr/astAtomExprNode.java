package juhuh.compiler.ast.node.expr;

import juhuh.compiler.frontend.astVisitor;
import juhuh.compiler.util.error.error;

@lombok.experimental.SuperBuilder
@lombok.Data
@lombok.EqualsAndHashCode(callSuper = true)
public class astAtomExprNode extends astExprNode {
    protected String Value; // is value == 
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
