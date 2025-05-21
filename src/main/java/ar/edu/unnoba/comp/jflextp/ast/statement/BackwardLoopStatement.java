package ar.edu.unnoba.comp.jflextp.ast.statement;

import ar.edu.unnoba.comp.jflextp.ast.expression.Expression;
import ar.edu.unnoba.comp.jflextp.ast.factor.Constant;

public class BackwardLoopStatement extends LoopStatement{
    public BackwardLoopStatement(Expression condition, StatementList statements) {
        super(condition, statements);
    }

    @Override
    public String getEtiqueta() {
        return "BACKWARD_LOOP_WHEN";
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
        // NOTA: el orden de ejecución sería inverso, pero para graficar probablemente se muestre igual
        graphic.append(statements.graficar(thenConst.getId()));

        Constant endConst = new Constant("END_LOOP");
        graphic.append(endConst.graficar(miId));

        return graphic.toString();
    }

    @Override
    public String toString(){
        return String.format("BACKWARD_LOOP WHEN\n%s\nTHEN\n%s\nEND_LOOP", getCondition(), getStatements());
    }
}
