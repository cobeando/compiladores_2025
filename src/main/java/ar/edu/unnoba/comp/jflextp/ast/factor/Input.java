package ar.edu.unnoba.comp.jflextp.ast.factor;

public class Input extends Factor{
    private String type;
    public Input(String type) {
        this.type = type;
    }

    public String getEtiqueta() {
        return "INPUT " + type;
    }

    @Override
    public String toString(){
        return String.format("%s", this.type);
    }
}
