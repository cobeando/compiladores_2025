package ar.edu.unnoba.comp.compilertp.ast.factor;

public class Constant extends Factor{

    private String value;

    public Constant(String value){
        this.value = value;
    }

    public String getEtiqueta(){
        return  value;
    }
}
