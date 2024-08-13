package frontend;

import ast.node.astNode;
import ast.node.astRoot;
import ast.node.def.*;
import ast.node.expr.*;
import ast.node.stmt.*;
import util.ClassInfo;
import util.Scope;
import util.error.error;
import util.globalScope;
import util.typeinfo;

public class SemanticChecker implements astVisitor<String> {
    private Scope curS;
    private globalScope gScope;
    private ClassInfo curC = null;
    public SemanticChecker(globalScope gScope) {
        curS = this.gScope = gScope;
    }

    @Override
    public String visit(astNode node) throws error {
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public String visit(astRoot node) throws error {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public String visit(astFuncDefNode node) throws error {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public String visit(astClassDefNode node) throws error {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public String visit(astVarDefNode node) throws error {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public String visit(astNewArrayExprNode node) throws error {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public String visit(astCallExprNode node) throws error {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public String visit(astArrayExprNode node) throws error {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public String visit(astArrayConstExpr node) throws error {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public String visit(astMemberExprNode node) throws error {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public String visit(astUnaryExprNode node) throws error {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public String visit(astPreSelfExprNode node) throws error {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public String visit(astBinaryExprNode node) throws error {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public String visit(astConditionalExprNode node) throws error {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public String visit(astAssignExprNode node) throws error {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public String visit(astAtomExprNode node) throws error {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public String visit(astBlockStmtNode node) throws error {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public String visit(astIfStmtNode node) throws error {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public String visit(astForStmtNode node) throws error {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public String visit(astWhileStmtNode node) throws error {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public String visit(astContinueStmtNode node) throws error {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public String visit(astBreakStmtNode node) throws error {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public String visit(astReturnStmtNode node) throws error {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public String visit(astExprStmtNode node) throws error {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public String visit(astVarDefStmtNode node) throws error {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public String visit(astEmptyStmtNode node) throws error {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public String visit(astConstrNode node) throws error {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public String visit(astFStrExpr node) throws error {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public String visit(astPureExprNode node) throws error {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }    
}
