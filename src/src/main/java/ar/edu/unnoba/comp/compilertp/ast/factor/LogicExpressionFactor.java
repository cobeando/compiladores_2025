package ar.edu.unnoba.comp.compilertp.ast.factor;

import ar.edu.unnoba.comp.compilertp.ast.expression.Expression;

//Workaround to handle the case when a logicExpression is between two parentesis and become a factor
public class LogicExpressionFactor extends Factor{
    private Expression logicExpression;

    public LogicExpressionFactor(Expression logicExpression){
        this.logicExpression = logicExpression;
    }

    public String graficar(String IdPadre){
        return logicExpression.graficar(IdPadre);
    }

}
