package ar.edu.unnoba.comp.jflextp.ast.factor;

import java.util.List;
import java.util.stream.Collectors;

import ar.edu.unnoba.comp.jflextp.ast.Nodo;
import ar.edu.unnoba.comp.jflextp.ast.expression.Expression;
import ar.edu.unnoba.comp.jflextp.ast.llvm.CodeGeneratorHelper;
import ar.edu.unnoba.comp.jflextp.ast.statement.AssignStatement;
import ar.edu.unnoba.comp.jflextp.ast.statement.StatementList;
import ar.edu.unnoba.comp.jflextp.ast.llvm.CodeGeneratorHelper;

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

    @Override
    public String generarCodigo(){
        StringBuilder resultado = new StringBuilder();
        setType(DataType.FLOAT);
        IdValue idValue = new IdValue(pivot);
        this.setIr_ref(CodeGeneratorHelper.getNewPointer());
        StatementList actionBlock = new StatementList( new AssignStatement("promrSum", new Constant("0", DataType.INTEGER)));
        actionBlock.addStatement(new AssignStatement("promrCount", new Constant("0", DataType.INTEGER)));

        for (String number : this.getNumberList().split(",")) {
            actionBlock.addStatement(getUnlessStatement(number, idValue));
        }
        actionBlock.addStatement(getResultStatement());
        resultado.append(actionBlock.generarCodigo());
        resultado.append((String.format("%1$s = load %2$s, %2$s* %3$s\n", this.getIr_ref(), DataType.FLOAT.getLlvmSymbol(), CodeGeneratorHelper.getPointerForId("promrSum"))));
        return resultado.toString();
    }

    public Object transformar(){
        Program program = new Program();
    }
}
