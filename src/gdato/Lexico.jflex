package gdato;

import java_cup.runtime.Symbol;
import java.util.ArrayList;

%%

%cupsym SymGDato
%class LexicoGDato
%cup
%public
%unicode
%line
%char
%column
%ignorecase


%{
	StringBuffer string = new StringBuffer();
	
%}

digito = [0-9]
entero = {digito}+
decimal = {digito}+"."{digito}+
letra = [a-zA-ZñÑ]
identificador = ({letra}|"_")({letra}|{digito}|"_")*

finLinea = \r|\n|\r\n
espacioBlanco = {finLinea} | [ \t\f]

%state STRING

%%

<YYINITIAL>{

\" 					{ string.setLength(0); yybegin(STRING); }

"<"					{return  new Symbol(SymGDato.menorque, yyline, yycolumn);}
">"					{return  new Symbol(SymGDato.mayorque, yyline, yycolumn);}
"/"					{return  new Symbol(SymGDato.diagonal, yyline, yycolumn);}

{entero}       		{ return new Symbol(SymGDato.entero, yyline, yycolumn, yytext());}
{decimal}			{ return new Symbol(SymGDato.decimal, yyline, yycolumn, yytext());}
{identificador}		{ return new Symbol(SymGDato.identificador, yyline, yycolumn, yytext());}


/* Espacios en Blanco */
{espacioBlanco}			{ /* se ignora */ }

}

<STRING> {
\"                   { yybegin(YYINITIAL);
					   return new Symbol(SymGDato.tstring, yyline, yycolumn, string.toString()); }
[^\n\r\"\\]+         { string.append( yytext() ); }
\\t                  { string.append('\t'); }
\\n                  { string.append('\n'); }
\\r                  { string.append('\r'); }
\\\"                 { string.append('\"'); }
\\                   { string.append('\\'); }
}

/* ERRORES LEXICOS */
.					{ System.out.println("Error lexico: "+yytext() + " Linea: "+(yyline+1) + " Columna: "+(yycolumn+1));}











