package ar.edu.unnoba.comp.jflextp.ast.statement;

import ar.edu.unnoba.comp.jflextp.ast.expression.Expression;
import ar.edu.unnoba.comp.jflextp.ast.factor.Constant;

public class DisplayStatement extends Statement {
    private Expression expression;
    private Constant displayString;

    public DisplayStatement(Expression expression){
        this.expression = expression;
    }

    public DisplayStatement(String string){
        displayString = new Constant(string);
    }

    public String getEtiqueta(){
        return "DISPLAY";
    }

    public String graficar(String idPadre){
        String thisId = this.getId();
        if( expression != null){
            return super.graficar(idPadre)
                    + expression.graficar(thisId);
        }
        return super.graficar(idPadre)
                + displayString.graficar(thisId);
    }
}
