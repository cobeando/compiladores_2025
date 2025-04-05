package ar.edu.unnoba.comp.compilertp.ast.statement;

import ar.edu.unnoba.comp.compilertp.ast.expression.Expression;
import ar.edu.unnoba.comp.compilertp.ast.factor.Constant;

public class ShowStatement extends Statement {
    private Expression expression;
    private Constant showString;

    public ShowStatement(Expression expression){
        this.expression = expression;
    }

    public ShowStatement(String string){
        showString = new Constant(string);
    }

    public String getEtiqueta(){
        return "Show";
    }

    public String graficar(String idPadre){
        String thisId = this.getId();
        if( expression != null){
            return super.graficar(idPadre)
                    + expression.graficar(thisId);
        }
        return super.graficar(idPadre)
                + showString.graficar(thisId);
    }
}
