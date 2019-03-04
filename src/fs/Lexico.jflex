package fs;

import java_cup.runtime.Symbol;
import java.util.ArrayList;

%%

%cupsym SymFs
%class LexicoFs
%cup
%public
%unicode
%line
%char
%column
%ignorecase


%{
	
	StringBuffer string = new StringBuffer();
	boolean blancos = false;
	
	private Symbol symbol(int type) {
		return new Symbol(type, yyline, yycolumn);
	}	
  
	private Symbol symbol(int type, Object value) {
		return new Symbol(type, yyline, yycolumn, value);
	}
%}

digito = [0-9]
entero = {digito}+
decimal = {digito}+"."{digito}+
letra = [a-zA-ZñÑ]
identificador = ({letra}|"_")({letra}|{digito}|"_")*

finLinea = \r|\n|\r\n
espacioBlanco = {finLinea} | [ \t\f]

COMENT_SIMPLE ="//" [^\r\n]* {finLinea}?
COMENT_MULTI ="/*""/"*([^*/]|[^*]"/"|"*"[^/])*"*"*"*/"

%state STRING
%state CHAR

%%

{COMENT_SIMPLE} 	{/* se ignora */} 
{COMENT_MULTI} 		{/* se ignora */} 


<YYINITIAL> "var"				{return symbol(SymFs.var_);}
<YYINITIAL> "imprimir"			{return symbol(SymFs.imprimir_);}
<YYINITIAL> "importar"			{return symbol(SymFs.importar_);}
<YYINITIAL> "detener"			{return symbol(SymFs.detener_);}
<YYINITIAL> "retornar"			{return symbol(SymFs.retornar_);}
<YYINITIAL> "si"				{return symbol(SymFs.si_);}
<YYINITIAL> "sino"				{return symbol(SymFs.sino_);}
<YYINITIAL> "selecciona"		{return symbol(SymFs.selecciona_);}
<YYINITIAL> "caso"				{return symbol(SymFs.caso_);}
<YYINITIAL> "defecto"			{return symbol(SymFs.defecto_);}
<YYINITIAL> "funcion"			{return symbol(SymFs.funcion_);}

<YYINITIAL> "nulo"				{ return symbol(SymFs.nulo_, yytext());}
<YYINITIAL> "verdadero"			{ return symbol(SymFs.verdadero_, yytext());}
<YYINITIAL> "falso"				{ return symbol(SymFs.falso_, yytext());}


<YYINITIAL>{

\" 					{ string.setLength(0); yybegin(STRING); }
\' 					{ string.setLength(0); yybegin(CHAR); }

"{"					{return symbol(SymFs.llaveIzquierda);}
"}"					{return symbol(SymFs.llaveDerecha);}
"("					{return symbol(SymFs.parIzquierda);}
")"					{return symbol(SymFs.parDerecha);}
"["					{return symbol(SymFs.corcheteIzquierda);}
"]"					{return symbol(SymFs.corcheteDerecha);}
";"					{return symbol(SymFs.puntoycoma);}
","					{return symbol(SymFs.coma);}
"."					{return symbol(SymFs.punto);}
":"					{return symbol(SymFs.dospuntos);}
"?"					{return symbol(SymFs.interrogacion);}


//Operadores Aritméticos
"+"                 {return symbol(SymFs.mas);}
"-"                 {return symbol(SymFs.menos);}
"*"                 {return symbol(SymFs.asterisco);}  
"/"                 {return symbol(SymFs.diagonal);}
"^"                 {return symbol(SymFs.potencia);}

"++"                {return symbol(SymFs.masmas);}
"--"                {return symbol(SymFs.menosmenos);}

//Operadores Relacionales 
">"                 {return symbol(SymFs.mayorque);}
"<"                 {return symbol(SymFs.menorque);}
">="                {return symbol(SymFs.mayorigual);}
"<="                {return symbol(SymFs.menorigual);}
"=="                {return symbol(SymFs.igualigual);}
"!="                {return symbol(SymFs.diferente);}

//Operadores Lógicos
"&&"                {return symbol(SymFs.and);}
"||"                {return symbol(SymFs.or);}
"!"                 {return symbol(SymFs.not);}

//Operador Asignacion
"="                {return symbol(SymFs.igual);}
"+="                {return symbol(SymFs.masigual);}
"-="                {return symbol(SymFs.menosigual);}
"*="                 {return symbol(SymFs.porigual);}
"/="                 {return symbol(SymFs.diagonaligual);}

{entero}       		{ return symbol(SymFs.entero, yytext());}
{decimal}			{ return symbol(SymFs.decimal, yytext());}
{identificador}		{ return symbol(SymFs.identificador, yytext());}

/* Espacios en Blanco */
{espacioBlanco}			{ /* se ignora */ }

}

<STRING> {
\"                   { yybegin(YYINITIAL);
					   return symbol(SymFs.tstring, string.toString()); }
[^\n\r\"\\]+         { string.append( yytext() ); }
\\t                  { string.append('\t'); }
\\n                  { string.append('\n'); }
\\r                  { string.append('\r'); }
\\\"                 { string.append('\"'); }
\\                   { string.append('\\'); }
}

<CHAR> {
\'                   { yybegin(YYINITIAL);
					   return symbol(SymFs.tchar, string.toString()); }
[^\n\r\'\\]+         { string.append( yytext() ); }
\\t                  { string.append('\t'); }
\\n                  { string.append('\n'); }
\\r                  { string.append('\r'); }
\\\'                 { string.append('\''); }
\\                   { string.append('\\'); }
}

/* ERRORES LEXICOS */
.					{ System.out.println("Error lexico: "+yytext() + " Linea: "+(yyline+1) + " Columna: "+(yycolumn+1));}











