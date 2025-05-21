package ar.edu.unnoba.comp.jflextp.ast.operation;

import ar.edu.unnoba.comp.jflextp.ast.factor.Factor;
import ar.edu.unnoba.comp.jflextp.ast.expression.Expression;

public abstract class BinaryOperation extends Factor{
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
