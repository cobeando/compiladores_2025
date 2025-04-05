package ar.edu.unnoba.comp.compilertp.ast.statement;

import ar.edu.unnoba.comp.compilertp.ast.expression.Expression;

public class RepeatStatement extends Statement {

    private StatementList codeBlock;
    private Expression condition;

    public RepeatStatement(Expression condition, StatementList codeBlock) {
        this.condition = condition;
        this.codeBlock = codeBlock;
    }

    public String getEtiqueta() {
        return "REPEAT";
    }

    public String graficar(String idPadre){
        String miId = this.getId();
        return super.graficar(idPadre) + condition.graficar(miId) + codeBlock.graficar(miId);
    }
}
