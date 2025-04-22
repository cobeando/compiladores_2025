package ar.edu.unnoba.comp.jflextp;

import jflex.generator.LexGenerator;


import java.io.File;

public class Generador {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        generarLexer();
    }

    public static void generarLexer(){
        File file= new File(Generador.class.getClassLoader().getResource("lexico.flex").getFile());
        LexGenerator generator = new LexGenerator(file);
        generator.generate();
    }

}