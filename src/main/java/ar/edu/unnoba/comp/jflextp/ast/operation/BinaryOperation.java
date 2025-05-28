package ar.edu.unnoba.comp.jflextp.ast.operation;

import ar.edu.unnoba.comp.jflextp.ast.factor.DataType;
import ar.edu.unnoba.comp.jflextp.ast.factor.Factor;
import ar.edu.unnoba.comp.jflextp.ast.llvm.CodeGeneratorHelper;
import ar.edu.unnoba.comp.jflextp.ast.expression.Expression;

public abstract class BinaryOperation extends Factor{
    protected final Expression left;
    protected final Expression right;

    private DataType type;

    public BinaryOperation(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    public String updateType(){
        DataType leftType = left.getType();
        DataType rightType = right.getType();
        type = leftType.updateType(rightType);

        if (leftType == rightType){
            return "";
        }
        if (leftType == DataType.DUPLE){
            return CodeGeneratorHelper.convertToDuple(right);
        }
        if (rightType == DataType.DUPLE){
            return CodeGeneratorHelper.convertToDuple(left);
        }
        if (leftType == DataType.FLOAT){
            return CodeGeneratorHelper.convertToFloat(right);
        }
        return CodeGeneratorHelper.convertToFloat(left);

    }

    public DataType getType() {
        return type;
    }

    public Expression getLeft() {
        return left;
    }

    public Expression getRight() {
        return right;
    }

    @Override
    public String getEtiqueta() {
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

    public abstract String get_llvm_op_code(boolean isFloat);

    @Override
    public abstract String toString();
    
    @Override
    public String generarCodigo(){
        StringBuilder resultado = new StringBuilder();
        resultado.append(this.getLeft().generarCodigo());
        resultado.append(this.getRight().generarCodigo());
        resultado.append(updateType());
        boolean isInteger = type.equals(DataType.INTEGER);
        this.setIr_ref(CodeGeneratorHelper.getNewPointer());
        resultado.append(String.format("%1$s = %2$s %5$s %3$s, %4$s\n", this.getIr_ref(),
                this.get_llvm_op_code(!isInteger), this.getLeft().getIr_ref(),
                this.getRight().getIr_ref(), isInteger ? type.getLlvmSymbol() : DataType.FLOAT.getLlvmSymbol()));
        if(type.equals(DataType.DUPLE)){
            this.setIr_ref_2(CodeGeneratorHelper.getNewPointer());
            resultado.append(String.format("%1$s = %2$s %5$s %3$s, %4$s\n", this.getIr_ref_2(),
                    this.get_llvm_op_code(!isInteger), this.getLeft().getIr_ref_2(),
                    this.getRight().getIr_ref_2(), DataType.FLOAT.getLlvmSymbol()));
        }
        return resultado.toString();
    }
}
