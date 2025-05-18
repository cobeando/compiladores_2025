package ar.edu.unnoba.comp.jflextp.ast.statement;

import ar.edu.unnoba.comp.jflextp.ast.expression.Expression;

public class AssignStatement extends Statement {
    private Id id;
    private Expression expression1;
    private Expression expression2;

    public AssignStatement(String id, Expression expression){
        this.id = new Id(id);
        this.expression1 = expression;
    }

    public AssignStatement(String id, Expression expression1, Expression expression2){
        this.id = new Id(id);
        this.expression1 = expression1;
        this.expression2 = expression2;
    }

    public String getEtiqueta(){
        return ":=";
    }

    public String graficar(String idPadre){
        String thisId = this.getId();
        return super.graficar(idPadre)
                + id.graficar(thisId)
                + expression1.graficar(thisId)
                + expression2.graficar(thisId);
    }
}