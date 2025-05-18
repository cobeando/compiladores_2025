package ar.edu.unnoba.comp.jflextp.ast.factor;

import ar.edu.unnoba.comp.jflextp.ast.Nodo;
import ar.edu.unnoba.comp.jflextp.ast.expression.Expression;

public class AllFactor extends Factor{
    private Expression expression;
    private String operador;
    private String identificador;

    public AllFactor(Expression expression, String operador, String identificador) {
        this.expression = expression;
        this.operador = operador;
        this.identificador = identificador;
    }

    public Expression getExpression() {
        return expression;
    }

    public String getOperador() {
        return operador;
    }

    public String getIdentificador() {
        return identificador;
    }

    @Override
    public String getEtiqueta() {
        return "ALL";
    }

    @Override
    public String graficar(String idPadre) {
        String miId = this.getId();
        StringBuilder sb = new StringBuilder(super.graficar(idPadre));
        sb.append(expression.graficar(miId));

        Nodo op = new Nodo() {
            @Override
            public String getEtiqueta() {
                return operador;
            }
        };
        sb.append(op.graficar(miId));

        Nodo id = new Nodo() {
            @Override
            public String getEtiqueta() {
                return identificador;
            }
        };
        sb.append(id.graficar(miId));

        return sb.toString();
    }
}
