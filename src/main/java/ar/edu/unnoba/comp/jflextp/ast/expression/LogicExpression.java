package ar.edu.unnoba.comp.jflextp.ast.expression;

public class LogicExpression {
    private Expression left;
    private Expression right;
    private String operator;

    public LogicExpression(Expression left, Expression right, String operator){
        this.left = left;
        this.right = right;
        this.operator = operator;
    }

    public String getEtiqueta(){
        return operator;
    }

    public String graficar(String idPadre){
        String thisId = this.getId();
        return
            super.graficar(idPadre)
            + left.graficar(thisId)
            + right.graficar(thisId);
    }
}
