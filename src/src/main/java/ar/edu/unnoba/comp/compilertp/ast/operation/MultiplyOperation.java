package ar.edu.unnoba.comp.compilertp.ast.operation;

import ar.edu.unnoba.comp.compilertp.ast.expression.Expression;

public class MultiplyOperation extends BinaryOperation{
    public MultiplyOperation(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    protected String getOperationName() {
        return "*";
    }
}
