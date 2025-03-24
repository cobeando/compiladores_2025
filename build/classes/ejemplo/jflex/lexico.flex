/* Ejemplo de JFlex */
package ejemplo.jflex;

/**
 * Esta clase es un ejemplo simple de un lexer.
 */
%%

%public
%class MiLexico
%unicode
%type MiToken
%line
%column

%{
    /*************************************************************************
    * En esta sección se puede incluir código que se copiará textualmente
    * como parte de la definición de la clase del analizador léxico.
    * Típicamente serán variables de instancia o nuevos métodos de la clase.
    *************************************************************************/

    /* Variables para reconocer Strings */
    StringBuffer string = new StringBuffer();
    int string_yyline = 0;
    int string_yycolumn = 0;

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

DecIntegerLiteral = 0 | [1-9][0-9]*

Identifier = \p{L}+

%state CADENA

%%

<YYINITIAL> {
  /* palabras reservadas */
  "abstract"           { return token("ABSTRACT", yytext());}
  "boolean"            { return token("BOOLEAN", yytext()); }
  "break"              { return token("BREAK", yytext()); }

  /* literales */
  {DecIntegerLiteral}  { return token("INTEGER_LITERAL", yytext()); }

  {Identifier}         { return token("ID", yytext()); }

  /* operadores */
  "="                  { return token("EQ",   yytext()); }
  "=="                 { return token("EQEQ", yytext()); }
  "+"                  { return token("PLUS", yytext()); }

  /* espacios en blanco */
  {WhiteSpace}         { /* ignore */ }

  \"                   { string.setLength(0);
                         string_yyline = this.yyline;
                         string_yycolumn = this.yycolumn;
                         yybegin(CADENA);
                       }
}

<CADENA> {
  \"                   { yybegin(YYINITIAL);
                         return token("STRING_LITERAL",
                                      string_yyline, string_yycolumn,
                                      string.toString());
                       }

  \\\"                 { string.append("\"") ; }

  /* Fin de archivo */
  <<EOF>>              { throw new Error("Fin de archivo dentro de la cadena: \n" +
                                         string.toString()); }

  /* Cualquier otro carácter */
  [^]                  { string.append(yytext()); }
}

/* fallback de errores */
[^]                    { throw new Error("Carácter inválido <"+yytext()+">"); }
