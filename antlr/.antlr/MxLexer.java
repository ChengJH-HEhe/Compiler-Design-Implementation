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
		LineComment=62, ParaComment=63;
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
			"WhiteSpace", "LineComment", "ParaComment"
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
			"WhiteSpace", "LineComment", "ParaComment"
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2A\u019e\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64\t"+
		"\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\4:\t:\4;\t;\4<\t<\4=\t="+
		"\4>\t>\4?\t?\4@\t@\3\2\3\2\3\2\3\2\3\2\3\3\3\3\3\3\3\3\3\3\3\4\3\4\3\4"+
		"\3\4\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\6\3\6\3\6\3\6\3\7\3\7\3\7\3\7\3\7\3"+
		"\7\3\b\3\b\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\n\3\n\3\n\3\n\3\n\3\n\3\13"+
		"\3\13\3\13\3\13\3\13\3\f\3\f\3\f\3\r\3\r\3\r\3\r\3\r\3\16\3\16\3\16\3"+
		"\16\3\17\3\17\3\17\3\17\3\17\3\17\3\20\3\20\3\20\3\20\3\20\3\20\3\21\3"+
		"\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\22\3\22\3\22\3\22\3\22\3\22\3"+
		"\22\3\23\3\23\3\24\3\24\3\25\3\25\3\26\3\26\3\27\3\27\3\30\3\30\3\31\3"+
		"\31\3\32\3\32\3\32\3\33\3\33\3\33\3\34\3\34\3\34\3\35\3\35\3\35\3\36\3"+
		"\36\3\36\3\37\3\37\3\37\3 \3 \3!\3!\3!\3\"\3\"\3\"\3#\3#\3$\3$\3%\3%\3"+
		"&\3&\3\'\3\'\3(\3(\3(\3)\3)\3)\3*\3*\3+\3+\7+\u011a\n+\f+\16+\u011d\13"+
		"+\3,\3,\3-\3-\3.\3.\3/\3/\3\60\3\60\3\61\3\61\3\62\3\62\3\63\3\63\3\64"+
		"\3\64\3\65\3\65\3\66\3\66\3\67\3\67\3\67\3\67\3\67\3\67\5\67\u013b\n\67"+
		"\38\38\38\78\u0140\n8\f8\168\u0143\138\58\u0145\n8\39\39\39\39\39\79\u014c"+
		"\n9\f9\169\u014f\139\39\39\3:\3:\3:\3:\3:\7:\u0158\n:\f:\16:\u015b\13"+
		":\3:\3:\3;\3;\3;\3;\7;\u0163\n;\f;\16;\u0166\13;\3;\3;\3<\3<\3<\7<\u016d"+
		"\n<\f<\16<\u0170\13<\3<\3<\3=\3=\3=\3=\7=\u0178\n=\f=\16=\u017b\13=\3"+
		"=\3=\3>\6>\u0180\n>\r>\16>\u0181\3>\3>\3?\3?\3?\3?\7?\u018a\n?\f?\16?"+
		"\u018d\13?\3?\3?\3@\3@\3@\3@\7@\u0195\n@\f@\16@\u0198\13@\3@\3@\3@\3@"+
		"\3@\5\u014d\u016e\u0196\2A\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25"+
		"\f\27\r\31\16\33\17\35\20\37\21!\22#\23%\24\'\25)\26+\27-\30/\31\61\32"+
		"\63\33\65\34\67\359\36;\37= ?!A\"C#E$G%I&K\'M(O)Q*S+U,W-Y.[/]\60_\61a"+
		"\62c\63e\64g\65i\66k\67m8o9q:s;u<w=y>{?}@\177A\3\2\t\4\2C\\c|\6\2\62;"+
		"C\\aac|\3\2\63;\3\2\62;\4\2$$&&\5\2\13\f\17\17\"\"\4\2\f\f\17\17\2\u01af"+
		"\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2"+
		"\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2"+
		"\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2"+
		"\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2"+
		"\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2\2\2\2;\3"+
		"\2\2\2\2=\3\2\2\2\2?\3\2\2\2\2A\3\2\2\2\2C\3\2\2\2\2E\3\2\2\2\2G\3\2\2"+
		"\2\2I\3\2\2\2\2K\3\2\2\2\2M\3\2\2\2\2O\3\2\2\2\2Q\3\2\2\2\2S\3\2\2\2\2"+
		"U\3\2\2\2\2W\3\2\2\2\2Y\3\2\2\2\2[\3\2\2\2\2]\3\2\2\2\2_\3\2\2\2\2a\3"+
		"\2\2\2\2c\3\2\2\2\2e\3\2\2\2\2g\3\2\2\2\2i\3\2\2\2\2k\3\2\2\2\2m\3\2\2"+
		"\2\2o\3\2\2\2\2q\3\2\2\2\2s\3\2\2\2\2u\3\2\2\2\2w\3\2\2\2\2y\3\2\2\2\2"+
		"{\3\2\2\2\2}\3\2\2\2\2\177\3\2\2\2\3\u0081\3\2\2\2\5\u0086\3\2\2\2\7\u008b"+
		"\3\2\2\2\t\u008f\3\2\2\2\13\u0096\3\2\2\2\r\u009a\3\2\2\2\17\u00a0\3\2"+
		"\2\2\21\u00a5\3\2\2\2\23\u00aa\3\2\2\2\25\u00b0\3\2\2\2\27\u00b5\3\2\2"+
		"\2\31\u00b8\3\2\2\2\33\u00bd\3\2\2\2\35\u00c1\3\2\2\2\37\u00c7\3\2\2\2"+
		"!\u00cd\3\2\2\2#\u00d6\3\2\2\2%\u00dd\3\2\2\2\'\u00df\3\2\2\2)\u00e1\3"+
		"\2\2\2+\u00e3\3\2\2\2-\u00e5\3\2\2\2/\u00e7\3\2\2\2\61\u00e9\3\2\2\2\63"+
		"\u00eb\3\2\2\2\65\u00ee\3\2\2\2\67\u00f1\3\2\2\29\u00f4\3\2\2\2;\u00f7"+
		"\3\2\2\2=\u00fa\3\2\2\2?\u00fd\3\2\2\2A\u00ff\3\2\2\2C\u0102\3\2\2\2E"+
		"\u0105\3\2\2\2G\u0107\3\2\2\2I\u0109\3\2\2\2K\u010b\3\2\2\2M\u010d\3\2"+
		"\2\2O\u010f\3\2\2\2Q\u0112\3\2\2\2S\u0115\3\2\2\2U\u0117\3\2\2\2W\u011e"+
		"\3\2\2\2Y\u0120\3\2\2\2[\u0122\3\2\2\2]\u0124\3\2\2\2_\u0126\3\2\2\2a"+
		"\u0128\3\2\2\2c\u012a\3\2\2\2e\u012c\3\2\2\2g\u012e\3\2\2\2i\u0130\3\2"+
		"\2\2k\u0132\3\2\2\2m\u013a\3\2\2\2o\u0144\3\2\2\2q\u0146\3\2\2\2s\u0152"+
		"\3\2\2\2u\u015e\3\2\2\2w\u0169\3\2\2\2y\u0173\3\2\2\2{\u017f\3\2\2\2}"+
		"\u0185\3\2\2\2\177\u0190\3\2\2\2\u0081\u0082\7x\2\2\u0082\u0083\7q\2\2"+
		"\u0083\u0084\7k\2\2\u0084\u0085\7f\2\2\u0085\4\3\2\2\2\u0086\u0087\7d"+
		"\2\2\u0087\u0088\7q\2\2\u0088\u0089\7q\2\2\u0089\u008a\7n\2\2\u008a\6"+
		"\3\2\2\2\u008b\u008c\7k\2\2\u008c\u008d\7p\2\2\u008d\u008e\7v\2\2\u008e"+
		"\b\3\2\2\2\u008f\u0090\7u\2\2\u0090\u0091\7v\2\2\u0091\u0092\7t\2\2\u0092"+
		"\u0093\7k\2\2\u0093\u0094\7p\2\2\u0094\u0095\7i\2\2\u0095\n\3\2\2\2\u0096"+
		"\u0097\7p\2\2\u0097\u0098\7g\2\2\u0098\u0099\7y\2\2\u0099\f\3\2\2\2\u009a"+
		"\u009b\7e\2\2\u009b\u009c\7n\2\2\u009c\u009d\7c\2\2\u009d\u009e\7u\2\2"+
		"\u009e\u009f\7u\2\2\u009f\16\3\2\2\2\u00a0\u00a1\7p\2\2\u00a1\u00a2\7"+
		"w\2\2\u00a2\u00a3\7n\2\2\u00a3\u00a4\7n\2\2\u00a4\20\3\2\2\2\u00a5\u00a6"+
		"\7v\2\2\u00a6\u00a7\7t\2\2\u00a7\u00a8\7w\2\2\u00a8\u00a9\7g\2\2\u00a9"+
		"\22\3\2\2\2\u00aa\u00ab\7h\2\2\u00ab\u00ac\7c\2\2\u00ac\u00ad\7n\2\2\u00ad"+
		"\u00ae\7u\2\2\u00ae\u00af\7g\2\2\u00af\24\3\2\2\2\u00b0\u00b1\7v\2\2\u00b1"+
		"\u00b2\7j\2\2\u00b2\u00b3\7k\2\2\u00b3\u00b4\7u\2\2\u00b4\26\3\2\2\2\u00b5"+
		"\u00b6\7k\2\2\u00b6\u00b7\7h\2\2\u00b7\30\3\2\2\2\u00b8\u00b9\7g\2\2\u00b9"+
		"\u00ba\7n\2\2\u00ba\u00bb\7u\2\2\u00bb\u00bc\7g\2\2\u00bc\32\3\2\2\2\u00bd"+
		"\u00be\7h\2\2\u00be\u00bf\7q\2\2\u00bf\u00c0\7t\2\2\u00c0\34\3\2\2\2\u00c1"+
		"\u00c2\7y\2\2\u00c2\u00c3\7j\2\2\u00c3\u00c4\7k\2\2\u00c4\u00c5\7n\2\2"+
		"\u00c5\u00c6\7g\2\2\u00c6\36\3\2\2\2\u00c7\u00c8\7d\2\2\u00c8\u00c9\7"+
		"t\2\2\u00c9\u00ca\7g\2\2\u00ca\u00cb\7c\2\2\u00cb\u00cc\7m\2\2\u00cc "+
		"\3\2\2\2\u00cd\u00ce\7e\2\2\u00ce\u00cf\7q\2\2\u00cf\u00d0\7p\2\2\u00d0"+
		"\u00d1\7v\2\2\u00d1\u00d2\7k\2\2\u00d2\u00d3\7p\2\2\u00d3\u00d4\7w\2\2"+
		"\u00d4\u00d5\7g\2\2\u00d5\"\3\2\2\2\u00d6\u00d7\7t\2\2\u00d7\u00d8\7g"+
		"\2\2\u00d8\u00d9\7v\2\2\u00d9\u00da\7w\2\2\u00da\u00db\7t\2\2\u00db\u00dc"+
		"\7p\2\2\u00dc$\3\2\2\2\u00dd\u00de\7-\2\2\u00de&\3\2\2\2\u00df\u00e0\7"+
		"/\2\2\u00e0(\3\2\2\2\u00e1\u00e2\7,\2\2\u00e2*\3\2\2\2\u00e3\u00e4\7\61"+
		"\2\2\u00e4,\3\2\2\2\u00e5\u00e6\7\'\2\2\u00e6.\3\2\2\2\u00e7\u00e8\7@"+
		"\2\2\u00e8\60\3\2\2\2\u00e9\u00ea\7>\2\2\u00ea\62\3\2\2\2\u00eb\u00ec"+
		"\7@\2\2\u00ec\u00ed\7?\2\2\u00ed\64\3\2\2\2\u00ee\u00ef\7>\2\2\u00ef\u00f0"+
		"\7?\2\2\u00f0\66\3\2\2\2\u00f1\u00f2\7#\2\2\u00f2\u00f3\7?\2\2\u00f38"+
		"\3\2\2\2\u00f4\u00f5\7?\2\2\u00f5\u00f6\7?\2\2\u00f6:\3\2\2\2\u00f7\u00f8"+
		"\7(\2\2\u00f8\u00f9\7(\2\2\u00f9<\3\2\2\2\u00fa\u00fb\7~\2\2\u00fb\u00fc"+
		"\7~\2\2\u00fc>\3\2\2\2\u00fd\u00fe\7#\2\2\u00fe@\3\2\2\2\u00ff\u0100\7"+
		"@\2\2\u0100\u0101\7@\2\2\u0101B\3\2\2\2\u0102\u0103\7>\2\2\u0103\u0104"+
		"\7>\2\2\u0104D\3\2\2\2\u0105\u0106\7(\2\2\u0106F\3\2\2\2\u0107\u0108\7"+
		"~\2\2\u0108H\3\2\2\2\u0109\u010a\7`\2\2\u010aJ\3\2\2\2\u010b\u010c\7\u0080"+
		"\2\2\u010cL\3\2\2\2\u010d\u010e\7?\2\2\u010eN\3\2\2\2\u010f\u0110\7-\2"+
		"\2\u0110\u0111\7-\2\2\u0111P\3\2\2\2\u0112\u0113\7/\2\2\u0113\u0114\7"+
		"/\2\2\u0114R\3\2\2\2\u0115\u0116\7\60\2\2\u0116T\3\2\2\2\u0117\u011b\t"+
		"\2\2\2\u0118\u011a\t\3\2\2\u0119\u0118\3\2\2\2\u011a\u011d\3\2\2\2\u011b"+
		"\u0119\3\2\2\2\u011b\u011c\3\2\2\2\u011cV\3\2\2\2\u011d\u011b\3\2\2\2"+
		"\u011e\u011f\7*\2\2\u011fX\3\2\2\2\u0120\u0121\7+\2\2\u0121Z\3\2\2\2\u0122"+
		"\u0123\7]\2\2\u0123\\\3\2\2\2\u0124\u0125\7_\2\2\u0125^\3\2\2\2\u0126"+
		"\u0127\7}\2\2\u0127`\3\2\2\2\u0128\u0129\7\177\2\2\u0129b\3\2\2\2\u012a"+
		"\u012b\7A\2\2\u012bd\3\2\2\2\u012c\u012d\7<\2\2\u012df\3\2\2\2\u012e\u012f"+
		"\7=\2\2\u012fh\3\2\2\2\u0130\u0131\7.\2\2\u0131j\3\2\2\2\u0132\u0133\7"+
		"$\2\2\u0133l\3\2\2\2\u0134\u0135\7^\2\2\u0135\u013b\7^\2\2\u0136\u0137"+
		"\7^\2\2\u0137\u013b\7p\2\2\u0138\u0139\7^\2\2\u0139\u013b\7$\2\2\u013a"+
		"\u0134\3\2\2\2\u013a\u0136\3\2\2\2\u013a\u0138\3\2\2\2\u013bn\3\2\2\2"+
		"\u013c\u0145\7\62\2\2\u013d\u0141\t\4\2\2\u013e\u0140\t\5\2\2\u013f\u013e"+
		"\3\2\2\2\u0140\u0143\3\2\2\2\u0141\u013f\3\2\2\2\u0141\u0142\3\2\2\2\u0142"+
		"\u0145\3\2\2\2\u0143\u0141\3\2\2\2\u0144\u013c\3\2\2\2\u0144\u013d\3\2"+
		"\2\2\u0145p\3\2\2\2\u0146\u0147\7h\2\2\u0147\u014d\5k\66\2\u0148\u014c"+
		"\n\6\2\2\u0149\u014a\7&\2\2\u014a\u014c\7&\2\2\u014b\u0148\3\2\2\2\u014b"+
		"\u0149\3\2\2\2\u014c\u014f\3\2\2\2\u014d\u014e\3\2\2\2\u014d\u014b\3\2"+
		"\2\2\u014e\u0150\3\2\2\2\u014f\u014d\3\2\2\2\u0150\u0151\5k\66\2\u0151"+
		"r\3\2\2\2\u0152\u0153\7h\2\2\u0153\u0159\5k\66\2\u0154\u0158\n\6\2\2\u0155"+
		"\u0156\7&\2\2\u0156\u0158\7&\2\2\u0157\u0154\3\2\2\2\u0157\u0155\3\2\2"+
		"\2\u0158\u015b\3\2\2\2\u0159\u0157\3\2\2\2\u0159\u015a\3\2\2\2\u015a\u015c"+
		"\3\2\2\2\u015b\u0159\3\2\2\2\u015c\u015d\7&\2\2\u015dt\3\2\2\2\u015e\u0164"+
		"\7&\2\2\u015f\u0163\n\6\2\2\u0160\u0161\7&\2\2\u0161\u0163\7&\2\2\u0162"+
		"\u015f\3\2\2\2\u0162\u0160\3\2\2\2\u0163\u0166\3\2\2\2\u0164\u0162\3\2"+
		"\2\2\u0164\u0165\3\2\2\2\u0165\u0167\3\2\2\2\u0166\u0164\3\2\2\2\u0167"+
		"\u0168\7&\2\2\u0168v\3\2\2\2\u0169\u016e\5k\66\2\u016a\u016d\5m\67\2\u016b"+
		"\u016d\13\2\2\2\u016c\u016a\3\2\2\2\u016c\u016b\3\2\2\2\u016d\u0170\3"+
		"\2\2\2\u016e\u016f\3\2\2\2\u016e\u016c\3\2\2\2\u016f\u0171\3\2\2\2\u0170"+
		"\u016e\3\2\2\2\u0171\u0172\5k\66\2\u0172x\3\2\2\2\u0173\u0179\7&\2\2\u0174"+
		"\u0178\n\6\2\2\u0175\u0176\7&\2\2\u0176\u0178\7&\2\2\u0177\u0174\3\2\2"+
		"\2\u0177\u0175\3\2\2\2\u0178\u017b\3\2\2\2\u0179\u0177\3\2\2\2\u0179\u017a"+
		"\3\2\2\2\u017a\u017c\3\2\2\2\u017b\u0179\3\2\2\2\u017c\u017d\5k\66\2\u017d"+
		"z\3\2\2\2\u017e\u0180\t\7\2\2\u017f\u017e\3\2\2\2\u0180\u0181\3\2\2\2"+
		"\u0181\u017f\3\2\2\2\u0181\u0182\3\2\2\2\u0182\u0183\3\2\2\2\u0183\u0184"+
		"\b>\2\2\u0184|\3\2\2\2\u0185\u0186\7\61\2\2\u0186\u0187\7\61\2\2\u0187"+
		"\u018b\3\2\2\2\u0188\u018a\n\b\2\2\u0189\u0188\3\2\2\2\u018a\u018d\3\2"+
		"\2\2\u018b\u0189\3\2\2\2\u018b\u018c\3\2\2\2\u018c\u018e\3\2\2\2\u018d"+
		"\u018b\3\2\2\2\u018e\u018f\b?\2\2\u018f~\3\2\2\2\u0190\u0191\7\61\2\2"+
		"\u0191\u0192\7,\2\2\u0192\u0196\3\2\2\2\u0193\u0195\13\2\2\2\u0194\u0193"+
		"\3\2\2\2\u0195\u0198\3\2\2\2\u0196\u0197\3\2\2\2\u0196\u0194\3\2\2\2\u0197"+
		"\u0199\3\2\2\2\u0198\u0196\3\2\2\2\u0199\u019a\7,\2\2\u019a\u019b\7\61"+
		"\2\2\u019b\u019c\3\2\2\2\u019c\u019d\b@\2\2\u019d\u0080\3\2\2\2\24\2\u011b"+
		"\u013a\u0141\u0144\u014b\u014d\u0157\u0159\u0162\u0164\u016c\u016e\u0177"+
		"\u0179\u0181\u018b\u0196\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}