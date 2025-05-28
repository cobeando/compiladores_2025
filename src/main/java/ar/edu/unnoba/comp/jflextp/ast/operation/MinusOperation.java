package ar.edu.unnoba.comp.jflextp.ast.operation;

import ar.edu.unnoba.comp.jflextp.ast.expression.Expression;

public class MinusOperation extends BinaryOperation {

        public MinusOperation(Expression left, Expression right) {
            super(left, right);
        }

        @Override
        protected String getOperationName() {
            return "-";
        }

        @Override
        public String get_llvm_op_code(boolean isFloat) {
                return isFloat ? "fsub" : "sub";
        }
}
