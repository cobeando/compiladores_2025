package ar.edu.unnoba.comp.compilertp.ast.statement;

import ar.edu.unnoba.comp.compilertp.ast.expression.Expression;
import ar.edu.unnoba.comp.compilertp.ast.factor.Constant;

public class UnlessStatement extends Statement {
    private StatementList statements;
    private StatementList elseStatements;
    private Expression condition;

    public UnlessStatement(Expression condition, StatementList statements, StatementList elseStatements){
        this.condition = condition;
        this.statements = statements;
        this.elseStatements = elseStatements;
    }

    public UnlessStatement(Expression condition, StatementList statements){
        this.condition = condition;
        this.statements = statements;
    }

    public String getEtiqueta() {
        return "UNLESS";
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
        return graphic.toString();
    }
}
