package ar.edu.unnoba.comp.jflextp;

import jflex.generator.LexGenerator;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Generador {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String path = "./src/main/resources/lexico.flex";
        generarLexer(path);
        
        String[] param = new String[5];
        param[0] = "-destdir";
        param[1] = "./src/main/java/ar/edu/unnoba/comp/jflextp";
        param[2] = "-parser";
        param[3] = "MiParser";
        param[4] = "./src/main/resources/parser.cup";
        //generarParser(param);
    }
    
    /**
     *
     * @param path
     */
    public static void generarLexer(String path){
        File file = new File(path);
        
        //UTILIZAR JFLEX 1.8, la próxima línea no funciona para las versiones previas
        jflex.generator.LexGenerator generator = new jflex.generator.LexGenerator(file);
        generator.generate();
    }
    
    public static void generarParser(String[] param){
        try {
            java_cup.Main.main(param);
        } catch (IOException ex) {
            Logger.getLogger(Generador.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Generador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}