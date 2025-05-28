package ar.edu.unnoba.comp.jflextp.ast.condition;

import ar.edu.unnoba.comp.jflextp.llvm.CodeGeneratorHelper;

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

    @Override
    public String generarCodigo() {
        this.setIr_ref(CodeGeneratorHelper.getNewPointer());
        return (String.format("%1$s =xor i1 0, %2$s\n", this.getIr_ref(),
                value ? 1 : 0));
    }
}
