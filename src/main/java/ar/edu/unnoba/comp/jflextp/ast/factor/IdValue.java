package ar.edu.unnoba.comp.jflextp.ast.factor;

import ar.edu.unnoba.comp.jflextp.ast.llvm.CodeGeneratorHelper;

public class IdValue extends Factor{

    private String id;

    public IdValue(String id){
        this.id = id;
    }

    public String getId(){
        return id;
    }

    @Override
    public String getEtiqueta(){
        return id;
    }


    @Override
    public String generarCodigo(){
        if (!CodeGeneratorHelper.isVariableInitialized(id)) {
            throw new RuntimeException("Variable " + id + " is not initialized");
        }
        this.setIr_ref(CodeGeneratorHelper.getNewPointer());
        String globalPointer = CodeGeneratorHelper.getPointerForId(id);
        DataType type = CodeGeneratorHelper.getTypeForId(id);
        setType(type);
        if (type!= DataType.DUPLE) {
            return (String.format("%1$s = load %2$s, %2$s* %3$s\n", this.getIr_ref(), type.getLlvmSymbol(), globalPointer));
        }
        String secondGlobalPointer = CodeGeneratorHelper.getSecondPointerForId(id);
        this.setIr_ref_2(CodeGeneratorHelper.getNewPointer());
        return (String.format("%1$s = load %2$s, %2$s* %3$s\n", this.getIr_ref(), DataType.FLOAT.getLlvmSymbol(), globalPointer))
                + (String.format("%1$s = load %2$s, %2$s* %3$s\n", this.getIr_ref_2(), DataType.FLOAT.getLlvmSymbol(), secondGlobalPointer));
    }

}
