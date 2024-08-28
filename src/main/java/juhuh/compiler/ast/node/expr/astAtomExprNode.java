package juhuh.compiler.ast.node.expr;

import juhuh.compiler.frontend.astVisitor;
import juhuh.compiler.util.error.error;

@lombok.experimental.SuperBuilder
@lombok.Data
@lombok.EqualsAndHashCode(callSuper = true)
public class astAtomExprNode extends astExprNode {
    private String Value; // is value == 
    @Override
    public String toString() {
        // replace \n -> '\' + 'n'
        // \" -> "
        return Value.substring(1, Value.length()-1);
    }

    @Override
    public <T> T accept(astVisitor<T> visitor) throws error {
        return visitor.visit(this);
    }
}
