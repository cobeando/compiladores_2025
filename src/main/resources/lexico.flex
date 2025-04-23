
package ar.edu.unnoba.comp.jflextp;

import java_cup.runtime.*;
import java_cup.sym;
import java.util.ArrayList;

%%

%public
%class MiLexico
%cup
%line
%column
%unicode

%{
    /*************************************************************************
    * En esta sección se puede incluir código que se copiará textualmente
    * como parte de la definición de la clase del analizador léxico.
    * Típicamente serán variables de instancia o nuevos métodos de la clase.
    *************************************************************************/

    public ArrayList<MiToken> tablaDeSimbolos = new ArrayList<>();


    /* Variables para reconocer Strings */
    StringBuffer string = new StringBuffer();
    int string_yyline = 0;
    int string_yycolumn = 0;
    int comment_count = 0;

    private MiToken token(String nombre) {
        return new MiToken(nombre, this.yyline, this.yycolumn);
    }

    private MiToken token(String nombre, Object valor) {
        return new MiToken(nombre, this.yyline, this.yycolumn, valor);
    }

    private MiToken token(String nombre, int line, int column, Object valor) {
        return new MiToken(nombre, line, column, valor);
    }
%}

LineTerminator = \r|\n|\r\n
WhiteSpace     = {LineTerminator} | [ \t\f]

DecFloatLiteral   = ([0-9]+\.[0-9]*)|([0-9]*\.[0-9]+)
DecIntegerLiteral =  [0-9]+ 
DecArrayFloat     = \[-?(([0-9]+\.[0-9]*)|([0-9]*\.[0-9]+))(,-?(([0-9]+\.[0-9]*)|([0-9]*\.[0-9]+)))*\]
ComentarioTerminallinea = \$.*{LineTerminator}?
OpenComment = \(\*
CloseComment = \*\)
OpenCommentBrackets = \[\*
CloseCommentBrackets = \*\]
OpenCommentCurlyBracket = \{\*
CloseCommentCurlyBracket = \*\}

Identifier = \p{L}[\p{L}\p{N}_]*

%state CADENA
%state COMENTARIO
%state COMENTARIOBRACKETS
%state COMENTARIOCURLYBRACKET

%%

<YYINITIAL> {
  {ComentarioTerminallinea} { /* ignore */ }


  {CloseComment}       { throw new ar.edu.unnoba.comp.jflextp.exceptions.CloseCommentException("Cierre de comentario inválido \n");}


  /* palabras reservadas */
  /*TODO Todos los tokens incorporan lexemas, aún cuando no es necesario,
    TODO como en el caso de palabras reservadas y operadores.*/

    
  "BOOLEAN"            { return token("BOOLEAN", yytext());}
  "FALSE"              { return token("FALSE", yytext());}
  "TRUE"               { return token("TRUE", yytext());}
  "INT"                { return token("ENTERO", yytext());}
  "FLOAT_ARRAY"        { return token("FLOAT_ARRAY", yytext());}
  "FLOAT"              { return token("FLOTANTE", yytext());}
  "STRING"             { return token("CADENA");}

  "AND"                { return token("AND", yytext()); }
  "OR"                 { return token("OR", yytext()); }
  "NOT"                { return token("NOT", yytext()); }

  "LOOP"               { return token("LOOP", yytext()); }
  "WHEN"               { return token("WHEN", yytext()); }
  "BACKWARD_LOOP"      { return token("BACKWARD_LOOP", yytext()); }
  "END_LOOP"           { return token("END_LOOP", yytext()); }
  "CONTINUE"           { return token("CONTINUE", yytext()); }
  "BREAK"              { return token("BREAK", yytext()); }


  "CONDITION"          { return token("CONDITION", yytext()); }
  "BACKWARD_CONDITION" { return token("BACKWARD_CONDITION", yytext()); }
  "THEN"               { return token("THEN", yytext()); }
  "END"                { return token("END", yytext()); }
  "ELSE"               { return token("ELSE", yytext()); }
  "ELSE_BACKWARD"      { return token("ELSE_BACKWARD", yytext()); }
  "DISPLAY"            { return token("DISPLAY", yytext()); }

  "any"                { return token("ANY", yytext()); }
  "all"                { return token("ALL", yytext()); }

  "INPUT_INT"          { return token("INPUT_INT", yytext()); }
  "INPUT_FLOAT"        { return token("INPUT_FLOAT", yytext()); }
  "INPUT_BOOL"         { return token("INPUT_BOOL", yytext()); }
  "INPUT_ARRAY"        { return token("INPUT_ARRAY", yytext()); }

  "DECLARE.SECTION"   { return token("DECLARE_SECTION"); }
  "ENDDECLARE.SECTION" { return token("ENDDECLARE_SECTION", yytext()); }

  "PROGRAM.SECTION"         { return token("PROGRAM_SECTION", yytext()); }
  "ENDPROGRAM.SECTION"      { return token("ENDPROGRAM_SECTION", yytext()); }
  "valor_mas_cercano"       { return token("VALOR_MAS_CERCANO", yytext()); }


  /* literales */

  {DecArrayFloat}      { return token("FLOAT_ARRAY_LITERAL", yytext()); }
  {DecFloatLiteral}    { return token("FLOTANTE_LITERAL", yytext()); }
  {DecIntegerLiteral}  { return token("ENTERO_LITERAL", yytext()); }

  {Identifier}         { return token("IDENTIFICADOR", yytext()); }

  /* operadores */
    "="                 { return token("IGUAL",  yytext()); }
    "=="                 { return token("EQUIVALE", yytext()); }
    "+"                  { return token("MAS", yytext()); }
    "-"                  { return token("MENOS", yytext()); }
    "*"                  { return token("MULT", yytext()); }
    "\/"                 { return token("DIV", yytext()); }
    "<"                  { return token("MENOR_QUE", yytext()); }
    ">"                  { return token("MAYOR_QUE", yytext()); }
    ">="                 { return token("MAYOR_IGUAL_QUE", yytext()); }
    "<="                 { return token("MENOR_IGUAL_QUE", yytext()); }
    "!="                 { return token("DIFERENTE", yytext()); }
    "\("                 { return token("ABRE_PARENTESIS", yytext()); }
    "\)"                 { return token("CIERRA_PARENTESIS", yytext()); }
    "\["                 { return token("ABRE_CORCHETE", yytext()); }
    "\]"                 { return token("CIERRA_CORCHETE", yytext()); }
    "."                  { return token("PUNTO");}
    ";"                  { return token("PUNTO_COMA", yytext()); }
    ","                  { return token("COMA", yytext()); }
    ":"                  { return token("DOS_PUNTOS", yytext()); }

  /* espacios en blanco */
  {WhiteSpace}         { /* ignore */ }

  /* Empieza String */
  \"                   { string.setLength(0);
                         string_yyline = this.yyline;
                         string_yycolumn = this.yycolumn;
                         yybegin(CADENA);
                       }

                         /* Empieza comentario */
    {OpenComment}         { comment_count = 1;
                            yybegin(COMENTARIO);
                            }

}


<CADENA> {
  \"                   { yybegin(YYINITIAL);
                         return token("STRING_LITERAL",
                                      string_yyline, string_yycolumn,
                                      string.toString());
                       }

  \\\"                  { string.append("\""); }
  "\n"                  { string.append("\n");}
  "\t"                  { string.append("\t");}
  "\\"                  { string.append("\\");}
  "\r"                  { string.append("\r");}

  /* Fin de archivo */
  <<EOF>>              { throw new ar.edu.unnoba.comp.jflextp.exceptions.EOFLexerException("Fin de archivo dentro de la cadena: \n" +
                                               string.toString()); }

  /* Cualquier otro carácter */
  [^]                  { string.append(yytext()); }
}



<COMENTARIO> {

    {CloseComment}                  { comment_count = comment_count - 1;
                                      if (comment_count == 0){
                                        yybegin(YYINITIAL);
                                      } yybegin(COMENTARIOCURLYBRACKET)
                                    ;}

    {OpenCommentBrackets}           {yybegin(COMENTARIOBRACKETS);}
                                  
  /* Fin de archivo */
  <<EOF>>              { throw new ar.edu.unnoba.comp.jflextp.exceptions.EOFLexerException("Comentario sin balancear: "+ yytext()); }

   [^.]|.          { /* ignore */ }
}

<COMENTARIOBRACKETS> {

    {CloseCommentBrackets}           {yybegin(COMENTARIO);}

    {OpenCommentCurlyBracket}        {yybegin(COMENTARIOCURLYBRACKET);}

  /* Fin de archivo */
  <<EOF>>              { throw new ar.edu.unnoba.comp.jflextp.exceptions.EOFLexerException("Comentario sin balancear: "+ yytext()); }

   [^.]|.          { /* ignore */ }
}

<COMENTARIOCURLYBRACKET> {

    {CloseCommentCurlyBracket}       {yybegin(COMENTARIOBRACKETS);}

    {OpenComment}                    {comment_count = comment_count + 1;   
                                      yybegin(COMENTARIO);}

  /* Fin de archivo */
  <<EOF>>              { throw new ar.edu.unnoba.comp.jflextp.exceptions.EOFLexerException("Comentario sin balancear: "+ yytext()); }

   [^.]|.          { /* ignore */ }
}


/* fallback de errores */
[^]                    { throw new ar.edu.unnoba.comp.jflextp.exceptions.LexerException("Carácter inválido <"+yytext()+">"); }
