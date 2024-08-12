package frontend;
import ast.node.astNode;
import ast.node.astRoot;
import ast.node.def.*;
import ast.node.expr.*;
import ast.node.stmt.*;
import util.Type;
import util.globalScope;
import util.error.error;

import java.util.HashMap;

public class SymbolCollector implements astVisitor<astNode> {
    private globalScope gScope;
    private HashMap<String, Type> varMap = new HashMap<>();
    @Override
    public astNode visit(astNode node) throws error {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public astNode visit(astRoot node) throws error {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public astNode visit(astFuncDefNode node) throws error {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public astNode visit(astClassDefNode node) throws error {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public astNode visit(astVarDefNode node) throws error {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public astNode visit(astConstrNode node) throws error {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public astNode visit(astNewArrayExprNode node) throws error {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public astNode visit(astCallExprNode node) throws error {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public astNode visit(astArrayExprNode node) throws error {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public astNode visit(astArrayConstExpr node) throws error {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public astNode visit(astMemberExprNode node) throws error {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public astNode visit(astFStrExpr node) throws error {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public astNode visit(astUnaryExprNode node) throws error {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public astNode visit(astPreSelfExprNode node) throws error {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public astNode visit(astBinaryExprNode node) throws error {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public astNode visit(astConditionalExprNode node) throws error {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public astNode visit(astAssignExprNode node) throws error {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public astNode visit(astAtomExprNode node) throws error {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public astNode visit(astBlockStmtNode node) throws error {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public astNode visit(astIfStmtNode node) throws error {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public astNode visit(astForStmtNode node) throws error {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public astNode visit(astWhileStmtNode node) throws error {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public astNode visit(astContinueStmtNode node) throws error {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public astNode visit(astBreakStmtNode node) throws error {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public astNode visit(astPureExprNode node) throws error {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public astNode visit(astReturnStmtNode node) throws error {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public astNode visit(astExprStmtNode node) throws error {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public astNode visit(astVarDefStmtNode node) throws error {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public astNode visit(astEmptyStmtNode node) throws error {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }
    
}
