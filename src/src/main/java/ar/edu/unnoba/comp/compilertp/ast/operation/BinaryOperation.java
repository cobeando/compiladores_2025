package ar.edu.unnoba.comp.compilertp.ast.operation;

import ar.edu.unnoba.comp.compilertp.ast.expression.Expression;
import ar.edu.unnoba.comp.compilertp.ast.factor.Factor;

public abstract class BinaryOperation extends Factor {
    protected final Expression left;
    protected final Expression right;

    public BinaryOperation(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    public Expression getLeft() {
        return left;
    }

    public Expression getRight() {
        return right;
    }

    @Override
    protected String getEtiqueta() {
        return String.format("%s", this.getOperationName());
    }

    protected abstract String getOperationName();

    @Override
    public String graficar(String idPadre) {
        final String miId = this.getId();
        return super.graficar(idPadre) +
                left.graficar(miId) +
                right.graficar(miId);
    }
}
