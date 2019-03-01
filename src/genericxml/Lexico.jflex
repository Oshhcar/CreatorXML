package genericxml;

import java_cup.runtime.Symbol;
import java.util.ArrayList;

%%

%cupsym Sym
%class Lexico
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

finLinea = \r|\n|\r\n
espacioBlanco = {finLinea} | [ \t\f]

tag = "importar"
	|"ventana"
	|"contenedor"
	|"texto"
	|"control"
	|"listadatos"
	|"dato"
	|"defecto"
	|"multimedia"
	|"boton"
	|"enviar"

COMENT_SIMPLE =("##".*\r\n)|("##".*\n)|("##".*\r)
COMENT_MULTI ="#$""#"*([^$#]|[^$]"#"|"$"[#])*"$"*"$#"
codigo = "{""{"*([^"}"])*"}"*"}"

%state STRING
%state PLANO

%%

{COMENT_SIMPLE} 	{/* se ignora */} 
{COMENT_MULTI} 		{/* se ignora */} 


<YYINITIAL> "importar"			{return symbol(Sym.importar_);}
<YYINITIAL> "ventana"			{return symbol(Sym.ventana_);}
<YYINITIAL> "contenedor"		{return symbol(Sym.contenedor_);}
<YYINITIAL> "texto"				{return symbol(Sym.texto_);}
<YYINITIAL> "control"			{return symbol(Sym.control_);}
<YYINITIAL> "listadatos"		{return symbol(Sym.listadatos_);}
<YYINITIAL> "dato"				{return symbol(Sym.dato_);}
<YYINITIAL> "defecto"			{return symbol(Sym.defecto_);}
<YYINITIAL> "multimedia"		{return symbol(Sym.multimedia_);}
<YYINITIAL> "boton"				{return symbol(Sym.boton_);}
<YYINITIAL> "enviar"			{return symbol(Sym.enviar_);}

<YYINITIAL> "id"				{return symbol(Sym.id_);}
<YYINITIAL> "tipo"				{return symbol(Sym.tipo_);}
<YYINITIAL> "color"				{return symbol(Sym.color_);}
<YYINITIAL> "accioninicial"		{return symbol(Sym.accioninicial_);}
<YYINITIAL> "accionfinal"		{return symbol(Sym.accionfinal_);}
<YYINITIAL> "x"					{return symbol(Sym.x_);}
<YYINITIAL> "y"					{return symbol(Sym.y_);}
<YYINITIAL> "alto"				{return symbol(Sym.alto_);}
<YYINITIAL> "ancho"				{return symbol(Sym.ancho_);}
<YYINITIAL> "borde"				{return symbol(Sym.borde_);}
<YYINITIAL> "nombre"			{return symbol(Sym.nombre_);}
<YYINITIAL> "fuente"			{return symbol(Sym.fuente_);}
<YYINITIAL> "tam"				{return symbol(Sym.tam_);}
<YYINITIAL> "negrita"			{return symbol(Sym.negrita_);}
<YYINITIAL> "cursiva"			{return symbol(Sym.cursiva_);}
<YYINITIAL> "maximo"			{return symbol(Sym.maximo_);}
<YYINITIAL> "minimo"			{return symbol(Sym.minimo_);}
<YYINITIAL> "accion"			{return symbol(Sym.accion_);}
<YYINITIAL> "referencia"		{return symbol(Sym.referencia_);}
<YYINITIAL> "path"				{return symbol(Sym.path_);}
<YYINITIAL> "auto-reproduccion"	{return symbol(Sym.autoreproduccion_);}


<YYINITIAL> "true"				{ return symbol(Sym.true_, yytext());}
<YYINITIAL> "false"				{ return symbol(Sym.false_, yytext());}


<YYINITIAL>{

\" 					{ string.setLength(0); yybegin(STRING); }

"<"					{return symbol(Sym.menorque);}
">"					{blancos=false; string.setLength(0); yybegin(PLANO); return symbol(Sym.mayorque);}
"="					{return symbol(Sym.igual);}
"/"					{return symbol(Sym.diagonal);}

{entero}       		{ return symbol(Sym.entero, yytext());}
{decimal}			{ return symbol(Sym.decimal, yytext());}
{codigo}			{ return symbol(Sym.codigo, yytext());}

/* Espacios en Blanco */
{espacioBlanco}			{ /* se ignora */ }

}

<STRING> {
\"                   { yybegin(YYINITIAL);
					   return symbol(Sym.tstring, string.toString()); }
[^\n\r\"\\]+         { string.append( yytext() ); }
\\t                  { string.append('\t'); }
\\n                  { string.append('\n'); }
\\r                  { string.append('\r'); }
\\\"                 { string.append('\"'); }
\\                   { string.append('\\'); }
}

<PLANO> {
"<"[^/]*"/"[^<>/]*{tag}[^<>/]*">"	{ 	yybegin(YYINITIAL); 
										//System.out.println("value:"+yytext()); 
										yypushback(yylength()); 
										
										if(string.length()>=1 && blancos){
											//System.out.println(string.toString());
											return symbol(Sym.textoPlano, string.toString());
										}
									}
"<"{tag}[^><]*">"				{ 	yybegin(YYINITIAL); 
									//System.out.println("entra abrir"); 
									yypushback(yylength());
								}
[^<\r|\n|\r\n\t\f ]+  			{ string.append( yytext() ); blancos=true; }
"<"  	  						{ string.append( yytext() ); blancos=true; }
[ \n\r\t\f][ \n\r\t\f]*			{ string.append( " " ); }

}

/* ERRORES LEXICOS */
.					{ System.out.println("Error lexico: "+yytext() + " Linea: "+(yyline+1) + " Columna: "+(yycolumn+1));}











