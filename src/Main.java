import java.io.FileInputStream;
import java.io.InputStream;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import ast.node.astRoot;

// import Assembly.AsmFn;
// import Backend.AsmPrinter;
// import Backend.IRBuilder;
// import Backend.InstSelector;
// import Backend.RegAlloc;

import frontend.*;

// import MIR.mainFn;

import parser.*;

import util.*;
import util.error.error;


public class Main {
    public static void main(String[] args) throws Exception{

        String name = "test.Mx";
        InputStream input = new FileInputStream(name);

        try {
            globalScope gScope = new globalScope(null);

            MxLexer lexer = new MxLexer(CharStreams.fromStream(input));
            lexer.removeErrorListeners();
            lexer.addErrorListener(new MxErrorListener());
            MxParser parser = new MxParser(new CommonTokenStream(lexer));
            parser.removeErrorListeners();
            parser.addErrorListener(new MxErrorListener());

            ParseTree parseTreeRoot = parser.program();
            astBuilder astBuilder = new astBuilder();

            var ASTRoot = (astRoot)astBuilder.visit(parseTreeRoot);
            new SymbolCollector(gScope).visit(ASTRoot);
            new SemanticChecker(gScope).visit(ASTRoot);

            // mainFn f = new mainFn();
            // new IRBuilder(f, gScope).visit(ASTRoot);
            // // new IRPrinter(System.out).visitFn(f);

            // AsmFn asmF = new AsmFn();
            // new InstSelector(asmF).visitFn(f);
            // new RegAlloc(asmF).work();
            // new AsmPrinter(asmF, System.out).print();
        } catch (error er) {
            System.err.println(er.toString());
            throw new RuntimeException();
        }
    }
}