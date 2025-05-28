package ar.edu.unnoba.comp.jflextp.ast.expression;

import ar.edu.unnoba.comp.jflextp.ast.factor.DataType;
import ar.edu.unnoba.comp.jflextp.ast.llvm.CodeGeneratorHelper;
import ar.edu.unnoba.comp.jflextp.ast.condition.Operator;

public class LogicExpression extends Expression{
    private Expression left;
    private Expression right;
    private Operator operator;

    public LogicExpression(Expression left, Expression right, Operator operator){
        this.left = left;
        this.right = right;
        this.operator = operator;
        setType(DataType.BOOLEAN);
    }

    public String getEtiqueta(){
        return operator.getSymbol();
    }

    public Expression getLeft() {
        return left;
    }

    public Expression getRight() {
        return right;
    }

    public Operator getOperator() {
        return operator;
    }

    public String graficar(String idPadre){
        String thisId = this.getId();
        return super.graficar(idPadre)
                + left.graficar(thisId)
                + right.graficar(thisId);
    }

    @Override
    public String toString(){
        return String.format("%s %s %s", this.left, this.operator, this.right);
    }

     @Override
    public String generarCodigo(){
        StringBuilder resultado = new StringBuilder();        
        resultado.append(this.getRight().generarCodigo());
        resultado.append(this.getLeft().generarCodigo());
        this.setIr_ref(CodeGeneratorHelper.getNewPointer());
        resultado.append(String.format("%1$s = %2$s i1 %3$s, %4$s\n", this.getIr_ref(), 
                this.getOperator().getLlvmCode(false), this.left.getIr_ref(),
                this.right.getIr_ref()));
        return resultado.toString();
    }

}
