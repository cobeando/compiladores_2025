package ar.edu.unnoba.comp.jflextp.ast.factor;

public class Constant {
    private String value;

    public Constant(String value){
        this.value = value;
    }

    public String getEtiqueta(){
        return value;
    }
}
