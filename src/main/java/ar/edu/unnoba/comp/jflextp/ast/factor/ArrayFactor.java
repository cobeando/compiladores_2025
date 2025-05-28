package ar.edu.unnoba.comp.jflextp.ast.factor;


import java.util.Objects;
import ar.edu.unnoba.comp.jflextp.ast.expression.Expression;
import ar.edu.unnoba.comp.jflextp.ast.llvm.CodeGeneratorHelper;

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
        return String.format("%s [ %s ]", this.arrayId, this.index);
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

    @Override
    public String generarCodigo() {
        StringBuilder codigo = new StringBuilder();
        // Generar código para el índice
        codigo.append(index.generarCodigo());
        String idx = index.getIr_ref();

        // Obtener punteros asociados al array
        String arrayPtr = CodeGeneratorHelper.getPointerForId(arrayId);       // float*
        String resultPtr = CodeGeneratorHelper.getNewPointer();               // float
        String value = CodeGeneratorHelper.getNewPointer();                   // valor final

        // Obtener el valor del elemento en la posición idx
        codigo.append(String.format(
            "%s = getelementptr float, float* %s, i32 %s\n",
            resultPtr, arrayPtr, idx
        ));
        codigo.append(String.format(
            "%s = load float, float* %s\n",
            value, resultPtr
        ));

        // Guardamos el valor en ir_ref para otros nodos que lo necesiten
        this.setIr_ref(value);

        return codigo.toString();
    }
}
