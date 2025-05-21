package ar.edu.unnoba.comp.jflextp.ast.condition;

public class BooleanCondition extends Condition{
    private final boolean value;

    public BooleanCondition(boolean value){
        this.value = value;
    }

    public String getEtiqueta(){
        return value ? "TRUE" : "FALSE";
    }

    public String graficar(String idPadre){
        return super.graficar(idPadre);
    }

    @Override
    public String toString(){
        return "BOOLEAN CONDITION";
    }
}
