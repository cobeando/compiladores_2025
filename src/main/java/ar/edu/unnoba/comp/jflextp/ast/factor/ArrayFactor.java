package ar.edu.unnoba.comp.jflextp.ast.factor;


import java.util.Objects;
import ar.edu.unnoba.comp.jflextp.ast.expression.Expression;

public class ArrayFactor extends Factor{
    private final String arrayId;
    private final Expression index;

    public ArrayFactor(String arrayId, Expression index) {
        this.arrayId = arrayId;
        this.index = index;
    }

    public String getArrayId() {
        return arrayId;
    }

    public Expression getIndex() {
        return index;
    }

    @Override
    public String toString() {
        return arrayId + "[" + index + "]";
    }

    @Override
    public String graficar(String idPadre) {
        StringBuilder grafico = new StringBuilder();
        String id = this.getId();

        grafico.append(String.format("%s[label=\"ArrayAccess: %s\"]\n", id, arrayId));
        if (idPadre != null) {
            grafico.append(String.format("%s--%s\n", idPadre, id));
        }

        grafico.append(index.graficar(id));

        return grafico.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ArrayFactor)) return false;
        ArrayFactor other = (ArrayFactor) o;
        return arrayId.equals(other.arrayId) && index.equals(other.index);
    }

    @Override
    public int hashCode() {
        return Objects.hash(arrayId, index);
    }
}
