package ar.edu.unnoba.comp.jflextp.ast.statement;

import ar.edu.unnoba.comp.jflextp.ast.expression.Expression;
import ar.edu.unnoba.comp.jflextp.ast.factor.Constant;
import ar.edu.unnoba.comp.jflextp.ast.llvm.CodeGeneratorHelper;

public class ConditionStatement extends Statement {
    private StatementList statements;
    private StatementList elseStatements;
    private Expression condition;
    private String tagTrue;
    private String tagFalse;
    private String tagEnd;

    public ConditionStatement(Expression condition, StatementList statements, StatementList elseStatements){
        this.condition = condition;
        this.statements = statements;
        this.elseStatements = elseStatements;
    }

    public ConditionStatement(Expression condition, StatementList statements){
        this.condition = condition;
        this.statements = statements;
    }

    public String getEtiqueta() {
        return "CONDITION";
    }

     public Expression getCondition() {
        return condition;
    }

    public StatementList getStatements(){
        return statements;
    }

    public StatementList getElseStatements(){
        return elseStatements;
    }


    public String getTag_True() {
        return tagTrue;
    }

    public void setTag_True(String tagTrue) {
        this.tagTrue = tagTrue;
    }

        public String getTag_False() {
        return tagFalse;
    }

    public void setTag_False(String tagFalse) {
        this.tagFalse = tagFalse;
    }

    public String getTag_End() {
        return tagEnd;
    }

    public void setTag_End(String tagEnd) {
        this.tagEnd = tagEnd;
    }

    @Override
    public String toString(){
        if (getElseStatements() == null){
            return String.format("CONDITION\n%s\nTHEN\n%s\nEND", getCondition(), getStatements());
        } else {
            return String.format("CONDITION\n%s\nTHEN\n%s\nELSE\n%s\nEND", getCondition(), getStatements(), getElseStatements());
        }
    }

    public String graficar(String idPadre) {
        final String miId = this.getId();
        StringBuilder graphic = new StringBuilder(super.graficar(idPadre));
        Constant thenConst = new Constant("THEN");
        graphic.append(condition.graficar(miId));
        graphic.append(thenConst.graficar(miId));
        graphic.append(statements.graficar(thenConst.getId()));
        if (elseStatements != null) {
            Constant elseConst = new Constant("ELSE");
            graphic.append(elseConst.graficar(miId));
            graphic.append(elseStatements.graficar(elseConst.getId()));
        }
        Constant endConst = new Constant("END");
        graphic.append(endConst.graficar(miId));
        return graphic.toString();
    }

    @Override
    public String generarCodigo(){
        StringBuilder resultado = new StringBuilder();
        this.setTag_True(CodeGeneratorHelper.getNewTag());
        this.setTag_False(CodeGeneratorHelper.getNewTag());
        this.setTag_End(CodeGeneratorHelper.getNewTag());
        resultado.append(this.getCondition().generarCodigo());
        resultado.append(String.format("br i1 %1$s, label %%%2$s, label %%%3$s \n\n", this.getCondition().getIr_ref(), this.getTag_True(),
                elseStatements == null? getTag_End() : this.getTag_False()));
        resultado.append(String.format("%s: \n\n", getTag_True()));
        resultado.append(this.statements.generarCodigo().replace("\n", "\n\t"));
        resultado.append(String.format("br label %%%s \n\n",getTag_End()));
        if (this.elseStatements != null){
            resultado.append(String.format("%s: \n\n", getTag_False()));
            resultado.append(this.elseStatements.generarCodigo().replace("\n", "\n\t"));
            resultado.append(String.format("br label %%%s \n\n",getTag_End()));
        }
        resultado.append(String.format("%s: \n\n", getTag_End()));


        return resultado.toString();
    }
}
