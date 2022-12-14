package cup.example;
import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.ComplexSymbolFactory.Location;
import java_cup.runtime.Symbol;
import java.lang.*;
import java.io.InputStreamReader;

%%

%class Lexer
%implements sym
%public
%unicode
%line
%column
%cup
%char
%{
	

    public Lexer(ComplexSymbolFactory sf, java.io.InputStream is){
		this(is);
        symbolFactory = sf;
    }
	public Lexer(ComplexSymbolFactory sf, java.io.Reader reader){
		this(reader);
        symbolFactory = sf;
    } 
    
    private StringBuffer sb;
    private ComplexSymbolFactory symbolFactory;
    private int csline,cscolumn;

    public Symbol symbol(String name, int code){
		return symbolFactory.newSymbol(name, code,
						new Location(yyline+1,yycolumn+1, yychar), // -yylength()
						new Location(yyline+1,yycolumn+yylength(), yychar+yylength())
				);
    }
    public Symbol symbol(String name, int code, String lexem){
	return symbolFactory.newSymbol(name, code, 
						new Location(yyline+1, yycolumn +1, yychar), 
						new Location(yyline+1,yycolumn+yylength(), yychar+yylength()), lexem);
    }
    
    protected void emit_warning(String message){
    	System.out.println("scanner warning: " + message + " at : 2 "+ 
    			(yyline+1) + " " + (yycolumn+1) + " " + yychar);
    }
    
    protected void emit_error(String message){
    	System.out.println("scanner error: " + message + " at : 2" + 
    			(yyline+1) + " " + (yycolumn+1) + " " + yychar);
    }
%}

Newline    = \r | \n | \r\n
Whitespace = [ \t\f] | {Newline}

Number     = [0-9]+
Name       = [a-zA-Z][0-9a-zA-z_]*
QChar      = '.'

/* comments */
Comment = {TraditionalComment} | {EndOfLineComment}
TraditionalComment = "//" {CommentContent} 
EndOfLineComment = "//" [^\r\n]* {Newline}
CommentContent = ( [^*] | \*+[^*/] )*

ident = ([:jletter:] | "_" ) ([:jletterdigit:] | [:jletter:] | "_" )*


%eofval{
    return symbolFactory.newSymbol("EOF",sym.EOF);
%eofval}

%state CODESEG

%%  


/* keywords */
<YYINITIAL> "int"	   {return symbolFactory.newSymbol("INT", INT); }
<YYINITIAL> "if"	   {return symbolFactory.newSymbol("IF", IF); }
<YYINITIAL> "else"	   {return symbolFactory.newSymbol("ELSE",ELSE); }
<YYINITIAL> "return"   {return symbolFactory.newSymbol("RETURN", RETURN); }
<YYINITIAL> "char"   {return symbolFactory.newSymbol("CHAR", CHAR); }
<YYINITIAL> "write"   {return symbolFactory.newSymbol("WRITE", WRITE); }
<YYINITIAL> "read"   {return symbolFactory.newSymbol("READ", READ); }
<YYINITIAL> "length"    {return symbolFactory.newSymbol("LENGTH", LENGTH); }
<YYINITIAL> "while"    {return symbolFactory.newSymbol("WHILE", WHILE); }

<YYINITIAL> {

  {Whitespace} {                              }

/* Relational Operators */
  "=="		   { return symbolFactory.newSymbol("EQUAL", EQUAL); }
  "!="         { return symbolFactory.newSymbol("NEQUAL", NEQUAL); }
   "="		   { return symbolFactory.newSymbol("ASSIGN", ASSIGN); }
  ">"		   { return symbolFactory.newSymbol("GREATER", GREATER); }
  "<"          { return symbolFactory.newSymbol("LESS", LESS); }

/* Separators */
  "("          { return symbolFactory.newSymbol("LPAR", LPAR); }
  ")"          { return symbolFactory.newSymbol("RPAR", RPAR); }
  "["          { return symbolFactory.newSymbol("LBRACK", LBRACK); }
  "]"          { return symbolFactory.newSymbol("RBRACK", RBRACK); }
  "{"          { return symbolFactory.newSymbol("LBRACE", LBRACE); }
  "}"          { return symbolFactory.newSymbol("RBRACE", RBRACE); }
  ";"          { return symbolFactory.newSymbol("SEMICOLON", SEMICOLON); }
  ","          { return symbolFactory.newSymbol("COMMA", COMMA); }

/* Arithmetic */
  "+"          { return symbolFactory.newSymbol("PLUS", PLUS); }
  "-"          { return symbolFactory.newSymbol("MINUS", MINUS); }
  "*"          { return symbolFactory.newSymbol("TIMES", TIMES); }
  "/"		   { return symbolFactory.newSymbol("DIVIDE", DIVIDE); }
  
/* Logical Operator */
  "!"          { return symbolFactory.newSymbol("NOT", NOT); }
  
  {Name}       { return symbolFactory.newSymbol("NAME", NAME, yytext()); }
  {Number}     { return symbolFactory.newSymbol("NUMBER", NUMBER, Integer.parseInt(yytext()));}
  {QChar}      { return symbolFactory.newSymbol("QCHAR", QCHAR, yytext()); }
}


// error fallback
.|\n          { emit_warning("Unrecognized character '" +yytext()+"' -- ignored"); }
