package ar.edu.unnoba.comp.jflextp.ast.statement;

import ar.edu.unnoba.comp.jflextp.ast.expression.Expression;
import ar.edu.unnoba.comp.jflextp.ast.factor.Constant;

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
        StringBuilder graphic = new StringBuilder(super.graficarEtiqueta(idPadre)); // nuevo método protegido

        Constant thenConst = new Constant("THEN");
        graphic.append(getCondition().graficar(miId));
        graphic.append(thenConst.graficar(miId));
        graphic.append(getStatements().graficar(thenConst.getId()));
        if (getElseStatements() != null) {
            Constant elseConst = new Constant("ELSE_BACKWARD");
            graphic.append(elseConst.graficar(miId));
            graphic.append(getElseStatements().graficar(elseConst.getId()));
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
}
