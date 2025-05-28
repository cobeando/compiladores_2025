package ar.edu.unnoba.comp.jflextp.ast.factor;

import ar.edu.unnoba.comp.jflextp.ast.llvm.CodeGeneratorHelper;

public class MinusFactor extends Factor{
    private Factor factor;

    public MinusFactor(Factor factor){
        this.factor = factor;
    }

    public String getEtiqueta(){
        return "-";
    }

    @Override
    public String graficar(String idPadre){
        return
            super.graficar(idPadre)
            + factor.graficar(this.getId());
    }

    @Override
    public String toString(){
        return String.format("%s", this.factor);
    }

    @Override
    public String generarCodigo() {
        return String.format("%1$s = mul i32 %2$s, -1 \n", this.getIr_ref(), this.factor.getIr_ref());
    }
}
