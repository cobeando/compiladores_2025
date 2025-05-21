package ar.edu.unnoba.comp.jflextp.ast.operation;

import ar.edu.unnoba.comp.jflextp.ast.expression.Expression;

public class PlusOperation extends BinaryOperation{
    public PlusOperation(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    protected String getOperationName() {
        return "+";
    }

    @Override
    public String toString(){
        return String.format("$s + %s", left, right);
    }
}
