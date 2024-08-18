// Generated from /home/unix/github/Compiler-Design-Implementation/antlr/Mx.g4 by ANTLR 4.9.2
package juhuh.compiler.parser;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class MxLexer extends Lexer {
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
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"Void", "Bool", "Int", "String", "New", "Class", "Null", "True", "False", 
			"This", "If", "Else", "For", "While", "Break", "Continue", "Return", 
			"Plus", "Minus", "Mul", "Div", "Mod", "Greater", "Less", "GreaterEqual", 
			"LessEqual", "UnEqual", "Equal", "LogicAnd", "LogicOr", "LogicNot", "RightShift", 
			"LeftShift", "And", "Or", "Xor", "Not", "Assign", "Increment", "Decrement", 
			"Member", "Identifier", "LParen", "RParen", "LBracket", "RBracket", "LBrace", 
			"RBrace", "Question", "Colon", "Semi", "Comma", "Quote", "Escape", "IntegerConst", 
			"FstringConst", "Fstring_l", "Fstring_m", "StringConst", "Fstring_lst", 
			"WhiteSpace", "LineComment", "ParaComment", "Non_s"
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


	public MxLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Mx.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2B\u01a4\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64\t"+
		"\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\4:\t:\4;\t;\4<\t<\4=\t="+
		"\4>\t>\4?\t?\4@\t@\4A\tA\3\2\3\2\3\2\3\2\3\2\3\3\3\3\3\3\3\3\3\3\3\4\3"+
		"\4\3\4\3\4\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\6\3\6\3\6\3\6\3\7\3\7\3\7\3\7"+
		"\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\n\3\n\3\n\3\n\3\n\3"+
		"\n\3\13\3\13\3\13\3\13\3\13\3\f\3\f\3\f\3\r\3\r\3\r\3\r\3\r\3\16\3\16"+
		"\3\16\3\16\3\17\3\17\3\17\3\17\3\17\3\17\3\20\3\20\3\20\3\20\3\20\3\20"+
		"\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\22\3\22\3\22\3\22\3\22"+
		"\3\22\3\22\3\23\3\23\3\24\3\24\3\25\3\25\3\26\3\26\3\27\3\27\3\30\3\30"+
		"\3\31\3\31\3\32\3\32\3\32\3\33\3\33\3\33\3\34\3\34\3\34\3\35\3\35\3\35"+
		"\3\36\3\36\3\36\3\37\3\37\3\37\3 \3 \3!\3!\3!\3\"\3\"\3\"\3#\3#\3$\3$"+
		"\3%\3%\3&\3&\3\'\3\'\3(\3(\3(\3)\3)\3)\3*\3*\3+\3+\7+\u011c\n+\f+\16+"+
		"\u011f\13+\3,\3,\3-\3-\3.\3.\3/\3/\3\60\3\60\3\61\3\61\3\62\3\62\3\63"+
		"\3\63\3\64\3\64\3\65\3\65\3\66\3\66\3\67\3\67\3\67\3\67\3\67\3\67\5\67"+
		"\u013d\n\67\38\38\38\78\u0142\n8\f8\168\u0145\138\58\u0147\n8\39\39\3"+
		"9\39\39\79\u014e\n9\f9\169\u0151\139\39\39\3:\3:\3:\3:\3:\7:\u015a\n:"+
		"\f:\16:\u015d\13:\3:\3:\3;\3;\3;\3;\7;\u0165\n;\f;\16;\u0168\13;\3;\3"+
		";\3<\3<\3<\7<\u016f\n<\f<\16<\u0172\13<\3<\3<\3=\3=\3=\3=\7=\u017a\n="+
		"\f=\16=\u017d\13=\3=\3=\3>\6>\u0182\n>\r>\16>\u0183\3>\3>\3?\3?\3?\3?"+
		"\7?\u018c\n?\f?\16?\u018f\13?\3?\3?\3@\3@\3@\3@\7@\u0197\n@\f@\16@\u019a"+
		"\13@\3@\3@\3@\3@\3@\3A\3A\5A\u01a3\nA\5\u014f\u0170\u0198\2B\3\3\5\4\7"+
		"\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20\37\21!\22"+
		"#\23%\24\'\25)\26+\27-\30/\31\61\32\63\33\65\34\67\359\36;\37= ?!A\"C"+
		"#E$G%I&K\'M(O)Q*S+U,W-Y.[/]\60_\61a\62c\63e\64g\65i\66k\67m8o9q:s;u<w"+
		"=y>{?}@\177A\u0081B\3\2\n\4\2C\\c|\6\2\62;C\\aac|\3\2\63;\3\2\62;\4\2"+
		"$$&&\5\2\13\f\17\17\"\"\4\2\f\f\17\17\3\2&&\2\u01b6\2\3\3\2\2\2\2\5\3"+
		"\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2"+
		"\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3"+
		"\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'"+
		"\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63"+
		"\3\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2\2\2\2;\3\2\2\2\2=\3\2\2\2\2"+
		"?\3\2\2\2\2A\3\2\2\2\2C\3\2\2\2\2E\3\2\2\2\2G\3\2\2\2\2I\3\2\2\2\2K\3"+
		"\2\2\2\2M\3\2\2\2\2O\3\2\2\2\2Q\3\2\2\2\2S\3\2\2\2\2U\3\2\2\2\2W\3\2\2"+
		"\2\2Y\3\2\2\2\2[\3\2\2\2\2]\3\2\2\2\2_\3\2\2\2\2a\3\2\2\2\2c\3\2\2\2\2"+
		"e\3\2\2\2\2g\3\2\2\2\2i\3\2\2\2\2k\3\2\2\2\2m\3\2\2\2\2o\3\2\2\2\2q\3"+
		"\2\2\2\2s\3\2\2\2\2u\3\2\2\2\2w\3\2\2\2\2y\3\2\2\2\2{\3\2\2\2\2}\3\2\2"+
		"\2\2\177\3\2\2\2\2\u0081\3\2\2\2\3\u0083\3\2\2\2\5\u0088\3\2\2\2\7\u008d"+
		"\3\2\2\2\t\u0091\3\2\2\2\13\u0098\3\2\2\2\r\u009c\3\2\2\2\17\u00a2\3\2"+
		"\2\2\21\u00a7\3\2\2\2\23\u00ac\3\2\2\2\25\u00b2\3\2\2\2\27\u00b7\3\2\2"+
		"\2\31\u00ba\3\2\2\2\33\u00bf\3\2\2\2\35\u00c3\3\2\2\2\37\u00c9\3\2\2\2"+
		"!\u00cf\3\2\2\2#\u00d8\3\2\2\2%\u00df\3\2\2\2\'\u00e1\3\2\2\2)\u00e3\3"+
		"\2\2\2+\u00e5\3\2\2\2-\u00e7\3\2\2\2/\u00e9\3\2\2\2\61\u00eb\3\2\2\2\63"+
		"\u00ed\3\2\2\2\65\u00f0\3\2\2\2\67\u00f3\3\2\2\29\u00f6\3\2\2\2;\u00f9"+
		"\3\2\2\2=\u00fc\3\2\2\2?\u00ff\3\2\2\2A\u0101\3\2\2\2C\u0104\3\2\2\2E"+
		"\u0107\3\2\2\2G\u0109\3\2\2\2I\u010b\3\2\2\2K\u010d\3\2\2\2M\u010f\3\2"+
		"\2\2O\u0111\3\2\2\2Q\u0114\3\2\2\2S\u0117\3\2\2\2U\u0119\3\2\2\2W\u0120"+
		"\3\2\2\2Y\u0122\3\2\2\2[\u0124\3\2\2\2]\u0126\3\2\2\2_\u0128\3\2\2\2a"+
		"\u012a\3\2\2\2c\u012c\3\2\2\2e\u012e\3\2\2\2g\u0130\3\2\2\2i\u0132\3\2"+
		"\2\2k\u0134\3\2\2\2m\u013c\3\2\2\2o\u0146\3\2\2\2q\u0148\3\2\2\2s\u0154"+
		"\3\2\2\2u\u0160\3\2\2\2w\u016b\3\2\2\2y\u0175\3\2\2\2{\u0181\3\2\2\2}"+
		"\u0187\3\2\2\2\177\u0192\3\2\2\2\u0081\u01a2\3\2\2\2\u0083\u0084\7x\2"+
		"\2\u0084\u0085\7q\2\2\u0085\u0086\7k\2\2\u0086\u0087\7f\2\2\u0087\4\3"+
		"\2\2\2\u0088\u0089\7d\2\2\u0089\u008a\7q\2\2\u008a\u008b\7q\2\2\u008b"+
		"\u008c\7n\2\2\u008c\6\3\2\2\2\u008d\u008e\7k\2\2\u008e\u008f\7p\2\2\u008f"+
		"\u0090\7v\2\2\u0090\b\3\2\2\2\u0091\u0092\7u\2\2\u0092\u0093\7v\2\2\u0093"+
		"\u0094\7t\2\2\u0094\u0095\7k\2\2\u0095\u0096\7p\2\2\u0096\u0097\7i\2\2"+
		"\u0097\n\3\2\2\2\u0098\u0099\7p\2\2\u0099\u009a\7g\2\2\u009a\u009b\7y"+
		"\2\2\u009b\f\3\2\2\2\u009c\u009d\7e\2\2\u009d\u009e\7n\2\2\u009e\u009f"+
		"\7c\2\2\u009f\u00a0\7u\2\2\u00a0\u00a1\7u\2\2\u00a1\16\3\2\2\2\u00a2\u00a3"+
		"\7p\2\2\u00a3\u00a4\7w\2\2\u00a4\u00a5\7n\2\2\u00a5\u00a6\7n\2\2\u00a6"+
		"\20\3\2\2\2\u00a7\u00a8\7v\2\2\u00a8\u00a9\7t\2\2\u00a9\u00aa\7w\2\2\u00aa"+
		"\u00ab\7g\2\2\u00ab\22\3\2\2\2\u00ac\u00ad\7h\2\2\u00ad\u00ae\7c\2\2\u00ae"+
		"\u00af\7n\2\2\u00af\u00b0\7u\2\2\u00b0\u00b1\7g\2\2\u00b1\24\3\2\2\2\u00b2"+
		"\u00b3\7v\2\2\u00b3\u00b4\7j\2\2\u00b4\u00b5\7k\2\2\u00b5\u00b6\7u\2\2"+
		"\u00b6\26\3\2\2\2\u00b7\u00b8\7k\2\2\u00b8\u00b9\7h\2\2\u00b9\30\3\2\2"+
		"\2\u00ba\u00bb\7g\2\2\u00bb\u00bc\7n\2\2\u00bc\u00bd\7u\2\2\u00bd\u00be"+
		"\7g\2\2\u00be\32\3\2\2\2\u00bf\u00c0\7h\2\2\u00c0\u00c1\7q\2\2\u00c1\u00c2"+
		"\7t\2\2\u00c2\34\3\2\2\2\u00c3\u00c4\7y\2\2\u00c4\u00c5\7j\2\2\u00c5\u00c6"+
		"\7k\2\2\u00c6\u00c7\7n\2\2\u00c7\u00c8\7g\2\2\u00c8\36\3\2\2\2\u00c9\u00ca"+
		"\7d\2\2\u00ca\u00cb\7t\2\2\u00cb\u00cc\7g\2\2\u00cc\u00cd\7c\2\2\u00cd"+
		"\u00ce\7m\2\2\u00ce \3\2\2\2\u00cf\u00d0\7e\2\2\u00d0\u00d1\7q\2\2\u00d1"+
		"\u00d2\7p\2\2\u00d2\u00d3\7v\2\2\u00d3\u00d4\7k\2\2\u00d4\u00d5\7p\2\2"+
		"\u00d5\u00d6\7w\2\2\u00d6\u00d7\7g\2\2\u00d7\"\3\2\2\2\u00d8\u00d9\7t"+
		"\2\2\u00d9\u00da\7g\2\2\u00da\u00db\7v\2\2\u00db\u00dc\7w\2\2\u00dc\u00dd"+
		"\7t\2\2\u00dd\u00de\7p\2\2\u00de$\3\2\2\2\u00df\u00e0\7-\2\2\u00e0&\3"+
		"\2\2\2\u00e1\u00e2\7/\2\2\u00e2(\3\2\2\2\u00e3\u00e4\7,\2\2\u00e4*\3\2"+
		"\2\2\u00e5\u00e6\7\61\2\2\u00e6,\3\2\2\2\u00e7\u00e8\7\'\2\2\u00e8.\3"+
		"\2\2\2\u00e9\u00ea\7@\2\2\u00ea\60\3\2\2\2\u00eb\u00ec\7>\2\2\u00ec\62"+
		"\3\2\2\2\u00ed\u00ee\7@\2\2\u00ee\u00ef\7?\2\2\u00ef\64\3\2\2\2\u00f0"+
		"\u00f1\7>\2\2\u00f1\u00f2\7?\2\2\u00f2\66\3\2\2\2\u00f3\u00f4\7#\2\2\u00f4"+
		"\u00f5\7?\2\2\u00f58\3\2\2\2\u00f6\u00f7\7?\2\2\u00f7\u00f8\7?\2\2\u00f8"+
		":\3\2\2\2\u00f9\u00fa\7(\2\2\u00fa\u00fb\7(\2\2\u00fb<\3\2\2\2\u00fc\u00fd"+
		"\7~\2\2\u00fd\u00fe\7~\2\2\u00fe>\3\2\2\2\u00ff\u0100\7#\2\2\u0100@\3"+
		"\2\2\2\u0101\u0102\7@\2\2\u0102\u0103\7@\2\2\u0103B\3\2\2\2\u0104\u0105"+
		"\7>\2\2\u0105\u0106\7>\2\2\u0106D\3\2\2\2\u0107\u0108\7(\2\2\u0108F\3"+
		"\2\2\2\u0109\u010a\7~\2\2\u010aH\3\2\2\2\u010b\u010c\7`\2\2\u010cJ\3\2"+
		"\2\2\u010d\u010e\7\u0080\2\2\u010eL\3\2\2\2\u010f\u0110\7?\2\2\u0110N"+
		"\3\2\2\2\u0111\u0112\7-\2\2\u0112\u0113\7-\2\2\u0113P\3\2\2\2\u0114\u0115"+
		"\7/\2\2\u0115\u0116\7/\2\2\u0116R\3\2\2\2\u0117\u0118\7\60\2\2\u0118T"+
		"\3\2\2\2\u0119\u011d\t\2\2\2\u011a\u011c\t\3\2\2\u011b\u011a\3\2\2\2\u011c"+
		"\u011f\3\2\2\2\u011d\u011b\3\2\2\2\u011d\u011e\3\2\2\2\u011eV\3\2\2\2"+
		"\u011f\u011d\3\2\2\2\u0120\u0121\7*\2\2\u0121X\3\2\2\2\u0122\u0123\7+"+
		"\2\2\u0123Z\3\2\2\2\u0124\u0125\7]\2\2\u0125\\\3\2\2\2\u0126\u0127\7_"+
		"\2\2\u0127^\3\2\2\2\u0128\u0129\7}\2\2\u0129`\3\2\2\2\u012a\u012b\7\177"+
		"\2\2\u012bb\3\2\2\2\u012c\u012d\7A\2\2\u012dd\3\2\2\2\u012e\u012f\7<\2"+
		"\2\u012ff\3\2\2\2\u0130\u0131\7=\2\2\u0131h\3\2\2\2\u0132\u0133\7.\2\2"+
		"\u0133j\3\2\2\2\u0134\u0135\7$\2\2\u0135l\3\2\2\2\u0136\u0137\7^\2\2\u0137"+
		"\u013d\7^\2\2\u0138\u0139\7^\2\2\u0139\u013d\7p\2\2\u013a\u013b\7^\2\2"+
		"\u013b\u013d\7$\2\2\u013c\u0136\3\2\2\2\u013c\u0138\3\2\2\2\u013c\u013a"+
		"\3\2\2\2\u013dn\3\2\2\2\u013e\u0147\7\62\2\2\u013f\u0143\t\4\2\2\u0140"+
		"\u0142\t\5\2\2\u0141\u0140\3\2\2\2\u0142\u0145\3\2\2\2\u0143\u0141\3\2"+
		"\2\2\u0143\u0144\3\2\2\2\u0144\u0147\3\2\2\2\u0145\u0143\3\2\2\2\u0146"+
		"\u013e\3\2\2\2\u0146\u013f\3\2\2\2\u0147p\3\2\2\2\u0148\u0149\7h\2\2\u0149"+
		"\u014f\5k\66\2\u014a\u014e\n\6\2\2\u014b\u014c\7&\2\2\u014c\u014e\7&\2"+
		"\2\u014d\u014a\3\2\2\2\u014d\u014b\3\2\2\2\u014e\u0151\3\2\2\2\u014f\u0150"+
		"\3\2\2\2\u014f\u014d\3\2\2\2\u0150\u0152\3\2\2\2\u0151\u014f\3\2\2\2\u0152"+
		"\u0153\5k\66\2\u0153r\3\2\2\2\u0154\u0155\7h\2\2\u0155\u015b\5k\66\2\u0156"+
		"\u015a\n\6\2\2\u0157\u0158\7&\2\2\u0158\u015a\7&\2\2\u0159\u0156\3\2\2"+
		"\2\u0159\u0157\3\2\2\2\u015a\u015d\3\2\2\2\u015b\u0159\3\2\2\2\u015b\u015c"+
		"\3\2\2\2\u015c\u015e\3\2\2\2\u015d\u015b\3\2\2\2\u015e\u015f\7&\2\2\u015f"+
		"t\3\2\2\2\u0160\u0166\7&\2\2\u0161\u0165\n\6\2\2\u0162\u0163\7&\2\2\u0163"+
		"\u0165\7&\2\2\u0164\u0161\3\2\2\2\u0164\u0162\3\2\2\2\u0165\u0168\3\2"+
		"\2\2\u0166\u0164\3\2\2\2\u0166\u0167\3\2\2\2\u0167\u0169\3\2\2\2\u0168"+
		"\u0166\3\2\2\2\u0169\u016a\7&\2\2\u016av\3\2\2\2\u016b\u0170\5k\66\2\u016c"+
		"\u016f\5m\67\2\u016d\u016f\13\2\2\2\u016e\u016c\3\2\2\2\u016e\u016d\3"+
		"\2\2\2\u016f\u0172\3\2\2\2\u0170\u0171\3\2\2\2\u0170\u016e\3\2\2\2\u0171"+
		"\u0173\3\2\2\2\u0172\u0170\3\2\2\2\u0173\u0174\5k\66\2\u0174x\3\2\2\2"+
		"\u0175\u017b\7&\2\2\u0176\u017a\n\6\2\2\u0177\u0178\7&\2\2\u0178\u017a"+
		"\7&\2\2\u0179\u0176\3\2\2\2\u0179\u0177\3\2\2\2\u017a\u017d\3\2\2\2\u017b"+
		"\u0179\3\2\2\2\u017b\u017c\3\2\2\2\u017c\u017e\3\2\2\2\u017d\u017b\3\2"+
		"\2\2\u017e\u017f\5k\66\2\u017fz\3\2\2\2\u0180\u0182\t\7\2\2\u0181\u0180"+
		"\3\2\2\2\u0182\u0183\3\2\2\2\u0183\u0181\3\2\2\2\u0183\u0184\3\2\2\2\u0184"+
		"\u0185\3\2\2\2\u0185\u0186\b>\2\2\u0186|\3\2\2\2\u0187\u0188\7\61\2\2"+
		"\u0188\u0189\7\61\2\2\u0189\u018d\3\2\2\2\u018a\u018c\n\b\2\2\u018b\u018a"+
		"\3\2\2\2\u018c\u018f\3\2\2\2\u018d\u018b\3\2\2\2\u018d\u018e\3\2\2\2\u018e"+
		"\u0190\3\2\2\2\u018f\u018d\3\2\2\2\u0190\u0191\b?\2\2\u0191~\3\2\2\2\u0192"+
		"\u0193\7\61\2\2\u0193\u0194\7,\2\2\u0194\u0198\3\2\2\2\u0195\u0197\13"+
		"\2\2\2\u0196\u0195\3\2\2\2\u0197\u019a\3\2\2\2\u0198\u0199\3\2\2\2\u0198"+
		"\u0196\3\2\2\2\u0199\u019b\3\2\2\2\u019a\u0198\3\2\2\2\u019b\u019c\7,"+
		"\2\2\u019c\u019d\7\61\2\2\u019d\u019e\3\2\2\2\u019e\u019f\b@\2\2\u019f"+
		"\u0080\3\2\2\2\u01a0\u01a3\n\t\2\2\u01a1\u01a3\5m\67\2\u01a2\u01a0\3\2"+
		"\2\2\u01a2\u01a1\3\2\2\2\u01a3\u0082\3\2\2\2\25\2\u011d\u013c\u0143\u0146"+
		"\u014d\u014f\u0159\u015b\u0164\u0166\u016e\u0170\u0179\u017b\u0183\u018d"+
		"\u0198\u01a2\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}