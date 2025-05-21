package ar.edu.unnoba.comp.jflextp.ast.statement;

import ar.edu.unnoba.comp.jflextp.ast.expression.Expression;
import ar.edu.unnoba.comp.jflextp.ast.factor.Constant;

public class ConditionStatement extends Statement {
    private StatementList statements;
    private StatementList elseStatements;
    private Expression condition;

    public ConditionStatement(Expression condition, StatementList statements, StatementList elseStatements){
        this.condition = condition;
        this.statements = statements;
        this.elseStatements = elseStatements;
    }

    public ConditionStatement(Expression condition, StatementList statements){
        this(condition, statements, null);
    }

    // Métodos accesibles desde la subclase
    protected Expression getCondition() {
        return condition;
    }

    protected StatementList getStatements() {
        return statements;
    }

    protected StatementList getElseStatements() {
        return elseStatements;
    }

    @Override
    public String getEtiqueta() {
        return "CONDITION";
    }

    // Método separado para reutilizar en subclases
    protected String graficarEtiqueta(String idPadre) {
        return super.graficar(idPadre);
    }

    @Override
    public String graficar(String idPadre) {
        final String miId = this.getId();
        StringBuilder graphic = new StringBuilder(graficarEtiqueta(idPadre));
        Constant thenConst = new Constant("THEN");
        graphic.append(condition.graficar(miId));
        graphic.append(thenConst.graficar(miId));
        graphic.append(statements.graficar(thenConst.getId()));
        if (elseStatements != null) {
            Constant elseConst = new Constant("ELSE");
            graphic.append(elseConst.graficar(miId));
            graphic.append(elseStatements.graficar(elseConst.getId()));
        }

        Constant endConstant = new Constant("END");
        graphic.append(endConstant.graficar(miId));
        
        return graphic.toString();
    }

    @Override
    public String toString(){
        if (getElseStatements() == null){
            return String.format("CONDITION\n%s\nTHEN\n%s\nEND", getCondition(), getStatements());
        } else {
            return String.format("CONDITION\n%s\nTHEN\n%s\nELSE\n%s\nEND", getCondition(), getStatements(), getElseStatements());
        }
    }
}
