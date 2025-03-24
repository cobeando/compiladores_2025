package ejemplo.jflex;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java_cup.runtime.Symbol;

public class EjemploJFlex {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        
        //FileReader entrada = new FileReader("./entrada.txt");
        //MiLexico lexico = new MiLexico(entrada);

        MiLexico lexico = new MiLexico(new InputStreamReader(System.in));

        System.out.printf("Análisis léxico iniciado: %nIngrese por teclado:%n");
        while (true) {
            MiToken token = lexico.yylex();
            if (token == null)
                break;
            System.out.println("Token: " + token.toString());
        }
        System.out.println("Análisis léxico terminado.");

    }
    
}
