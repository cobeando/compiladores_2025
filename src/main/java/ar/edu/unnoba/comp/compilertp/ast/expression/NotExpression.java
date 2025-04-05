package ar.edu.unnoba.comp.compilertp.ast.expression;


public class NotExpression extends Expression{
    private final Expression expression;

    public NotExpression(Expression expression){
        this.expression = expression;
    }

    public String getEtiqueta(){
        return "NOT";
    }

    public String graficar(String idPadre) {
        return super.graficar(idPadre)
                + expression.graficar(this.getId());
    }
}
