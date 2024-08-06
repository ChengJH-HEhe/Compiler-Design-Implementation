// Generated from /home/unix/github/Compiler-Design-Implementation/src/parser/Mx.g4 by ANTLR 4.9.2
package parser;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class MxParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.9.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		Void=1, Bool=2, Int=3, String=4, New=5, Class=6, Null=7, True=8, False=9, 
		This=10, If=11, Else=12, For=13, While=14, Break=15, Continue=16, Return=17, 
		Plus=18, Minus=19, Mul=20, Div=21, Mod=22, Greater=23, Less=24, GreaterEqual=25, 
		LessEqual=26, UnEqual=27, Equal=28, LogicAnd=29, LogicOr=30, LogicNot=31, 
		RightShift=32, LeftShift=33, And=34, Or=35, Xor=36, Not=37, Assign=38, 
		Increment=39, Decrement=40, Member=41, Identifier=42, LParen=43, RParen=44, 
		LBracket=45, RBracket=46, LBrace=47, RBrace=48, Question=49, Colon=50, 
		Semi=51, Comma=52, Quote=53, Escape=54, IntegerConst=55, FstringConst=56, 
		Fstring_l=57, Fstring_m=58, StringConst=59, Fstring_lst=60, WhiteSpace=61, 
		LineComment=62, ParaComment=63, Non_s=64;
	public static final int
		RULE_program = 0, RULE_classDef = 1, RULE_constr = 2, RULE_funcDef = 3, 
		RULE_arglist = 4, RULE_varDef = 5, RULE_varDefn = 6, RULE_returnType = 7, 
		RULE_type = 8, RULE_typename = 9, RULE_baseType = 10, RULE_block = 11, 
		RULE_arrayConst = 12, RULE_literal = 13, RULE_primary = 14, RULE_stmt = 15, 
		RULE_expr = 16, RULE_formatStr = 17;
	private static String[] makeRuleNames() {
		return new String[] {
			"program", "classDef", "constr", "funcDef", "arglist", "varDef", "varDefn", 
			"returnType", "type", "typename", "baseType", "block", "arrayConst", 
			"literal", "primary", "stmt", "expr", "formatStr"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'void'", "'bool'", "'int'", "'string'", "'new'", "'class'", "'null'", 
			"'true'", "'false'", "'this'", "'if'", "'else'", "'for'", "'while'", 
			"'break'", "'continue'", "'return'", "'+'", "'-'", "'*'", "'/'", "'%'", 
			"'>'", "'<'", "'>='", "'<='", "'!='", "'=='", "'&&'", "'||'", "'!'", 
			"'>>'", "'<<'", "'&'", "'|'", "'^'", "'~'", "'='", "'++'", "'--'", "'.'", 
			null, "'('", "')'", "'['", "']'", "'{'", "'}'", "'?'", "':'", "';'", 
			"','", "'\"'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "Void", "Bool", "Int", "String", "New", "Class", "Null", "True", 
			"False", "This", "If", "Else", "For", "While", "Break", "Continue", "Return", 
			"Plus", "Minus", "Mul", "Div", "Mod", "Greater", "Less", "GreaterEqual", 
			"LessEqual", "UnEqual", "Equal", "LogicAnd", "LogicOr", "LogicNot", "RightShift", 
			"LeftShift", "And", "Or", "Xor", "Not", "Assign", "Increment", "Decrement", 
			"Member", "Identifier", "LParen", "RParen", "LBracket", "RBracket", "LBrace", 
			"RBrace", "Question", "Colon", "Semi", "Comma", "Quote", "Escape", "IntegerConst", 
			"FstringConst", "Fstring_l", "Fstring_m", "StringConst", "Fstring_lst", 
			"WhiteSpace", "LineComment", "ParaComment", "Non_s"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "Mx.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public MxParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class ProgramContext extends ParserRuleContext {
		public TerminalNode EOF() { return getToken(MxParser.EOF, 0); }
		public List<FuncDefContext> funcDef() {
			return getRuleContexts(FuncDefContext.class);
		}
		public FuncDefContext funcDef(int i) {
			return getRuleContext(FuncDefContext.class,i);
		}
		public List<ClassDefContext> classDef() {
			return getRuleContexts(ClassDefContext.class);
		}
		public ClassDefContext classDef(int i) {
			return getRuleContext(ClassDefContext.class,i);
		}
		public List<VarDefContext> varDef() {
			return getRuleContexts(VarDefContext.class);
		}
		public VarDefContext varDef(int i) {
			return getRuleContext(VarDefContext.class,i);
		}
		public List<TerminalNode> Semi() { return getTokens(MxParser.Semi); }
		public TerminalNode Semi(int i) {
			return getToken(MxParser.Semi, i);
		}
		public ProgramContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_program; }
	}

	public final ProgramContext program() throws RecognitionException {
		ProgramContext _localctx = new ProgramContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_program);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(43);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << Void) | (1L << Bool) | (1L << Int) | (1L << String) | (1L << Class) | (1L << Identifier))) != 0)) {
				{
				setState(41);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,0,_ctx) ) {
				case 1:
					{
					setState(36);
					funcDef();
					}
					break;
				case 2:
					{
					setState(37);
					classDef();
					}
					break;
				case 3:
					{
					{
					setState(38);
					varDef();
					setState(39);
					match(Semi);
					}
					}
					break;
				}
				}
				setState(45);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(46);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ClassDefContext extends ParserRuleContext {
		public TerminalNode Class() { return getToken(MxParser.Class, 0); }
		public TerminalNode Identifier() { return getToken(MxParser.Identifier, 0); }
		public TerminalNode LBrace() { return getToken(MxParser.LBrace, 0); }
		public TerminalNode RBrace() { return getToken(MxParser.RBrace, 0); }
		public List<TerminalNode> Semi() { return getTokens(MxParser.Semi); }
		public TerminalNode Semi(int i) {
			return getToken(MxParser.Semi, i);
		}
		public List<VarDefContext> varDef() {
			return getRuleContexts(VarDefContext.class);
		}
		public VarDefContext varDef(int i) {
			return getRuleContext(VarDefContext.class,i);
		}
		public List<FuncDefContext> funcDef() {
			return getRuleContexts(FuncDefContext.class);
		}
		public FuncDefContext funcDef(int i) {
			return getRuleContext(FuncDefContext.class,i);
		}
		public List<ConstrContext> constr() {
			return getRuleContexts(ConstrContext.class);
		}
		public ConstrContext constr(int i) {
			return getRuleContext(ConstrContext.class,i);
		}
		public ClassDefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_classDef; }
	}

	public final ClassDefContext classDef() throws RecognitionException {
		ClassDefContext _localctx = new ClassDefContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_classDef);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(48);
			match(Class);
			setState(49);
			match(Identifier);
			setState(50);
			match(LBrace);
			setState(58);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << Void) | (1L << Bool) | (1L << Int) | (1L << String) | (1L << Identifier))) != 0)) {
				{
				setState(56);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,2,_ctx) ) {
				case 1:
					{
					setState(51);
					varDef();
					setState(52);
					match(Semi);
					}
					break;
				case 2:
					{
					setState(54);
					funcDef();
					}
					break;
				case 3:
					{
					setState(55);
					constr();
					}
					break;
				}
				}
				setState(60);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(61);
			match(RBrace);
			setState(62);
			match(Semi);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ConstrContext extends ParserRuleContext {
		public TerminalNode Identifier() { return getToken(MxParser.Identifier, 0); }
		public TerminalNode LParen() { return getToken(MxParser.LParen, 0); }
		public TerminalNode RParen() { return getToken(MxParser.RParen, 0); }
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public ConstrContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_constr; }
	}

	public final ConstrContext constr() throws RecognitionException {
		ConstrContext _localctx = new ConstrContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_constr);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(64);
			match(Identifier);
			setState(65);
			match(LParen);
			setState(66);
			match(RParen);
			setState(67);
			block();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FuncDefContext extends ParserRuleContext {
		public ReturnTypeContext returnType() {
			return getRuleContext(ReturnTypeContext.class,0);
		}
		public TerminalNode Identifier() { return getToken(MxParser.Identifier, 0); }
		public TerminalNode LParen() { return getToken(MxParser.LParen, 0); }
		public TerminalNode RParen() { return getToken(MxParser.RParen, 0); }
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public ArglistContext arglist() {
			return getRuleContext(ArglistContext.class,0);
		}
		public FuncDefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_funcDef; }
	}

	public final FuncDefContext funcDef() throws RecognitionException {
		FuncDefContext _localctx = new FuncDefContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_funcDef);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(69);
			returnType();
			setState(70);
			match(Identifier);
			setState(71);
			match(LParen);
			setState(73);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << Bool) | (1L << Int) | (1L << String) | (1L << Identifier))) != 0)) {
				{
				setState(72);
				arglist();
				}
			}

			setState(75);
			match(RParen);
			setState(76);
			block();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ArglistContext extends ParserRuleContext {
		public List<TypeContext> type() {
			return getRuleContexts(TypeContext.class);
		}
		public TypeContext type(int i) {
			return getRuleContext(TypeContext.class,i);
		}
		public List<TerminalNode> Identifier() { return getTokens(MxParser.Identifier); }
		public TerminalNode Identifier(int i) {
			return getToken(MxParser.Identifier, i);
		}
		public List<TerminalNode> Comma() { return getTokens(MxParser.Comma); }
		public TerminalNode Comma(int i) {
			return getToken(MxParser.Comma, i);
		}
		public ArglistContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arglist; }
	}

	public final ArglistContext arglist() throws RecognitionException {
		ArglistContext _localctx = new ArglistContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_arglist);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(78);
			type();
			setState(79);
			match(Identifier);
			setState(86);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==Comma) {
				{
				{
				setState(80);
				match(Comma);
				setState(81);
				type();
				setState(82);
				match(Identifier);
				}
				}
				setState(88);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VarDefContext extends ParserRuleContext {
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public List<VarDefnContext> varDefn() {
			return getRuleContexts(VarDefnContext.class);
		}
		public VarDefnContext varDefn(int i) {
			return getRuleContext(VarDefnContext.class,i);
		}
		public List<TerminalNode> Comma() { return getTokens(MxParser.Comma); }
		public TerminalNode Comma(int i) {
			return getToken(MxParser.Comma, i);
		}
		public VarDefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_varDef; }
	}

	public final VarDefContext varDef() throws RecognitionException {
		VarDefContext _localctx = new VarDefContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_varDef);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(89);
			type();
			setState(90);
			varDefn();
			setState(95);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==Comma) {
				{
				{
				setState(91);
				match(Comma);
				setState(92);
				varDefn();
				}
				}
				setState(97);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VarDefnContext extends ParserRuleContext {
		public TerminalNode Identifier() { return getToken(MxParser.Identifier, 0); }
		public TerminalNode Assign() { return getToken(MxParser.Assign, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public VarDefnContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_varDefn; }
	}

	public final VarDefnContext varDefn() throws RecognitionException {
		VarDefnContext _localctx = new VarDefnContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_varDefn);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(98);
			match(Identifier);
			setState(101);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Assign) {
				{
				setState(99);
				match(Assign);
				setState(100);
				expr(0);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ReturnTypeContext extends ParserRuleContext {
		public TerminalNode Void() { return getToken(MxParser.Void, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public ReturnTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_returnType; }
	}

	public final ReturnTypeContext returnType() throws RecognitionException {
		ReturnTypeContext _localctx = new ReturnTypeContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_returnType);
		try {
			setState(105);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Void:
				enterOuterAlt(_localctx, 1);
				{
				setState(103);
				match(Void);
				}
				break;
			case Bool:
			case Int:
			case String:
			case Identifier:
				enterOuterAlt(_localctx, 2);
				{
				setState(104);
				type();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypeContext extends ParserRuleContext {
		public TypenameContext typename() {
			return getRuleContext(TypenameContext.class,0);
		}
		public List<TerminalNode> LBracket() { return getTokens(MxParser.LBracket); }
		public TerminalNode LBracket(int i) {
			return getToken(MxParser.LBracket, i);
		}
		public List<TerminalNode> RBracket() { return getTokens(MxParser.RBracket); }
		public TerminalNode RBracket(int i) {
			return getToken(MxParser.RBracket, i);
		}
		public TypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type; }
	}

	public final TypeContext type() throws RecognitionException {
		TypeContext _localctx = new TypeContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_type);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(107);
			typename();
			setState(112);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==LBracket) {
				{
				{
				setState(108);
				match(LBracket);
				setState(109);
				match(RBracket);
				}
				}
				setState(114);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypenameContext extends ParserRuleContext {
		public BaseTypeContext baseType() {
			return getRuleContext(BaseTypeContext.class,0);
		}
		public TerminalNode Identifier() { return getToken(MxParser.Identifier, 0); }
		public TypenameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typename; }
	}

	public final TypenameContext typename() throws RecognitionException {
		TypenameContext _localctx = new TypenameContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_typename);
		try {
			setState(117);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Bool:
			case Int:
			case String:
				enterOuterAlt(_localctx, 1);
				{
				setState(115);
				baseType();
				}
				break;
			case Identifier:
				enterOuterAlt(_localctx, 2);
				{
				setState(116);
				match(Identifier);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BaseTypeContext extends ParserRuleContext {
		public TerminalNode Bool() { return getToken(MxParser.Bool, 0); }
		public TerminalNode Int() { return getToken(MxParser.Int, 0); }
		public TerminalNode String() { return getToken(MxParser.String, 0); }
		public BaseTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_baseType; }
	}

	public final BaseTypeContext baseType() throws RecognitionException {
		BaseTypeContext _localctx = new BaseTypeContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_baseType);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(119);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << Bool) | (1L << Int) | (1L << String))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BlockContext extends ParserRuleContext {
		public TerminalNode LBrace() { return getToken(MxParser.LBrace, 0); }
		public TerminalNode RBrace() { return getToken(MxParser.RBrace, 0); }
		public List<StmtContext> stmt() {
			return getRuleContexts(StmtContext.class);
		}
		public StmtContext stmt(int i) {
			return getRuleContext(StmtContext.class,i);
		}
		public BlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_block; }
	}

	public final BlockContext block() throws RecognitionException {
		BlockContext _localctx = new BlockContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_block);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(121);
			match(LBrace);
			setState(125);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << Bool) | (1L << Int) | (1L << String) | (1L << New) | (1L << Null) | (1L << True) | (1L << False) | (1L << This) | (1L << If) | (1L << For) | (1L << While) | (1L << Break) | (1L << Continue) | (1L << Return) | (1L << Plus) | (1L << Minus) | (1L << LogicNot) | (1L << Not) | (1L << Increment) | (1L << Decrement) | (1L << Identifier) | (1L << LParen) | (1L << LBrace) | (1L << Semi) | (1L << IntegerConst) | (1L << FstringConst) | (1L << Fstring_l) | (1L << StringConst))) != 0)) {
				{
				{
				setState(122);
				stmt();
				}
				}
				setState(127);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(128);
			match(RBrace);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ArrayConstContext extends ParserRuleContext {
		public TerminalNode LBrace() { return getToken(MxParser.LBrace, 0); }
		public TerminalNode RBrace() { return getToken(MxParser.RBrace, 0); }
		public List<LiteralContext> literal() {
			return getRuleContexts(LiteralContext.class);
		}
		public LiteralContext literal(int i) {
			return getRuleContext(LiteralContext.class,i);
		}
		public List<TerminalNode> Comma() { return getTokens(MxParser.Comma); }
		public TerminalNode Comma(int i) {
			return getToken(MxParser.Comma, i);
		}
		public ArrayConstContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arrayConst; }
	}

	public final ArrayConstContext arrayConst() throws RecognitionException {
		ArrayConstContext _localctx = new ArrayConstContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_arrayConst);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(130);
			match(LBrace);
			setState(141);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Null:
			case True:
			case False:
			case LBrace:
			case IntegerConst:
			case FstringConst:
			case Fstring_l:
			case StringConst:
				{
				{
				setState(136);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,12,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(131);
						literal();
						setState(132);
						match(Comma);
						}
						} 
					}
					setState(138);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,12,_ctx);
				}
				setState(139);
				literal();
				}
				}
				break;
			case RBrace:
				{
				{
				}
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(143);
			match(RBrace);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LiteralContext extends ParserRuleContext {
		public TerminalNode True() { return getToken(MxParser.True, 0); }
		public TerminalNode False() { return getToken(MxParser.False, 0); }
		public TerminalNode IntegerConst() { return getToken(MxParser.IntegerConst, 0); }
		public TerminalNode StringConst() { return getToken(MxParser.StringConst, 0); }
		public TerminalNode Null() { return getToken(MxParser.Null, 0); }
		public ArrayConstContext arrayConst() {
			return getRuleContext(ArrayConstContext.class,0);
		}
		public FormatStrContext formatStr() {
			return getRuleContext(FormatStrContext.class,0);
		}
		public LiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_literal; }
	}

	public final LiteralContext literal() throws RecognitionException {
		LiteralContext _localctx = new LiteralContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_literal);
		try {
			setState(152);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case True:
				enterOuterAlt(_localctx, 1);
				{
				setState(145);
				match(True);
				}
				break;
			case False:
				enterOuterAlt(_localctx, 2);
				{
				setState(146);
				match(False);
				}
				break;
			case IntegerConst:
				enterOuterAlt(_localctx, 3);
				{
				setState(147);
				match(IntegerConst);
				}
				break;
			case StringConst:
				enterOuterAlt(_localctx, 4);
				{
				setState(148);
				match(StringConst);
				}
				break;
			case Null:
				enterOuterAlt(_localctx, 5);
				{
				setState(149);
				match(Null);
				}
				break;
			case LBrace:
				enterOuterAlt(_localctx, 6);
				{
				setState(150);
				arrayConst();
				}
				break;
			case FstringConst:
			case Fstring_l:
				enterOuterAlt(_localctx, 7);
				{
				setState(151);
				formatStr();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PrimaryContext extends ParserRuleContext {
		public TerminalNode Identifier() { return getToken(MxParser.Identifier, 0); }
		public LiteralContext literal() {
			return getRuleContext(LiteralContext.class,0);
		}
		public TerminalNode This() { return getToken(MxParser.This, 0); }
		public PrimaryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_primary; }
	}

	public final PrimaryContext primary() throws RecognitionException {
		PrimaryContext _localctx = new PrimaryContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_primary);
		try {
			setState(157);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Identifier:
				enterOuterAlt(_localctx, 1);
				{
				setState(154);
				match(Identifier);
				}
				break;
			case Null:
			case True:
			case False:
			case LBrace:
			case IntegerConst:
			case FstringConst:
			case Fstring_l:
			case StringConst:
				enterOuterAlt(_localctx, 2);
				{
				setState(155);
				literal();
				}
				break;
			case This:
				enterOuterAlt(_localctx, 3);
				{
				setState(156);
				match(This);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StmtContext extends ParserRuleContext {
		public StmtContext thenStmt;
		public StmtContext elseStmt;
		public StmtContext initStmt;
		public ExprContext condExpr;
		public ExprContext stepexpr;
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public VarDefContext varDef() {
			return getRuleContext(VarDefContext.class,0);
		}
		public TerminalNode Semi() { return getToken(MxParser.Semi, 0); }
		public TerminalNode If() { return getToken(MxParser.If, 0); }
		public TerminalNode LParen() { return getToken(MxParser.LParen, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode RParen() { return getToken(MxParser.RParen, 0); }
		public List<StmtContext> stmt() {
			return getRuleContexts(StmtContext.class);
		}
		public StmtContext stmt(int i) {
			return getRuleContext(StmtContext.class,i);
		}
		public TerminalNode Else() { return getToken(MxParser.Else, 0); }
		public TerminalNode While() { return getToken(MxParser.While, 0); }
		public TerminalNode For() { return getToken(MxParser.For, 0); }
		public TerminalNode Return() { return getToken(MxParser.Return, 0); }
		public TerminalNode Break() { return getToken(MxParser.Break, 0); }
		public TerminalNode Continue() { return getToken(MxParser.Continue, 0); }
		public StmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_stmt; }
	}

	public final StmtContext stmt() throws RecognitionException {
		StmtContext _localctx = new StmtContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_stmt);
		int _la;
		try {
			setState(201);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,19,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(159);
				block();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(160);
				varDef();
				setState(161);
				match(Semi);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(163);
				match(If);
				setState(164);
				match(LParen);
				setState(165);
				expr(0);
				setState(166);
				match(RParen);
				setState(167);
				((StmtContext)_localctx).thenStmt = stmt();
				setState(170);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,16,_ctx) ) {
				case 1:
					{
					setState(168);
					match(Else);
					setState(169);
					((StmtContext)_localctx).elseStmt = stmt();
					}
					break;
				}
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(172);
				match(While);
				setState(173);
				match(LParen);
				setState(174);
				expr(0);
				setState(175);
				match(RParen);
				setState(176);
				stmt();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(178);
				match(For);
				setState(179);
				match(LParen);
				setState(180);
				((StmtContext)_localctx).initStmt = stmt();
				setState(182);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << New) | (1L << Null) | (1L << True) | (1L << False) | (1L << This) | (1L << Plus) | (1L << Minus) | (1L << LogicNot) | (1L << Not) | (1L << Increment) | (1L << Decrement) | (1L << Identifier) | (1L << LParen) | (1L << LBrace) | (1L << IntegerConst) | (1L << FstringConst) | (1L << Fstring_l) | (1L << StringConst))) != 0)) {
					{
					setState(181);
					((StmtContext)_localctx).condExpr = expr(0);
					}
				}

				setState(184);
				match(Semi);
				setState(185);
				((StmtContext)_localctx).stepexpr = expr(0);
				setState(186);
				match(RParen);
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(188);
				match(Return);
				setState(190);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << New) | (1L << Null) | (1L << True) | (1L << False) | (1L << This) | (1L << Plus) | (1L << Minus) | (1L << LogicNot) | (1L << Not) | (1L << Increment) | (1L << Decrement) | (1L << Identifier) | (1L << LParen) | (1L << LBrace) | (1L << IntegerConst) | (1L << FstringConst) | (1L << Fstring_l) | (1L << StringConst))) != 0)) {
					{
					setState(189);
					expr(0);
					}
				}

				setState(192);
				match(Semi);
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(193);
				match(Break);
				setState(194);
				match(Semi);
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(195);
				match(Continue);
				setState(196);
				match(Semi);
				}
				break;
			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(197);
				expr(0);
				setState(198);
				match(Semi);
				}
				break;
			case 10:
				enterOuterAlt(_localctx, 10);
				{
				setState(200);
				match(Semi);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExprContext extends ParserRuleContext {
		public Token op;
		public TerminalNode New() { return getToken(MxParser.New, 0); }
		public TypenameContext typename() {
			return getRuleContext(TypenameContext.class,0);
		}
		public List<TerminalNode> LBracket() { return getTokens(MxParser.LBracket); }
		public TerminalNode LBracket(int i) {
			return getToken(MxParser.LBracket, i);
		}
		public List<TerminalNode> RBracket() { return getTokens(MxParser.RBracket); }
		public TerminalNode RBracket(int i) {
			return getToken(MxParser.RBracket, i);
		}
		public ArrayConstContext arrayConst() {
			return getRuleContext(ArrayConstContext.class,0);
		}
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode LParen() { return getToken(MxParser.LParen, 0); }
		public TerminalNode RParen() { return getToken(MxParser.RParen, 0); }
		public TerminalNode Not() { return getToken(MxParser.Not, 0); }
		public TerminalNode LogicNot() { return getToken(MxParser.LogicNot, 0); }
		public TerminalNode Minus() { return getToken(MxParser.Minus, 0); }
		public TerminalNode Plus() { return getToken(MxParser.Plus, 0); }
		public TerminalNode Increment() { return getToken(MxParser.Increment, 0); }
		public TerminalNode Decrement() { return getToken(MxParser.Decrement, 0); }
		public PrimaryContext primary() {
			return getRuleContext(PrimaryContext.class,0);
		}
		public TerminalNode Mul() { return getToken(MxParser.Mul, 0); }
		public TerminalNode Div() { return getToken(MxParser.Div, 0); }
		public TerminalNode Mod() { return getToken(MxParser.Mod, 0); }
		public TerminalNode LeftShift() { return getToken(MxParser.LeftShift, 0); }
		public TerminalNode RightShift() { return getToken(MxParser.RightShift, 0); }
		public TerminalNode Greater() { return getToken(MxParser.Greater, 0); }
		public TerminalNode GreaterEqual() { return getToken(MxParser.GreaterEqual, 0); }
		public TerminalNode Less() { return getToken(MxParser.Less, 0); }
		public TerminalNode LessEqual() { return getToken(MxParser.LessEqual, 0); }
		public TerminalNode Equal() { return getToken(MxParser.Equal, 0); }
		public TerminalNode UnEqual() { return getToken(MxParser.UnEqual, 0); }
		public TerminalNode And() { return getToken(MxParser.And, 0); }
		public TerminalNode Xor() { return getToken(MxParser.Xor, 0); }
		public TerminalNode Or() { return getToken(MxParser.Or, 0); }
		public TerminalNode LogicAnd() { return getToken(MxParser.LogicAnd, 0); }
		public TerminalNode LogicOr() { return getToken(MxParser.LogicOr, 0); }
		public TerminalNode Question() { return getToken(MxParser.Question, 0); }
		public TerminalNode Colon() { return getToken(MxParser.Colon, 0); }
		public TerminalNode Assign() { return getToken(MxParser.Assign, 0); }
		public List<TerminalNode> Comma() { return getTokens(MxParser.Comma); }
		public TerminalNode Comma(int i) {
			return getToken(MxParser.Comma, i);
		}
		public TerminalNode Identifier() { return getToken(MxParser.Identifier, 0); }
		public TerminalNode Member() { return getToken(MxParser.Member, 0); }
		public ExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expr; }
	}

	public final ExprContext expr() throws RecognitionException {
		return expr(0);
	}

	private ExprContext expr(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExprContext _localctx = new ExprContext(_ctx, _parentState);
		ExprContext _prevctx = _localctx;
		int _startState = 32;
		enterRecursionRule(_localctx, 32, RULE_expr, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(233);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,24,_ctx) ) {
			case 1:
				{
				setState(204);
				match(New);
				setState(205);
				typename();
				setState(211); 
				_errHandler.sync(this);
				_alt = 1;
				do {
					switch (_alt) {
					case 1:
						{
						{
						setState(206);
						match(LBracket);
						setState(208);
						_errHandler.sync(this);
						_la = _input.LA(1);
						if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << New) | (1L << Null) | (1L << True) | (1L << False) | (1L << This) | (1L << Plus) | (1L << Minus) | (1L << LogicNot) | (1L << Not) | (1L << Increment) | (1L << Decrement) | (1L << Identifier) | (1L << LParen) | (1L << LBrace) | (1L << IntegerConst) | (1L << FstringConst) | (1L << Fstring_l) | (1L << StringConst))) != 0)) {
							{
							setState(207);
							expr(0);
							}
						}

						setState(210);
						match(RBracket);
						}
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					setState(213); 
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,21,_ctx);
				} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
				{
				setState(216);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,22,_ctx) ) {
				case 1:
					{
					setState(215);
					arrayConst();
					}
					break;
				}
				}
				}
				break;
			case 2:
				{
				setState(218);
				match(New);
				setState(219);
				typename();
				setState(222);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,23,_ctx) ) {
				case 1:
					{
					setState(220);
					match(LParen);
					setState(221);
					match(RParen);
					}
					break;
				}
				}
				break;
			case 3:
				{
				setState(224);
				((ExprContext)_localctx).op = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << Plus) | (1L << Minus) | (1L << LogicNot) | (1L << Not))) != 0)) ) {
					((ExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(225);
				expr(16);
				}
				break;
			case 4:
				{
				setState(226);
				((ExprContext)_localctx).op = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==Increment || _la==Decrement) ) {
					((ExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(227);
				expr(15);
				}
				break;
			case 5:
				{
				setState(228);
				match(LParen);
				setState(229);
				expr(0);
				setState(230);
				match(RParen);
				}
				break;
			case 6:
				{
				setState(232);
				primary();
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(299);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,28,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(297);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,27,_ctx) ) {
					case 1:
						{
						_localctx = new ExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(235);
						if (!(precpred(_ctx, 14))) throw new FailedPredicateException(this, "precpred(_ctx, 14)");
						setState(236);
						((ExprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << Mul) | (1L << Div) | (1L << Mod))) != 0)) ) {
							((ExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(237);
						expr(15);
						}
						break;
					case 2:
						{
						_localctx = new ExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(238);
						if (!(precpred(_ctx, 13))) throw new FailedPredicateException(this, "precpred(_ctx, 13)");
						setState(239);
						((ExprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==Plus || _la==Minus) ) {
							((ExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(240);
						expr(14);
						}
						break;
					case 3:
						{
						_localctx = new ExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(241);
						if (!(precpred(_ctx, 12))) throw new FailedPredicateException(this, "precpred(_ctx, 12)");
						setState(242);
						((ExprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==RightShift || _la==LeftShift) ) {
							((ExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(243);
						expr(13);
						}
						break;
					case 4:
						{
						_localctx = new ExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(244);
						if (!(precpred(_ctx, 11))) throw new FailedPredicateException(this, "precpred(_ctx, 11)");
						setState(245);
						((ExprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << Greater) | (1L << Less) | (1L << GreaterEqual) | (1L << LessEqual))) != 0)) ) {
							((ExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(246);
						expr(12);
						}
						break;
					case 5:
						{
						_localctx = new ExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(247);
						if (!(precpred(_ctx, 10))) throw new FailedPredicateException(this, "precpred(_ctx, 10)");
						setState(248);
						((ExprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==UnEqual || _la==Equal) ) {
							((ExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(249);
						expr(11);
						}
						break;
					case 6:
						{
						_localctx = new ExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(250);
						if (!(precpred(_ctx, 9))) throw new FailedPredicateException(this, "precpred(_ctx, 9)");
						setState(251);
						((ExprContext)_localctx).op = match(And);
						setState(252);
						expr(10);
						}
						break;
					case 7:
						{
						_localctx = new ExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(253);
						if (!(precpred(_ctx, 8))) throw new FailedPredicateException(this, "precpred(_ctx, 8)");
						setState(254);
						((ExprContext)_localctx).op = match(Xor);
						setState(255);
						expr(9);
						}
						break;
					case 8:
						{
						_localctx = new ExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(256);
						if (!(precpred(_ctx, 7))) throw new FailedPredicateException(this, "precpred(_ctx, 7)");
						setState(257);
						((ExprContext)_localctx).op = match(Or);
						setState(258);
						expr(8);
						}
						break;
					case 9:
						{
						_localctx = new ExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(259);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(260);
						((ExprContext)_localctx).op = match(LogicAnd);
						setState(261);
						expr(7);
						}
						break;
					case 10:
						{
						_localctx = new ExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(262);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(263);
						((ExprContext)_localctx).op = match(LogicOr);
						setState(264);
						expr(6);
						}
						break;
					case 11:
						{
						_localctx = new ExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(265);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(266);
						match(Question);
						setState(267);
						expr(0);
						setState(268);
						match(Colon);
						setState(269);
						expr(4);
						}
						break;
					case 12:
						{
						_localctx = new ExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(271);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(272);
						((ExprContext)_localctx).op = match(Assign);
						setState(273);
						expr(3);
						}
						break;
					case 13:
						{
						_localctx = new ExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(274);
						if (!(precpred(_ctx, 20))) throw new FailedPredicateException(this, "precpred(_ctx, 20)");
						setState(275);
						match(LParen);
						setState(284);
						_errHandler.sync(this);
						_la = _input.LA(1);
						if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << New) | (1L << Null) | (1L << True) | (1L << False) | (1L << This) | (1L << Plus) | (1L << Minus) | (1L << LogicNot) | (1L << Not) | (1L << Increment) | (1L << Decrement) | (1L << Identifier) | (1L << LParen) | (1L << LBrace) | (1L << IntegerConst) | (1L << FstringConst) | (1L << Fstring_l) | (1L << StringConst))) != 0)) {
							{
							setState(276);
							expr(0);
							setState(281);
							_errHandler.sync(this);
							_la = _input.LA(1);
							while (_la==Comma) {
								{
								{
								setState(277);
								match(Comma);
								setState(278);
								expr(0);
								}
								}
								setState(283);
								_errHandler.sync(this);
								_la = _input.LA(1);
							}
							}
						}

						setState(286);
						match(RParen);
						}
						break;
					case 14:
						{
						_localctx = new ExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(287);
						if (!(precpred(_ctx, 19))) throw new FailedPredicateException(this, "precpred(_ctx, 19)");
						setState(288);
						match(LBracket);
						setState(289);
						expr(0);
						setState(290);
						match(RBracket);
						}
						break;
					case 15:
						{
						_localctx = new ExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(292);
						if (!(precpred(_ctx, 18))) throw new FailedPredicateException(this, "precpred(_ctx, 18)");
						setState(293);
						((ExprContext)_localctx).op = match(Member);
						setState(294);
						match(Identifier);
						}
						break;
					case 16:
						{
						_localctx = new ExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(295);
						if (!(precpred(_ctx, 17))) throw new FailedPredicateException(this, "precpred(_ctx, 17)");
						setState(296);
						((ExprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==Increment || _la==Decrement) ) {
							((ExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						}
						break;
					}
					} 
				}
				setState(301);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,28,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class FormatStrContext extends ParserRuleContext {
		public TerminalNode Fstring_l() { return getToken(MxParser.Fstring_l, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode Fstring_lst() { return getToken(MxParser.Fstring_lst, 0); }
		public List<TerminalNode> Fstring_m() { return getTokens(MxParser.Fstring_m); }
		public TerminalNode Fstring_m(int i) {
			return getToken(MxParser.Fstring_m, i);
		}
		public TerminalNode FstringConst() { return getToken(MxParser.FstringConst, 0); }
		public FormatStrContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_formatStr; }
	}

	public final FormatStrContext formatStr() throws RecognitionException {
		FormatStrContext _localctx = new FormatStrContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_formatStr);
		int _la;
		try {
			setState(314);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Fstring_l:
				enterOuterAlt(_localctx, 1);
				{
				{
				setState(302);
				match(Fstring_l);
				setState(303);
				expr(0);
				setState(308);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==Fstring_m) {
					{
					{
					setState(304);
					match(Fstring_m);
					setState(305);
					expr(0);
					}
					}
					setState(310);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(311);
				match(Fstring_lst);
				}
				}
				break;
			case FstringConst:
				enterOuterAlt(_localctx, 2);
				{
				setState(313);
				match(FstringConst);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 16:
			return expr_sempred((ExprContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean expr_sempred(ExprContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 14);
		case 1:
			return precpred(_ctx, 13);
		case 2:
			return precpred(_ctx, 12);
		case 3:
			return precpred(_ctx, 11);
		case 4:
			return precpred(_ctx, 10);
		case 5:
			return precpred(_ctx, 9);
		case 6:
			return precpred(_ctx, 8);
		case 7:
			return precpred(_ctx, 7);
		case 8:
			return precpred(_ctx, 6);
		case 9:
			return precpred(_ctx, 5);
		case 10:
			return precpred(_ctx, 4);
		case 11:
			return precpred(_ctx, 3);
		case 12:
			return precpred(_ctx, 20);
		case 13:
			return precpred(_ctx, 19);
		case 14:
			return precpred(_ctx, 18);
		case 15:
			return precpred(_ctx, 17);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3B\u013f\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\3\2\3\2\3\2\3\2\3\2\7\2,\n\2\f\2\16\2/\13\2\3\2\3\2\3\3\3\3"+
		"\3\3\3\3\3\3\3\3\3\3\3\3\7\3;\n\3\f\3\16\3>\13\3\3\3\3\3\3\3\3\4\3\4\3"+
		"\4\3\4\3\4\3\5\3\5\3\5\3\5\5\5L\n\5\3\5\3\5\3\5\3\6\3\6\3\6\3\6\3\6\3"+
		"\6\7\6W\n\6\f\6\16\6Z\13\6\3\7\3\7\3\7\3\7\7\7`\n\7\f\7\16\7c\13\7\3\b"+
		"\3\b\3\b\5\bh\n\b\3\t\3\t\5\tl\n\t\3\n\3\n\3\n\7\nq\n\n\f\n\16\nt\13\n"+
		"\3\13\3\13\5\13x\n\13\3\f\3\f\3\r\3\r\7\r~\n\r\f\r\16\r\u0081\13\r\3\r"+
		"\3\r\3\16\3\16\3\16\3\16\7\16\u0089\n\16\f\16\16\16\u008c\13\16\3\16\3"+
		"\16\5\16\u0090\n\16\3\16\3\16\3\17\3\17\3\17\3\17\3\17\3\17\3\17\5\17"+
		"\u009b\n\17\3\20\3\20\3\20\5\20\u00a0\n\20\3\21\3\21\3\21\3\21\3\21\3"+
		"\21\3\21\3\21\3\21\3\21\3\21\5\21\u00ad\n\21\3\21\3\21\3\21\3\21\3\21"+
		"\3\21\3\21\3\21\3\21\3\21\5\21\u00b9\n\21\3\21\3\21\3\21\3\21\3\21\3\21"+
		"\5\21\u00c1\n\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\5\21\u00cc"+
		"\n\21\3\22\3\22\3\22\3\22\3\22\5\22\u00d3\n\22\3\22\6\22\u00d6\n\22\r"+
		"\22\16\22\u00d7\3\22\5\22\u00db\n\22\3\22\3\22\3\22\3\22\5\22\u00e1\n"+
		"\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\5\22\u00ec\n\22\3\22"+
		"\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22"+
		"\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22"+
		"\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22"+
		"\3\22\7\22\u011a\n\22\f\22\16\22\u011d\13\22\5\22\u011f\n\22\3\22\3\22"+
		"\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\7\22\u012c\n\22\f\22\16"+
		"\22\u012f\13\22\3\23\3\23\3\23\3\23\7\23\u0135\n\23\f\23\16\23\u0138\13"+
		"\23\3\23\3\23\3\23\5\23\u013d\n\23\3\23\2\3\"\24\2\4\6\b\n\f\16\20\22"+
		"\24\26\30\32\34\36 \"$\2\n\3\2\4\6\5\2\24\25!!\'\'\3\2)*\3\2\26\30\3\2"+
		"\24\25\3\2\"#\3\2\31\34\3\2\35\36\2\u016d\2-\3\2\2\2\4\62\3\2\2\2\6B\3"+
		"\2\2\2\bG\3\2\2\2\nP\3\2\2\2\f[\3\2\2\2\16d\3\2\2\2\20k\3\2\2\2\22m\3"+
		"\2\2\2\24w\3\2\2\2\26y\3\2\2\2\30{\3\2\2\2\32\u0084\3\2\2\2\34\u009a\3"+
		"\2\2\2\36\u009f\3\2\2\2 \u00cb\3\2\2\2\"\u00eb\3\2\2\2$\u013c\3\2\2\2"+
		"&,\5\b\5\2\',\5\4\3\2()\5\f\7\2)*\7\65\2\2*,\3\2\2\2+&\3\2\2\2+\'\3\2"+
		"\2\2+(\3\2\2\2,/\3\2\2\2-+\3\2\2\2-.\3\2\2\2.\60\3\2\2\2/-\3\2\2\2\60"+
		"\61\7\2\2\3\61\3\3\2\2\2\62\63\7\b\2\2\63\64\7,\2\2\64<\7\61\2\2\65\66"+
		"\5\f\7\2\66\67\7\65\2\2\67;\3\2\2\28;\5\b\5\29;\5\6\4\2:\65\3\2\2\2:8"+
		"\3\2\2\2:9\3\2\2\2;>\3\2\2\2<:\3\2\2\2<=\3\2\2\2=?\3\2\2\2><\3\2\2\2?"+
		"@\7\62\2\2@A\7\65\2\2A\5\3\2\2\2BC\7,\2\2CD\7-\2\2DE\7.\2\2EF\5\30\r\2"+
		"F\7\3\2\2\2GH\5\20\t\2HI\7,\2\2IK\7-\2\2JL\5\n\6\2KJ\3\2\2\2KL\3\2\2\2"+
		"LM\3\2\2\2MN\7.\2\2NO\5\30\r\2O\t\3\2\2\2PQ\5\22\n\2QX\7,\2\2RS\7\66\2"+
		"\2ST\5\22\n\2TU\7,\2\2UW\3\2\2\2VR\3\2\2\2WZ\3\2\2\2XV\3\2\2\2XY\3\2\2"+
		"\2Y\13\3\2\2\2ZX\3\2\2\2[\\\5\22\n\2\\a\5\16\b\2]^\7\66\2\2^`\5\16\b\2"+
		"_]\3\2\2\2`c\3\2\2\2a_\3\2\2\2ab\3\2\2\2b\r\3\2\2\2ca\3\2\2\2dg\7,\2\2"+
		"ef\7(\2\2fh\5\"\22\2ge\3\2\2\2gh\3\2\2\2h\17\3\2\2\2il\7\3\2\2jl\5\22"+
		"\n\2ki\3\2\2\2kj\3\2\2\2l\21\3\2\2\2mr\5\24\13\2no\7/\2\2oq\7\60\2\2p"+
		"n\3\2\2\2qt\3\2\2\2rp\3\2\2\2rs\3\2\2\2s\23\3\2\2\2tr\3\2\2\2ux\5\26\f"+
		"\2vx\7,\2\2wu\3\2\2\2wv\3\2\2\2x\25\3\2\2\2yz\t\2\2\2z\27\3\2\2\2{\177"+
		"\7\61\2\2|~\5 \21\2}|\3\2\2\2~\u0081\3\2\2\2\177}\3\2\2\2\177\u0080\3"+
		"\2\2\2\u0080\u0082\3\2\2\2\u0081\177\3\2\2\2\u0082\u0083\7\62\2\2\u0083"+
		"\31\3\2\2\2\u0084\u008f\7\61\2\2\u0085\u0086\5\34\17\2\u0086\u0087\7\66"+
		"\2\2\u0087\u0089\3\2\2\2\u0088\u0085\3\2\2\2\u0089\u008c\3\2\2\2\u008a"+
		"\u0088\3\2\2\2\u008a\u008b\3\2\2\2\u008b\u008d\3\2\2\2\u008c\u008a\3\2"+
		"\2\2\u008d\u0090\5\34\17\2\u008e\u0090\3\2\2\2\u008f\u008a\3\2\2\2\u008f"+
		"\u008e\3\2\2\2\u0090\u0091\3\2\2\2\u0091\u0092\7\62\2\2\u0092\33\3\2\2"+
		"\2\u0093\u009b\7\n\2\2\u0094\u009b\7\13\2\2\u0095\u009b\79\2\2\u0096\u009b"+
		"\7=\2\2\u0097\u009b\7\t\2\2\u0098\u009b\5\32\16\2\u0099\u009b\5$\23\2"+
		"\u009a\u0093\3\2\2\2\u009a\u0094\3\2\2\2\u009a\u0095\3\2\2\2\u009a\u0096"+
		"\3\2\2\2\u009a\u0097\3\2\2\2\u009a\u0098\3\2\2\2\u009a\u0099\3\2\2\2\u009b"+
		"\35\3\2\2\2\u009c\u00a0\7,\2\2\u009d\u00a0\5\34\17\2\u009e\u00a0\7\f\2"+
		"\2\u009f\u009c\3\2\2\2\u009f\u009d\3\2\2\2\u009f\u009e\3\2\2\2\u00a0\37"+
		"\3\2\2\2\u00a1\u00cc\5\30\r\2\u00a2\u00a3\5\f\7\2\u00a3\u00a4\7\65\2\2"+
		"\u00a4\u00cc\3\2\2\2\u00a5\u00a6\7\r\2\2\u00a6\u00a7\7-\2\2\u00a7\u00a8"+
		"\5\"\22\2\u00a8\u00a9\7.\2\2\u00a9\u00ac\5 \21\2\u00aa\u00ab\7\16\2\2"+
		"\u00ab\u00ad\5 \21\2\u00ac\u00aa\3\2\2\2\u00ac\u00ad\3\2\2\2\u00ad\u00cc"+
		"\3\2\2\2\u00ae\u00af\7\20\2\2\u00af\u00b0\7-\2\2\u00b0\u00b1\5\"\22\2"+
		"\u00b1\u00b2\7.\2\2\u00b2\u00b3\5 \21\2\u00b3\u00cc\3\2\2\2\u00b4\u00b5"+
		"\7\17\2\2\u00b5\u00b6\7-\2\2\u00b6\u00b8\5 \21\2\u00b7\u00b9\5\"\22\2"+
		"\u00b8\u00b7\3\2\2\2\u00b8\u00b9\3\2\2\2\u00b9\u00ba\3\2\2\2\u00ba\u00bb"+
		"\7\65\2\2\u00bb\u00bc\5\"\22\2\u00bc\u00bd\7.\2\2\u00bd\u00cc\3\2\2\2"+
		"\u00be\u00c0\7\23\2\2\u00bf\u00c1\5\"\22\2\u00c0\u00bf\3\2\2\2\u00c0\u00c1"+
		"\3\2\2\2\u00c1\u00c2\3\2\2\2\u00c2\u00cc\7\65\2\2\u00c3\u00c4\7\21\2\2"+
		"\u00c4\u00cc\7\65\2\2\u00c5\u00c6\7\22\2\2\u00c6\u00cc\7\65\2\2\u00c7"+
		"\u00c8\5\"\22\2\u00c8\u00c9\7\65\2\2\u00c9\u00cc\3\2\2\2\u00ca\u00cc\7"+
		"\65\2\2\u00cb\u00a1\3\2\2\2\u00cb\u00a2\3\2\2\2\u00cb\u00a5\3\2\2\2\u00cb"+
		"\u00ae\3\2\2\2\u00cb\u00b4\3\2\2\2\u00cb\u00be\3\2\2\2\u00cb\u00c3\3\2"+
		"\2\2\u00cb\u00c5\3\2\2\2\u00cb\u00c7\3\2\2\2\u00cb\u00ca\3\2\2\2\u00cc"+
		"!\3\2\2\2\u00cd\u00ce\b\22\1\2\u00ce\u00cf\7\7\2\2\u00cf\u00d5\5\24\13"+
		"\2\u00d0\u00d2\7/\2\2\u00d1\u00d3\5\"\22\2\u00d2\u00d1\3\2\2\2\u00d2\u00d3"+
		"\3\2\2\2\u00d3\u00d4\3\2\2\2\u00d4\u00d6\7\60\2\2\u00d5\u00d0\3\2\2\2"+
		"\u00d6\u00d7\3\2\2\2\u00d7\u00d5\3\2\2\2\u00d7\u00d8\3\2\2\2\u00d8\u00da"+
		"\3\2\2\2\u00d9\u00db\5\32\16\2\u00da\u00d9\3\2\2\2\u00da\u00db\3\2\2\2"+
		"\u00db\u00ec\3\2\2\2\u00dc\u00dd\7\7\2\2\u00dd\u00e0\5\24\13\2\u00de\u00df"+
		"\7-\2\2\u00df\u00e1\7.\2\2\u00e0\u00de\3\2\2\2\u00e0\u00e1\3\2\2\2\u00e1"+
		"\u00ec\3\2\2\2\u00e2\u00e3\t\3\2\2\u00e3\u00ec\5\"\22\22\u00e4\u00e5\t"+
		"\4\2\2\u00e5\u00ec\5\"\22\21\u00e6\u00e7\7-\2\2\u00e7\u00e8\5\"\22\2\u00e8"+
		"\u00e9\7.\2\2\u00e9\u00ec\3\2\2\2\u00ea\u00ec\5\36\20\2\u00eb\u00cd\3"+
		"\2\2\2\u00eb\u00dc\3\2\2\2\u00eb\u00e2\3\2\2\2\u00eb\u00e4\3\2\2\2\u00eb"+
		"\u00e6\3\2\2\2\u00eb\u00ea\3\2\2\2\u00ec\u012d\3\2\2\2\u00ed\u00ee\f\20"+
		"\2\2\u00ee\u00ef\t\5\2\2\u00ef\u012c\5\"\22\21\u00f0\u00f1\f\17\2\2\u00f1"+
		"\u00f2\t\6\2\2\u00f2\u012c\5\"\22\20\u00f3\u00f4\f\16\2\2\u00f4\u00f5"+
		"\t\7\2\2\u00f5\u012c\5\"\22\17\u00f6\u00f7\f\r\2\2\u00f7\u00f8\t\b\2\2"+
		"\u00f8\u012c\5\"\22\16\u00f9\u00fa\f\f\2\2\u00fa\u00fb\t\t\2\2\u00fb\u012c"+
		"\5\"\22\r\u00fc\u00fd\f\13\2\2\u00fd\u00fe\7$\2\2\u00fe\u012c\5\"\22\f"+
		"\u00ff\u0100\f\n\2\2\u0100\u0101\7&\2\2\u0101\u012c\5\"\22\13\u0102\u0103"+
		"\f\t\2\2\u0103\u0104\7%\2\2\u0104\u012c\5\"\22\n\u0105\u0106\f\b\2\2\u0106"+
		"\u0107\7\37\2\2\u0107\u012c\5\"\22\t\u0108\u0109\f\7\2\2\u0109\u010a\7"+
		" \2\2\u010a\u012c\5\"\22\b\u010b\u010c\f\6\2\2\u010c\u010d\7\63\2\2\u010d"+
		"\u010e\5\"\22\2\u010e\u010f\7\64\2\2\u010f\u0110\5\"\22\6\u0110\u012c"+
		"\3\2\2\2\u0111\u0112\f\5\2\2\u0112\u0113\7(\2\2\u0113\u012c\5\"\22\5\u0114"+
		"\u0115\f\26\2\2\u0115\u011e\7-\2\2\u0116\u011b\5\"\22\2\u0117\u0118\7"+
		"\66\2\2\u0118\u011a\5\"\22\2\u0119\u0117\3\2\2\2\u011a\u011d\3\2\2\2\u011b"+
		"\u0119\3\2\2\2\u011b\u011c\3\2\2\2\u011c\u011f\3\2\2\2\u011d\u011b\3\2"+
		"\2\2\u011e\u0116\3\2\2\2\u011e\u011f\3\2\2\2\u011f\u0120\3\2\2\2\u0120"+
		"\u012c\7.\2\2\u0121\u0122\f\25\2\2\u0122\u0123\7/\2\2\u0123\u0124\5\""+
		"\22\2\u0124\u0125\7\60\2\2\u0125\u012c\3\2\2\2\u0126\u0127\f\24\2\2\u0127"+
		"\u0128\7+\2\2\u0128\u012c\7,\2\2\u0129\u012a\f\23\2\2\u012a\u012c\t\4"+
		"\2\2\u012b\u00ed\3\2\2\2\u012b\u00f0\3\2\2\2\u012b\u00f3\3\2\2\2\u012b"+
		"\u00f6\3\2\2\2\u012b\u00f9\3\2\2\2\u012b\u00fc\3\2\2\2\u012b\u00ff\3\2"+
		"\2\2\u012b\u0102\3\2\2\2\u012b\u0105\3\2\2\2\u012b\u0108\3\2\2\2\u012b"+
		"\u010b\3\2\2\2\u012b\u0111\3\2\2\2\u012b\u0114\3\2\2\2\u012b\u0121\3\2"+
		"\2\2\u012b\u0126\3\2\2\2\u012b\u0129\3\2\2\2\u012c\u012f\3\2\2\2\u012d"+
		"\u012b\3\2\2\2\u012d\u012e\3\2\2\2\u012e#\3\2\2\2\u012f\u012d\3\2\2\2"+
		"\u0130\u0131\7;\2\2\u0131\u0136\5\"\22\2\u0132\u0133\7<\2\2\u0133\u0135"+
		"\5\"\22\2\u0134\u0132\3\2\2\2\u0135\u0138\3\2\2\2\u0136\u0134\3\2\2\2"+
		"\u0136\u0137\3\2\2\2\u0137\u0139\3\2\2\2\u0138\u0136\3\2\2\2\u0139\u013a"+
		"\7>\2\2\u013a\u013d\3\2\2\2\u013b\u013d\7:\2\2\u013c\u0130\3\2\2\2\u013c"+
		"\u013b\3\2\2\2\u013d%\3\2\2\2!+-:<KXagkrw\177\u008a\u008f\u009a\u009f"+
		"\u00ac\u00b8\u00c0\u00cb\u00d2\u00d7\u00da\u00e0\u00eb\u011b\u011e\u012b"+
		"\u012d\u0136\u013c";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}