package ar.edu.unnoba.comp.jflextp.ast.factor;

import ar.edu.unnoba.comp.jflextp.ast.Nodo;
import ar.edu.unnoba.comp.jflextp.ast.expression.Expression;
import ar.edu.unnoba.comp.jflextp.llvm.CodeGeneratorHelper;

public class AnyFactor extends Factor{
    private Expression expression;
    private String operador;
    private String identificador;

    public AnyFactor(Expression expression, String operador, String identificador) {
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
        return "ANY";
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
            @Override
            public String generarCodigo() {
                return ""; // No hace nada en código LLVM, solo es para graficar
            }
        };
        sb.append(op.graficar(miId));

        Nodo id = new Nodo() {
            @Override
            public String getEtiqueta() {
                return identificador;
            }
            @Override
            public String generarCodigo() {
                return ""; // No hace nada en código LLVM, solo es para graficar
            }
        };
        sb.append(id.graficar(miId));

        return sb.toString();
    }

    @Override
    public String toString(){
        return String.format("any(%s %s %s)", this.expression, this.operador, this.identificador);
    }

    @Override
    public String generarCodigo() {
        StringBuilder codigo = new StringBuilder();

        expression.setType(DataType.FLOAT);
        codigo.append(expression.generarCodigo());

        String exprVal = expression.getIr_ref();
        String arrayPtr = CodeGeneratorHelper.getPointerForId(identificador);
        String sizePtr = CodeGeneratorHelper.getSecondPointerForId(identificador);

        String anyResult = CodeGeneratorHelper.getNewPointer();
        setIr_ref(anyResult);

        codigo.append(String.format("%s = alloca i1\n", anyResult));
        codigo.append(String.format("store i1 false, i1* %s\n", anyResult));

        String i = CodeGeneratorHelper.getNewPointer();
        codigo.append(String.format("%s = alloca i32\n", i));
        codigo.append("store i32 0, i32* " + i + "\n");

        String tagLoop = CodeGeneratorHelper.getNewTag();
        String tagBody = CodeGeneratorHelper.getNewTag();
        String tagEnd = CodeGeneratorHelper.getNewTag();
        String tagSuccess = CodeGeneratorHelper.getNewTag();

        String iLoad = CodeGeneratorHelper.getNewPointer();
        String sizeLoad = CodeGeneratorHelper.getNewPointer();
        String loopCond = CodeGeneratorHelper.getNewPointer();
        String elemVal = CodeGeneratorHelper.getNewPointer();

        codigo.append(String.format("br label %%%s\n", tagLoop));
        codigo.append(String.format("%s:\n", tagLoop));
        codigo.append(String.format("%s = load i32, i32* %s\n", iLoad, i));
        codigo.append(String.format("%s = load i32, i32* %s\n", sizeLoad, sizePtr));
        codigo.append(String.format("%s = icmp slt i32 %s, %s\n", loopCond, iLoad, sizeLoad));
        codigo.append(String.format("br i1 %s, label %%%s, label %%%s\n", loopCond, tagBody, tagEnd));

        codigo.append(String.format("%s:\n", tagBody));
        String offset = CodeGeneratorHelper.getNewPointer();
        codigo.append(String.format("%s = getelementptr float, float* %s, i32 %s\n", offset, arrayPtr, iLoad));
        codigo.append(String.format("%s = load float, float* %s\n", elemVal, offset));

        String cmp = CodeGeneratorHelper.getNewPointer();
        String opLlvm = getLlvmOperator(operador);
        codigo.append(String.format("%s = fcmp %s float %s, %s\n", cmp, opLlvm, elemVal, exprVal));

        codigo.append(String.format("br i1 %s, label %%%s, label %%%s\n", cmp, tagSuccess, tagLoop));

        codigo.append(String.format("%s:\n", tagSuccess));
        codigo.append(String.format("store i1 true, i1* %s\n", anyResult));
        codigo.append(String.format("br label %%%s\n", tagEnd));

        codigo.append(String.format("%s:\n", tagEnd));

        return codigo.toString();
    }

    private String getLlvmOperator(String operador) {
        return switch (operador) {
            case ">" -> "ogt";
            case ">=" -> "oge";
            case "<" -> "olt";
            case "<=" -> "ole";
            case "==" -> "oeq";
            case "!=" -> "one";
            default -> throw new RuntimeException("Operador no soportado: " + operador);
        };
    }
}
