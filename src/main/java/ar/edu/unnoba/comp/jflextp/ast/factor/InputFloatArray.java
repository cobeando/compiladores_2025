package ar.edu.unnoba.comp.jflextp.ast.factor;

public class InputFloatArray extends Factor{
    private  DataType type;
    public InputFloatArray(){
        this.type = DataType.FLOAT_ARRAY;
    }

    public String getEtiqueta() {
        return "INPUT FLOAT_ARRAY";
    }

    @Override
    public String toString(){
        return String.format("FLOAT_ARRAY");
    }

    @Override
    public String generarCodigo() {
        return null;
    }
}
