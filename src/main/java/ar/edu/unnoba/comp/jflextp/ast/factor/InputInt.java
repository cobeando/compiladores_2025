package ar.edu.unnoba.comp.jflextp.ast.factor;

import ar.edu.unnoba.comp.jflextp.ast.llvm.CodeGeneratorHelper;

public class InputInt extends Factor {

    private String ir_ref_int1;
    private String ir_ref_int2;
    private  DataType type;
    public InputInt() {
        this.type = DataType.INTEGER;
    }

    public DataType getType() {
        return type;
    }



    public String getEtiqueta() {
        return "INPUT INT";
    }

    public String getIr_ref_Int1() {
        return ir_ref_int1;
    }

    public void setIr_ref_Int1(String ir_ref_int1) {
        this.ir_ref_int1 = ir_ref_int1;
    }

    public String getIr_ref_Int2() {
        return ir_ref_int2;
    }

    public void setIr_ref_Int2(String ir_ref_int2) {
        this.ir_ref_int2 = ir_ref_int2;
    }

    @Override
    public String toString(){
        return String.format("INT");
    }

    @Override
    public String generarCodigo(){
        StringBuilder resultado = new StringBuilder();
        this.setIr_ref_Int1(CodeGeneratorHelper.getNewPointer());
        this.setIr_ref_Int2(CodeGeneratorHelper.getNewPointer());
        this.setIr_ref(CodeGeneratorHelper.getNewPointer());
        resultado.append(String.format("%s = alloca i32 \n", this.getIr_ref_Int1())); 
        resultado.append(String.format("%1$s = call i32 (i8*, ...) @scanf(i8* getelementptr inbounds ([3 x i8], [3 x i8]* @int_read_format, i64 0, i64 0), i32* %2$s) \n", this.getIr_ref_Int2(), this.getIr_ref_Int1()));
        resultado.append(String.format("%1$s = load i32, i32* %2$s \n",this.getIr_ref() , this.getIr_ref_Int1()));

        return resultado.toString() ;
    }
}

