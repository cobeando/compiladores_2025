package ar.edu.unnoba.comp.jflextp.ast.statement;

import ar.edu.unnoba.comp.jflextp.ast.expression.Expression;
import ar.edu.unnoba.comp.jflextp.ast.factor.Constant;

public class LoopStatement extends Statement{
    protected Expression condition;
    protected StatementList statements;

    public LoopStatement(Expression condition, StatementList statements) {
        this.condition = condition;
        this.statements = statements;
    }

    @Override
    public String getEtiqueta() {
        return "LOOP";
    }

    protected String graficarEtiqueta(String idPadre) {
        return super.graficar(idPadre);
    }

    @Override
    public String graficar(String idPadre) {
        final String miId = this.getId();
        StringBuilder graphic = new StringBuilder(graficarEtiqueta(idPadre));

        Constant whenConst = new Constant("WHEN");
        graphic.append(condition.graficar(miId));
        graphic.append(whenConst.graficar(miId));

        Constant thenConst = new Constant("THEN");
        graphic.append(thenConst.graficar(miId));
        graphic.append(statements.graficar(thenConst.getId()));

        Constant endConst = new Constant("END_LOOP");
        graphic.append(endConst.graficar(miId));

        return graphic.toString();
    }

    public Expression getCondition() {
        return condition;
    }

    public StatementList getStatements() {
        return statements;
    }
}
