package frontend;

import ast.*;
import ast.node.astNode;
import ast.node.astRoot;
import ast.node.def.astClassDefNode;
import ast.node.def.astFuncDefNode;
import ast.node.def.astVarDefNode;
import ast.node.expr.astArrayConstExpr;
import ast.node.expr.astArrayExprNode;
import ast.node.expr.astAssignExprNode;
import ast.node.expr.astAtomExprNode;
import ast.node.expr.astBinaryExprNode;
import ast.node.expr.astCallExprNode;
import ast.node.expr.astConditionalExprNode;
import ast.node.expr.astMemberExprNode;
import ast.node.expr.astNewArrayExprNode;
import ast.node.expr.astPreSelfExprNode;
import ast.node.expr.astUnaryExprNode;
import ast.node.stmt.astBlockStmtNode;
import ast.node.stmt.astBreakStmtNode;
import ast.node.stmt.astContinueStmtNode;
import ast.node.stmt.astEmptyStmtNode;
import ast.node.stmt.astExprStmtNode;
import ast.node.stmt.astForStmtNode;
import ast.node.stmt.astIfStmtNode;
import ast.node.stmt.astReturnStmtNode;
import ast.node.stmt.astVarDefStmtNode;
import ast.node.stmt.astWhileStmtNode;
import util.Scope;
import util.Type;
import util.error.error;
import util.error.semanticError;
import util.globalScope;

public class SemanticChecker implements astVisitor {
    private Scope currentScope;
    private globalScope gScope;
    private Type currentStruct = null;

    public SemanticChecker(globalScope gScope) {
        currentScope = this.gScope = gScope;
    }

    @Override
    public Object visit(astNode node) throws error {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public Object visit(astRoot node) throws error {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public Object visit(astFuncDefNode node) throws error {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public Object visit(astClassDefNode node) throws error {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public Object visit(astVarDefNode node) throws error {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public Object visit(astNewArrayExprNode node) throws error {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public Object visit(astCallExprNode node) throws error {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public Object visit(astArrayExprNode node) throws error {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public Object visit(astArrayConstExpr node) throws error {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public Object visit(astMemberExprNode node) throws error {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public Object visit(astUnaryExprNode node) throws error {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public Object visit(astPreSelfExprNode node) throws error {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public Object visit(astBinaryExprNode node) throws error {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public Object visit(astConditionalExprNode node) throws error {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public Object visit(astAssignExprNode node) throws error {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public Object visit(astAtomExprNode node) throws error {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public Object visit(astBlockStmtNode node) throws error {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public Object visit(astIfStmtNode node) throws error {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public Object visit(astForStmtNode node) throws error {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public Object visit(astWhileStmtNode node) throws error {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public Object visit(astContinueStmtNode node) throws error {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public Object visit(astBreakStmtNode node) throws error {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public Object visit(astReturnStmtNode node) throws error {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public Object visit(astExprStmtNode node) throws error {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public Object visit(astVarDefStmtNode node) throws error {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public Object visit(astEmptyStmtNode node) throws error {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    
}
