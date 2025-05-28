package ar.edu.unnoba.comp.jflextp.ast.operation;

import ar.edu.unnoba.comp.jflextp.ast.expression.Expression;

public class DivideOperation  extends BinaryOperation{


    public DivideOperation(Expression left, Expression right){
        super(left, right);
    }

    @Override
    protected String getOperationName() {
        return "/";
    }

     @Override
    public String get_llvm_op_code(boolean isFloat) {
        return  isFloat ? "fdiv" : "sdiv";
    }    

    @Override
    public String toString(){
        return String.format("(%s / %s)", left, right);
    }
}
