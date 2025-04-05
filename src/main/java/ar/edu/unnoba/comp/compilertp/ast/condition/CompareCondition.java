package ar.edu.unnoba.comp.compilertp.ast.condition;

import ar.edu.unnoba.comp.compilertp.ast.expression.Expression;

public class CompareCondition extends Condition {
    private Expression left;
    private Expression right;
    private String operator;

    public CompareCondition(Expression left, String operator, Expression right){
        this.left = left;
        this.right = right;
        this.operator = operator;
    }

    public String getEtiqueta(){
        return operator;
    }

    public String graficar(String idPadre) {
        final String miId = this.getId();
        return super.graficar(idPadre) +
                left.graficar(miId) +
                right.graficar(miId);
    }
}
