package ar.edu.unnoba.comp.jflextp.ast.factor;

import java.util.List;
import java.util.stream.Collectors;

import ar.edu.unnoba.comp.jflextp.ast.Nodo;
import ar.edu.unnoba.comp.jflextp.ast.expression.Expression;

public class ValorMasCercano extends Factor{
    private Expression referencia;
    private List<Expression> lista;

    public ValorMasCercano(Expression referencia, List<Expression> lista) {
        this.referencia = referencia;
        this.lista = lista;
        if (lista.isEmpty()) {
            System.out.println("Advertencia: La lista está vacía en ValorMasCercano.");
        }
    }

    public Expression getReferencia() {
        return referencia;
    }

    public List<Expression> getLista() {
        return lista;
    }

    @Override
    public String getEtiqueta() {
        return "valor_mas_cercano";
    }

    @Override
    public String graficar(String idPadre) {
        String miId = this.getId();
        StringBuilder sb = new StringBuilder(super.graficar(idPadre));

        sb.append(referencia.graficar(miId));

        Nodo listaNodo = new Nodo("Lista") {};
        sb.append(listaNodo.graficar(miId));

        for (Expression expr : lista) {
            sb.append(expr.graficar(listaNodo.getId()));
        }

        return sb.toString();
    }

    @Override
    public String toString(){
        String aux = lista.stream()
            .map(Expression::toString)
            .collect(Collectors.joining(", "));

        return String.format("valor_mas_cercano(%s[%s])", this.referencia, aux);
    }
}
