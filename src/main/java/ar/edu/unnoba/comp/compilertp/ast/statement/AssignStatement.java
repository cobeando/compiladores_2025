package ar.edu.unnoba.comp.compilertp.ast.statement;

import ar.edu.unnoba.comp.compilertp.ast.expression.Expression;

public class AssignStatement extends Statement {
    private Id id;
    private Expression expression;

    public AssignStatement(String id, Expression expression){
        this.id = new Id(id);
        this.expression = expression;
    }

    public String getEtiqueta(){
        return ":=";
    }

    public String graficar(String idPadre){
        String thisId = this.getId();
        return super.graficar(idPadre)
                + id.graficar(thisId)
                + expression.graficar(thisId);
    }
}
