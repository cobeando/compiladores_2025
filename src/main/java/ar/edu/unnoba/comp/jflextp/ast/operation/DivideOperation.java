package ar.edu.unnoba.comp.jflextp.ast.operation;

import ar.edu.unnoba.comp.jflextp.ast.expression.Expression;

public class DivideOperation extends BinaryOperation{
    public DivideOperation(Expression left, Expression right){
        super(left, right);
    }

    @Override
    protected String getOperationName() {
        return "/";
    }
}
