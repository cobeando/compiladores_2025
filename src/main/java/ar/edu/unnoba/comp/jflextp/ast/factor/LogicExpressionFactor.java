package ar.edu.unnoba.comp.jflextp.ast.factor;

public class LogicExpressionFactor extends Factor
    private Expression logicExpression;

    public LogicExpressionFactor(Expression logicExpression){
        this.logicExpression = logicExpression;
    }

    public String graficar(String idPadre){
        return logicExpression.graficar(idPadre);
    }
}
