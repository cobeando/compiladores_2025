package ar.edu.unnoba.comp.jflextp.ast.factor;

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
}
