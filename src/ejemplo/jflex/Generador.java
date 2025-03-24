package ejemplo.jflex;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Generador {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String path = "./src/ejemplo/jflex/lexico.flex";
        generarLexer(path);
    }
    
    /**
     *
     * @param path
     */
    public static void generarLexer(String path){
        File file=new File(path);
        //jflex.Main.generate(file);
        // Utilizar JFlex 1.8 o superior;
	// la próxima línea no funciona para las versiones previas
        jflex.generator.LexGenerator generator = new jflex.generator.LexGenerator(file);
        generator.generate();
    }
    
}
