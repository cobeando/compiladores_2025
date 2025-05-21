package ar.edu.unnoba.comp.jflextp.ast.factor;

public class Constant extends Factor{
    private String value;

    public Constant(String value){
        this.value = value;
    }

    public String getEtiqueta(){
        return value;
    }

    @Override
    public String toString(){
        return String.format("%s", this.value);
    }
}
