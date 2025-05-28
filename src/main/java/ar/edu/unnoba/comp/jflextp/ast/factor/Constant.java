package ar.edu.unnoba.comp.jflextp.ast.factor;

import ar.edu.unnoba.comp.jflextp.llvm.CodeGeneratorHelper;

public class Constant extends Factor{
    private String value;
    private DataType type;

    public Constant(String value){
        this.value = value;
    }

    public Constant(String value, DataType type){
        this.value = value;
        this.type = type;
    }

    public String getEtiqueta(){
        return value;
    }

    public DataType getType() {
        return type;
    }

    @Override
    public String toString(){
        return String.format("%s", this.value);
    }

    @Override
    public String generarCodigo(){
        StringBuilder resultado = new StringBuilder();
        this.setIr_ref(CodeGeneratorHelper.getNewPointer());
        String pointerRef = CodeGeneratorHelper.getNewPointer();
        switch (type) {
            case INTEGER:
                return String.format("%1$s = add %3$s %4$s, %2$s\n", this.getIr_ref(), value, this.type.getLlvmSymbol(), this.type.getInitialValue());
            case FLOAT:
                String numberValue = CodeGeneratorHelper.toLLVMFloatFormat(this.value);
                resultado.append(storeFloat(pointerRef, numberValue, this.getIr_ref()));
                return resultado.toString();
            case DUPLE:
                this.setIr_ref_2(CodeGeneratorHelper.getNewPointer());
                String pointerRef2 = CodeGeneratorHelper.getNewPointer();
                String firstValue = CodeGeneratorHelper.getLLVMValueDuple(this.value, 0);
                String secondValue = CodeGeneratorHelper.getLLVMValueDuple(this.value, 1);
                resultado.append(storeFloat(pointerRef, firstValue, this.getIr_ref()));
                resultado.append(storeFloat(pointerRef2, secondValue, this.getIr_ref_2()));
                return resultado.toString();

            default:
                return "No implemented yet";
        }
    }

    private String storeFloat(String pointerRef, String numberValue, String valueRef) {
        final String floatType = DataType.FLOAT.getLlvmSymbol();
        return String.format("%1$s = alloca %2$s\n", pointerRef, floatType) +
                String.format("store %3$s %2$s, %3$s* %1$s\n", pointerRef, numberValue, floatType) +
                String.format("%1$s = load %2$s, %2$s* %3$s\n", valueRef, floatType, pointerRef);
    }
//    public String generarCodigo() {
//        this.setIr_ref(CodeGeneratorHelper.getNewPointer());
//        boolean isInteger = this.type.equals(DataType.INTEGER);
//        String operation = isInteger ? "add" : "fadd";
//        String numberValue = isInteger ? this.value : CodeGeneratorHelper.toLLVMFloatFormat(this.value);
//        return String.format("%1$s = %5$s %3$s %4$s, %2$s\n", this.getIr_ref(), numberValue, this.type.getLlvmSymbol(), this.type.getInitialValue(), operation);
//    }
}
