package ar.edu.unnoba.comp.jflextp.ast.factor;

public class Input {
    private String type;

    public Input(String type){
        this.type = type;
    }

    public String getEtiqueta(){
        return "INPUT " + type;
    }
}
