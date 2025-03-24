package ar.edu.unnoba.comp.compilertp;

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

DecDuplaLiteral    = \(\s*\d+\.\d*\s*,\s*\d+\.?\d*\s*\)
DecFloatLiteral   = ([0-9]+\.[0-9]*)|([0-9]*\.[0-9]+)
DecIntegerLiteral = 0 | [1-9][0-9]* //todo arreglar 0123 toma 2 token 0 y 123  ej: 0$| [1-9][0-9]*
ComentarioTerminallinea = \#.*{LineTerminator}?
OpenComment = \(\*
CloseComment = \*\)

Identifier = \p{L}[\p{L}\p{N}_]*

%state CADENA
%state COMENTARIO

%%

<YYINITIAL> {
  {ComentarioTerminallinea} { /* ignore */ }


  {CloseComment}       { throw new ar.edu.unnoba.comp.compilertp.exceptions.CloseCommentException("Cierre de comentario inválido \n");}


  /* palabras reservadas */
  /*TODO Todos los tokens incorporan lexemas, aún cuando no es necesario,
    TODO como en el caso de palabras reservadas y operadores.*/
  "BOOLEAN"            { return token("BOOLEAN", yytext());}
  "FALSE"              { return token("FALSE", yytext());}
  "TRUE"               { return token("TRUE", yytext());}
  "INT"                { return token("INTEGER", yytext());}
  "DUPLE"              { return token("DUPLE", yytext());}
  "FLOAT"              { return token("FLOAT", yytext());}
  "STRING"             { return token("STRING");}

  "AND"                { return token("AND", yytext()); }
  "OR"                 { return token("OR", yytext()); }
  "NOT"                { return token("NOT", yytext()); }

  "REPEAT"             { return token("REPEAT", yytext()); }
  "UNTIL"              { return token("UNTIL", yytext()); }
  "CONTINUE"           { return token("CONTINUE", yytext()); }
  "BREAK"              { return token("BREAK", yytext()); }

  "THEN"               { return token("THEN", yytext()); }
  "UNLESS"             { return token("UNLESS", yytext()); }
  "END"                { return token("END", yytext()); }
  "ELSE"               { return token("ELSE", yytext()); }
  "SHOW"               { return token("SHOW", yytext()); }

  "INPUT_INT"          { return token("INPUT_INT", yytext()); }
  "INPUT_FLOAT"        { return token("INPUT_FLOAT", yytext()); }
  "INPUT_BOOL"         { return token("INPUT_BOOL", yytext()); }
  "INPUT_DUPLE"        { return token("INPUT_DUPLE", yytext()); }

  "DECLARE.SECTION"   { return token("DECLARE_SECTION"); }
  "ENDDECLARE.SECTION" { return token("ENDDECLARE_SECTION", yytext()); }

  "PROGRAM.SECTION"         { return token("PROGRAM_SECTION", yytext()); }
  "ENDPROGRAM.SECTION"      { return token("ENDPROGRAM_SECTION", yytext()); }


  "promr"              { return token("PROMR", yytext()); }
  "PROMR"              { return token("PROMR", yytext()); }

  /* literales */

  {DecDuplaLiteral}    { return token("DUPLA_LITERAL", yytext()); }
  {DecIntegerLiteral}  { return token("INTEGER_LITERAL", yytext()); }
  {DecFloatLiteral}    { return token("FLOAT_LITERAL", yytext()); }

  {Identifier}         { return token("ID", yytext()); }

  /* operadores */
    ":="                 { return token("EQ",   yytext()); }
    "=.="                 { return token("EQEQ", yytext()); }
    "+"                  { return token("PLUS", yytext()); }
    "-"                  { return token("MINUS", yytext()); }
    "*"                  { return token("MULT", yytext()); }
    "\/"                 { return token("DIV", yytext()); }
    "<"                  { return token("LESS", yytext()); }
    ">"                  { return token("GREATER", yytext()); }
    ">="                 { return token("GEQ", yytext()); }
    "<="                 { return token("LEQ", yytext()); }
    "!="                 { return token("DIFFERENT", yytext()); }
    "\("                 { return token("OPPARENT", yytext()); }
    "\)"                 { return token("CLPARENT", yytext()); }
    "\["                 { return token("OPBRACKETS", yytext()); }
    "\]"                 { return token("CLBRACKETS", yytext()); }
    "."                  { return token("DOT");}
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
  <<EOF>>              { throw new ar.edu.unnoba.comp.compilertp.exceptions.EOFLexerException("Fin de archivo dentro de la cadena: \n" +
                                               string.toString()); }

  /* Cualquier otro carácter */
  [^]                  { string.append(yytext()); }
}

<COMENTARIO> {

   // Incrementar contador de comentarios al encontrar "(*"
    {OpenComment}                { comment_count = comment_count + 1; }

    // Decrementar contador de comentarios al encontrar "*)"
    {CloseComment}           { comment_count = comment_count - 1;
                            if(comment_count == 0) {
                                  yybegin(YYINITIAL);
                            }}

  /* Fin de archivo */
  <<EOF>>              { throw new ar.edu.unnoba.comp.compilertp.exceptions.EOFLexerException("Comentario sin balancear: "+ yytext()); }

   [^.]|.          { /* ignore */ }
}


/* fallback de errores */
[^]                    { throw new ar.edu.unnoba.comp.compilertp.exceptions.LexerException("Carácter inválido <"+yytext()+">"); }
