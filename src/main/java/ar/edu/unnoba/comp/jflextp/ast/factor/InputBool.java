package ar.edu.unnoba.comp.jflextp.ast.factor;

public class InputBool extends Factor{
    private DataType type;
    public InputBool(DataType type) {
        this.type = type;
    }

    public String getEtiqueta() {
        return "INPUT BOOL";
    }

    @Override
    public String toString(){
        return String.format("BOOL");
    }

    @Override
    public String generarCodigo() {
        return null;
    }
}
