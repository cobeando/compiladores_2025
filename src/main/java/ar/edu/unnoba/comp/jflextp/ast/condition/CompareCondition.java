package ar.edu.unnoba.comp.jflextp.ast.condition;

import ar.edu.unnoba.comp.jflextp.ast.expression.Expression;
import ar.edu.unnoba.comp.jflextp.ast.factor.DataType;
import ar.edu.unnoba.comp.jflextp.llvm.CodeGeneratorHelper;

public class CompareCondition extends Condition{
    private Expression left;
    private Expression right;
    private Operator operator;

    public CompareCondition(Expression left, Operator operator, Expression right){
        this.left = left;
        this.right = right;
        this.operator = operator;
        setType(DataType.BOOLEAN);
    }


    public String updateType(){
        DataType leftType = left.getType();
        DataType rightType = right.getType();

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

    public String getEtiqueta(){
        return operator.getSymbol();
    }

    public String graficar(String idPadre) {
        final String miId = this.getId();
        return super.graficar(idPadre) +
                left.graficar(miId) +
                right.graficar(miId);
    }

    @Override
    public String toString(){
        return String.format("%s %s %s", this.left, this.operator, this.right);
    }

    @Override
    public String generarCodigo() {
        StringBuilder resultado = new StringBuilder();
        resultado.append(this.left.generarCodigo());
        resultado.append(this.right.generarCodigo());
        this.setIr_ref(CodeGeneratorHelper.getNewPointer());
        boolean isInteger= this.left.getType() == DataType.INTEGER && this.right.getType() == DataType.INTEGER;

        resultado.append(updateType());
        resultado.append(String.format("%1$s = %6$s %2$s %5$s %3$s, %4$s\n", this.getIr_ref(),
                this.operator.getLlvmCode(isInteger), this.left.getIr_ref(),
                this.right.getIr_ref(), isInteger ? "i32" : "float", isInteger ? "icmp" : "fcmp"));

        if (left.getType() == DataType.DUPLE || right.getType() == DataType.DUPLE) {
            String firstEvaluation = getIr_ref();
            String secondEvaluation = CodeGeneratorHelper.getNewPointer();
            this.setIr_ref(CodeGeneratorHelper.getNewPointer());
            resultado.append(String.format("%1$s = fcmp %2$s float %3$s, %4$s\n", secondEvaluation ,
                    this.operator.getLlvmCode(false), this.left.getIr_ref_2(),
                    this.right.getIr_ref_2()));
            resultado.append(String.format("%1$s = and i1 %2$s, %3$s\n", this.getIr_ref(),
                    firstEvaluation,
                    secondEvaluation));
        }

        return resultado.toString();
    }
}