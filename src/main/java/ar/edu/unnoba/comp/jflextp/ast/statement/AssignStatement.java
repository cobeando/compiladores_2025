package ar.edu.unnoba.comp.jflextp.ast.statement;

import ar.edu.unnoba.comp.jflextp.ast.factor.DataType;

import ar.edu.unnoba.comp.jflextp.ast.expression.Expression;
import ar.edu.unnoba.comp.jflextp.ast.llvm.CodeGeneratorHelper;

public class AssignStatement extends Statement {
    private Id idAssignment;
    private Expression expression1;
    private Expression expression2;

    public AssignStatement(String id, Expression expression){
        this.idAssignment = new Id(id);
        this.expression1 = expression;
    }

    public AssignStatement(String id, Expression expression1, Expression expression2){
        this.expression1 = expression1;
        this.expression2 = expression2;
        this.idAssignment = new Id(id);
    }

    public String getEtiqueta(){
        return ":=";
    }

    public String graficar(String idPadre){
        String thisId = this.getId();


        if (expression2 == null){
             return super.graficar(idPadre)
                + idAssignment.graficar(thisId)
                + expression1.graficar(thisId);
        }
        return super.graficar(idPadre)
                + idAssignment.graficar(thisId)
                + expression1.graficar(thisId)
                + expression2.graficar(thisId);
    }

    @Override
    public String toString(){
        if (expression2 == null){
            return String.format("%s = %s", idAssignment, expression1);
        } else {
            return String.format("%s[%s] = %s", idAssignment, expression1, expression2);
        }
    }

    @Override
    public String generarCodigo() {
        StringBuilder resultado = new StringBuilder();
        resultado.append(expression.generarCodigo());
        DataType destinationType = CodeGeneratorHelper.getTypeForId(idAssignment.getEtiqueta());
        DataType expressionType = expression.getType();
        String globalPointer = CodeGeneratorHelper.getPointerForId(idAssignment.getEtiqueta());


        if (!destinationType.compatibleWith(expressionType)) {
            throw new RuntimeException("Error in " + idAssignment.getId() + " assignment statement. \n" +
                    "Incompatible types in assign statement " +
                    "destineType:" + destinationType + " and expressionType:" + expressionType + ".");
        }

        CodeGeneratorHelper.setVariableAsInitialized(idAssignment.getEtiqueta());

        if (destinationType != DataType.INTEGER && expressionType == DataType.INTEGER) {
            resultado.append(CodeGeneratorHelper.convertToFloat(expression));
            return resultado.toString();
        }


        if (destinationType != DataType.DUPLE) {
            resultado.append(String.format("store %1$s %2$s, %1$s* %3$s\n", destinationType.getLlvmSymbol(), expression.getIr_ref(), globalPointer));
            return resultado.toString();
        }

        String secondGlobalPointer = CodeGeneratorHelper.getSecondPointerForId(idAssignment.getEtiqueta());

        resultado.append(String.format("store %1$s %2$s, %1$s* %3$s\n", DataType.FLOAT.getLlvmSymbol(), expression.getIr_ref(), globalPointer));
        resultado.append(String.format("store %1$s %2$s, %1$s* %3$s\n", DataType.FLOAT.getLlvmSymbol(), expression.getIr_ref_2(), secondGlobalPointer));
        return resultado.toString();
    }
}