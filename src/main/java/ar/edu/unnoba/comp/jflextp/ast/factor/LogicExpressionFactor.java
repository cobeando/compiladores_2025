package ar.edu.unnoba.comp.jflextp.ast.factor;

import ar.edu.unnoba.comp.jflextp.ast.expression.Expression;
import ar.edu.unnoba.comp.jflextp.ast.llvm.CodeGeneratorHelper;

public class LogicExpressionFactor extends Factor{
    private Expression logicExpression;

    public LogicExpressionFactor(Expression logicExpression){
        this.logicExpression = logicExpression;
    }

    public String graficar(String idPadre){
        return logicExpression.graficar(idPadre);
    }

    @Override
    public String toString(){
        return String.format("%s", this.logicExpression);
    }

    public String generarCodigo() {
        String result = logicExpression.generarCodigo();
        setIr_ref(logicExpression.getIr_ref());
        return result;
    }
}
