package ar.edu.unnoba.comp.jflextp.ast.statement;

import ar.edu.unnoba.comp.jflextp.ast.expression.Expression;
import ar.edu.unnoba.comp.jflextp.ast.expression.StringConstant;
import ar.edu.unnoba.comp.jflextp.ast.llvm.CodeGeneratorHelper;

public class DisplayStatement extends Statement {
    private Expression expression;
    private StringConstant displayString;

    public DisplayStatement(Expression expression) {
        this.expression = expression;
    }

    public DisplayStatement(String string) {
        displayString = new StringConstant(string);
    }

    public String getEtiqueta() {
        return "DISPLAY";
    }

    public String graficar(String idPadre) {
        String thisId = this.getId();
        if (expression != null) {
            return super.graficar(idPadre)
                    + expression.graficar(thisId);
        }
        return super.graficar(idPadre)
                + displayString.graficar(thisId);
    }

    @Override
    public String toString(){
        if (expression == null){
            return String.format("DISPLAY(%s)", displayString);
        } else {
            return String.format("DISPLAY(%s)", expression);
        }
    }

    @Override
    public String generarCodigo() {
        if (expression != null) {
            StringBuilder resultado = new StringBuilder();
            resultado.append(expression.generarCodigo());
            String displayPointer = CodeGeneratorHelper.getNewPointer();

            switch (expression.getType()) {
                case INTEGER:
                    resultado.append(String.format("%1$s = call i32 (i8*, ...) @printf(i8* getelementptr([4 x i8], [4 x i8]* @.integer, i32 0, i32 0), i32 %2$s)\n",
                            displayPointer, expression.getIr_ref()));
                    return resultado.toString();
                case FLOAT:
                    resultado.append(String.format("%1$s = fpext float %2$s to double\n", displayPointer, expression.getIr_ref()));
                    resultado.append(String.format("%1$s = call i32 (i8*, ...) @printf(i8* getelementptr([4 x i8], [4 x i8]* @.float, i32 0, i32 0), double %2$s)\n",
                            CodeGeneratorHelper.getNewPointer(), displayPointer));
                    return resultado.toString();
                case DUPLE:
                    resultado.append(String.format("%1$s = fpext float %2$s to double\n", displayPointer, expression.getIr_ref()));
                    resultado.append(String.format("%1$s = call i32 (i8*, ...) @printf(i8* getelementptr([6 x i8], [6 x i8]* @.duple1, i32 0, i32 0), double %2$s)\n",
                            CodeGeneratorHelper.getNewPointer(), displayPointer));
                    String displayPointer2 = CodeGeneratorHelper.getNewPointer();

                    resultado.append(String.format("%1$s = fpext float %2$s to double\n", displayPointer2, expression.getIr_ref_2()));
                    resultado.append(String.format("%1$s = call i32 (i8*, ...) @printf(i8* getelementptr([5 x i8], [5 x i8]* @.duple2, i32 0, i32 0), double %2$s)\n",
                            CodeGeneratorHelper.getNewPointer(), displayPointer2));
                    return resultado.toString();
                default:
                    return "No implemented yet";
            }
        }
        return String.format(
                "%1$s = call i32 (i8*, ...) @printf(i8* getelementptr ([%2$d x i8], [%2$d x i8]* %3$s, i64 0, i64 0))\n",
                CodeGeneratorHelper.getNewPointer(), displayString.getLength(),
                CodeGeneratorHelper.getPointerForId(displayString.getEtiqueta()));
    }
}
