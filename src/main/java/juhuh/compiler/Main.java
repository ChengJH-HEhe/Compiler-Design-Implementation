package juhuh.compiler;


// import java.io.FileInputStream;
// import java.io.InputStream;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import juhuh.compiler.ast.node.astRoot;
import juhuh.compiler.backend.asmBuilder;

// import Assembly.AsmFn;
// import Backend.AsmPrinter;
// import Backend.IRBuilder;
// import Backend.InstSelector;
// import Backend.RegAlloc;

import juhuh.compiler.frontend.*;
import juhuh.compiler.ir.irRoot;

// import MIR.mainFn;

import juhuh.compiler.parser.*;

import juhuh.compiler.util.*;
import juhuh.compiler.util.error.error;


public class Main {
    public static void main(String[] args) throws Exception{
        var input = CharStreams.fromStream(System.in);
        try {
            globalScope gScope = new globalScope(null,null),
            origin = new globalScope(null,null);
            
            MxLexer lexer = new MxLexer(input);
            lexer.removeErrorListeners();
            lexer.addErrorListener(new MxErrorListener());
            MxParser parser = new MxParser(new CommonTokenStream(lexer));
            parser.removeErrorListeners();
            parser.addErrorListener(new MxErrorListener());

            ParseTree parseTreeRoot = parser.program();
            astBuilder astBuilder = new astBuilder();

            var ASTRoot = (astRoot)astBuilder.visit(parseTreeRoot);
            //System.err.println("AST built successfully");
            var symbol = new SymbolCollector(gScope, origin);
            symbol.visit(ASTRoot);
            //System.err.println("Symbol collected successfully");
            new SemanticChecker(gScope).visit(ASTRoot);
            //System.err.println("Sema successfully");
            irBuilder IR = new irBuilder(origin);
            irRoot rt = (irRoot) IR.visit(ASTRoot);
            // {
            //     FileWriter writer = new FileWriter("output.ll");   
            //     writer.write(rt.toString());
            //     writer.close();
            // }
            asmBuilder asm = new asmBuilder();
            asm.visit(rt);
            System.out.print(asm.getRt().toString());
            // {
            //     FileWriter writer = new FileWriter("test.s");   
            //     writer.write(asm.getRt().toString());
            //     writer.close();
            // }
            // new RegAlloc(asmF).work();
            // new AsmPrinter(asmF, System.out).print();
        } 
        catch (error er) {
            System.out.println(er.toString());
            System.exit(1);
        }
        System.exit(0);
    }
}