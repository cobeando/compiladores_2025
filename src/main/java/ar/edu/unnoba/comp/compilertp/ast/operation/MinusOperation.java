package ar.edu.unnoba.comp.compilertp.ast.operation;

import ar.edu.unnoba.comp.compilertp.ast.expression.Expression;

public class MinusOperation extends BinaryOperation {

        public MinusOperation(Expression left, Expression right) {
            super(left, right);
        }

        @Override
        protected String getOperationName() {
            return "-";
        }
}
