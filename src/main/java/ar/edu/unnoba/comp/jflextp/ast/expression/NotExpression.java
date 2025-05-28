package ar.edu.unnoba.comp.jflextp.ast.expression;

public class NotExpression extends Expression{
    private final Expression expression;

    public NotExpression(Expression expression){
        this.expression = expression;
    }

    public Expression getExpression(){
        return expression;
    }

    public String getEtiqueta(){
        return "NOT";
    }

    public String graficar(String idPadre) {
        return super.graficar(idPadre)
                + expression.graficar(this.getId());
    }

    @Override
    public String toString(){
        return String.format("%s", this.expression);
    }

    @Override
    public String generarCodigo(){
        StringBuilder resultado = new StringBuilder();
        resultado.append(String.format("%1$s = xor i1 %2$s, true \n", this.getIr_ref(), this.getExpression().getIr_ref()));

        return resultado.toString();
    }
}
