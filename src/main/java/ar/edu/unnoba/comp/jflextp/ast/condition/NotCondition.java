package ar.edu.unnoba.comp.jflextp.ast.condition;

public class NotCondition extends Condition{

    private final Condition condition;

    public NotCondition(Condition condition){
        this.condition = condition;
    }

    public String getEtiqueta(){
        return "NOT";
    }

    public String graficar(String idPadre){
        return 
            super.graficar(idPadre) + 
            this.condition.graficar(idPadre);
    }


    @Override
    public String toString(){
        return String.format("%s", this.condition);
    }

}
