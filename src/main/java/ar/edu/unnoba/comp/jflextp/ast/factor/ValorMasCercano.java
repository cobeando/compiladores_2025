package ar.edu.unnoba.comp.jflextp.ast.factor;

import java.util.List;
import java.util.stream.Collectors;

import ar.edu.unnoba.comp.jflextp.ast.Nodo;
import ar.edu.unnoba.comp.jflextp.ast.expression.Expression;
import ar.edu.unnoba.comp.jflextp.ast.statement.AssignStatement;
import ar.edu.unnoba.comp.jflextp.ast.statement.StatementList;
import ar.edu.unnoba.comp.jflextp.llvm.CodeGeneratorHelper;

public class ValorMasCercano extends Factor{
    public static String VMC_EMPTY_LIST = "La lista de números está vacía, resultado: 0";
    public static String VMC_RESULT = "El valor mas cercano es: ";
    private Expression referencia;
    private List<Expression> lista;
    private String ir_ref_vmc;
    private String tagTrue;
    private String tagFalse;
    private String tagEnd;
    private String resultVMC;

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

        Nodo listaNodo = new Nodo("Lista") {
            @Override
            public String generarCodigo() {
                return ""; // No hace nada en código LLVM, solo es para graficar
            }
        };
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

    @Override
public String generarCodigo() {
    StringBuilder codigo = new StringBuilder();
    this.setType(DataType.FLOAT);

    // Generar código para la referencia
    codigo.append(referencia.generarCodigo());

    String pivotPtr = CodeGeneratorHelper.getNewPointer();
    codigo.append(String.format("%s = alloca float\n", pivotPtr));
    codigo.append(String.format("store float %s, float* %s\n", referencia.getIr_ref(), pivotPtr));

    // Inicializamos el resultado con el primer valor de la lista
    Expression primerValor = lista.get(0);
    codigo.append(primerValor.generarCodigo());

    String resultPtr = CodeGeneratorHelper.getNewPointer();
    this.setIr_ref(resultPtr);
    codigo.append(String.format("%s = alloca float\n", resultPtr));
    codigo.append(String.format("store float %s, float* %s\n", primerValor.getIr_ref(), resultPtr));

    String pivotTemp = CodeGeneratorHelper.getNewTemp();
    codigo.append(String.format("%s = load float, float* %s\n", pivotTemp, pivotPtr));

    // Calcular la distancia inicial: abs(referencia - primerValor)
    String diffInit = CodeGeneratorHelper.getNewTemp();
    codigo.append(String.format("%s = fsub float %s, %s\n", diffInit, pivotTemp, primerValor.getIr_ref()));

    String absInit = CodeGeneratorHelper.getNewTemp();
    codigo.append(String.format("%s = call float @llvm.fabs.f32(float %s)\n", absInit, diffInit));

    String closestDiffPtr = CodeGeneratorHelper.getNewPointer();
    codigo.append(String.format("%s = alloca float\n", closestDiffPtr));
    codigo.append(String.format("store float %s, float* %s\n", absInit, closestDiffPtr));

    // Recorremos el resto de la lista (empezando desde el índice 1)
    for (int i = 1; i < lista.size(); i++) {
        Expression expr = lista.get(i);
        codigo.append(expr.generarCodigo());

        // Calculamos diferencia con el pivot
        String diff = CodeGeneratorHelper.getNewTemp();
        codigo.append(String.format("%s = fsub float %s, %s\n", diff, pivotTemp, expr.getIr_ref()));

        String absDiff = CodeGeneratorHelper.getNewTemp();
        codigo.append(String.format("%s = call float @llvm.fabs.f32(float %s)\n", absDiff, diff));

        // Comparamos con el mejor hasta ahora
        String prevDiff = CodeGeneratorHelper.getNewTemp();
        codigo.append(String.format("%s = load float, float* %s\n", prevDiff, closestDiffPtr));

        String cmp = CodeGeneratorHelper.getNewTemp();
        codigo.append(String.format("%s = fcmp olt float %s, %s\n", cmp, absDiff, prevDiff));

        // Generar etiquetas
        String tagUpdate = CodeGeneratorHelper.getNewLabel("closer");
        String tagSkip = CodeGeneratorHelper.getNewLabel("skip");
        String tagEnd = CodeGeneratorHelper.getNewLabel("end");

        // Branch según comparación
        codigo.append(String.format("br i1 %s, label %%%s, label %%%s\n", cmp, tagUpdate, tagSkip));

        // Si es más cercano: actualizar el valor
        codigo.append(String.format("%s:\n", tagUpdate));
        codigo.append(String.format("store float %s, float* %s\n", absDiff, closestDiffPtr));
        codigo.append(String.format("store float %s, float* %s\n", expr.getIr_ref(), resultPtr));
        codigo.append(String.format("br label %%%s\n", tagEnd));

        // Salto si no se actualiza
        codigo.append(String.format("%s:\n", tagSkip));
        codigo.append(String.format("br label %%%s\n", tagEnd));

        // Etiqueta de unión
        codigo.append(String.format("%s:\n", tagEnd));
    }

    // Cargar resultado final
    String finalResult = CodeGeneratorHelper.getNewTemp();
    codigo.append(String.format("%s = load float, float* %s\n", finalResult, resultPtr));
    this.setIr_ref(finalResult);

    return codigo.toString();
    }
}
