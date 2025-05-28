package ar.edu.unnoba.comp.jflextp.ast.statement;

import ar.edu.unnoba.comp.jflextp.ast.expression.Expression;
import ar.edu.unnoba.comp.jflextp.ast.factor.Constant;
import ar.edu.unnoba.comp.jflextp.llvm.CodeGeneratorHelper;

public class BackwardConditionStatement extends ConditionStatement{
    public BackwardConditionStatement(Expression condition, StatementList statements, StatementList elseStatements) {
        super(condition, statements, elseStatements);
    }

    public BackwardConditionStatement(Expression condition, StatementList statements) {
        super(condition, statements);
    }

    @Override
    public String getEtiqueta() {
        return "BACKWARD_CONDITION";
    }

    @Override
    public String graficar(String idPadre) {
        final String miId = this.getId();
        StringBuilder graphic = new StringBuilder(super.graficar(idPadre));
        Constant thenConst = new Constant("THEN");
        graphic.append(this.getCondition().graficar(miId));
        graphic.append(thenConst.graficar(miId));
        graphic.append(this.getStatements().graficar(thenConst.getId()));
        if (this.getElseStatements() != null) {
            Constant elseConst = new Constant("ELSE_BACKWARD");
            graphic.append(elseConst.graficar(miId));
            graphic.append(this.getElseStatements().graficar(elseConst.getId()));
        }
        Constant endConst = new Constant("END");
        graphic.append(endConst.graficar(miId));
        return graphic.toString();
    }

    @Override
    public String toString(){
        if (getElseStatements() == null){
            return String.format("BACKWARD_CONDITION\n%s\nTHEN\n%s\nEND", getCondition(), getStatements());
        } else {
            return String.format("BACKWARD_CONDITION\n%s\nTHEN\n%s\nELSE_BACKWARD\n%s\nEND", getCondition(), getStatements(), getElseStatements());
        }
    }
    
    @Override
    public String generarCodigo(){
        StringBuilder resultado = new StringBuilder();
        this.setTag_True(CodeGeneratorHelper.getNewTag());
        this.setTag_False(CodeGeneratorHelper.getNewTag());
        this.setTag_End(CodeGeneratorHelper.getNewTag());
        resultado.append(this.getCondition().generarCodigo());
        resultado.append(String.format("br i1 %1$s, label %%%2$s, label %%%3$s \n\n", this.getCondition().getIr_ref(), this.getTag_True(),
                this.getElseStatements() == null? getTag_End() : this.getTag_False()));
        resultado.append(String.format("%s: \n\n", getTag_True()));
        resultado.append(this.getStatements().generarCodigo().replace("\n", "\n\t"));
        resultado.append(String.format("br label %%%s \n\n",getTag_End()));
        if (this.getElseStatements() != null){
            resultado.append(String.format("%s: \n\n", getTag_False()));
            resultado.append(this.getElseStatements().generarCodigo().replace("\n", "\n\t"));
            resultado.append(String.format("br label %%%s \n\n",getTag_End()));
        }
        resultado.append(String.format("%s: \n\n", getTag_End()));


        return resultado.toString();
    }
}
