package ar.edu.unnoba.comp.jflextp.ast.factor;

import ar.edu.unnoba.comp.jflextp.llvm.CodeGeneratorHelper;

public class InputFloat extends Factor {

    private String ir_ref_float1;
    private String ir_ref_float2;
    private String ir_ref_duple;
    private  DataType type;
    public InputFloat() {
        this.type = DataType.FLOAT;
    }

    public DataType getType() {
        return type;
    }

    public String getEtiqueta() {
        return "INPUT FLOAT";
    }

    public String getIr_ref_float1() {
        return ir_ref_float1;
    }

    public void setIr_ref_float1(String ir_ref_float1) {
        this.ir_ref_float1 = ir_ref_float1;
    }

    public String getIr_ref_float2() {
        return ir_ref_float2;
    }

    public void setIr_ref_float2(String ir_ref_float2) {
        this.ir_ref_float2 = ir_ref_float2;
    }

    public String getIr_ref_duple() {
        return ir_ref_duple;
    }

    public void setIr_ref_duple(String ir_ref_duple) {
        this.ir_ref_duple = ir_ref_duple;
    }

    @Override
    public String toString(){
        return String.format("FLOAT");
    }

    @Override
    public String generarCodigo(){
        StringBuilder resultado = new StringBuilder();
        this.setIr_ref_float1(CodeGeneratorHelper.getNewPointer());
        this.setIr_ref_float2(CodeGeneratorHelper.getNewPointer());
        this.setIr_ref_duple(CodeGeneratorHelper.getNewPointer());
        this.setIr_ref(CodeGeneratorHelper.getNewPointer());
        resultado.append(String.format("%s = alloca double \n", this.getIr_ref_float1())); 
        resultado.append(String.format("%1$s = call i32 (i8*, ...) @scanf(i8* getelementptr inbounds ([4 x i8], [4 x i8]* @double_read_format, i64 0, i64 0), double* %2$s) \n", this.getIr_ref_float2(), this.getIr_ref_float1()));
        resultado.append(String.format("%1$s = load double, double* %2$s \n",this.getIr_ref_duple() , this.getIr_ref_float1()));
        resultado.append(String.format("%1$s = fptrunc double %2$s to float\n", this.getIr_ref(), this.getIr_ref_duple()));

        return resultado.toString() ;
    }

}
