package ar.edu.unnoba.comp.jflextp.ast.expression;

public class NotExpression {
    private final Expression expression;

    public NotExpression(Expression expression){
        this.expression = expression;
    }

    public String getEtiqueta(){
        return "NOT";
    }

    public String graficar(String idPadre){
        return
            super.graficar(idPadre)
            + expression.graficar(this.getId());
    }

}
