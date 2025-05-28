package ar.edu.unnoba.comp.jflextp.ast.statement;

import ar.edu.unnoba.comp.jflextp.ast.expression.Expression;
import ar.edu.unnoba.comp.jflextp.ast.llvm.CodeGeneratorHelper;

public class LoopStatement extends Statement{
    private StatementList codeBlock;
    private Expression condition;
    private String tagRepeat;
    private String tagEnd;

    public LoopStatement(Expression condition, StatementList codeBlock) {
        this.condition = condition;
        this.codeBlock = codeBlock;
    }

    public String getEtiqueta() {
        return "LOOP";
    }

     public Expression getCondition() {
        return condition;
    }

    public StatementList getStatementsList(){
        return codeBlock;
    }
    
    public String getTag_Repeat() {
        return tagRepeat;
    }

    public void setTag_Repeat(String tagRepeat) {
        this.tagRepeat = tagRepeat;
    }

    public String getTag_End() {
        return tagEnd;
    }

    public void setTag_End(String tagEnd) {
        this.tagEnd = tagEnd;
    }

    public String graficar(String idPadre){
        String miId = this.getId();
        return super.graficar(idPadre) + condition.graficar(miId) + codeBlock.graficar(miId);
    }

    @Override
    public String generarCodigo(){
        StringBuilder resultado = new StringBuilder();
        this.setTag_Repeat(CodeGeneratorHelper.getNewTag());
        this.setTag_End(CodeGeneratorHelper.getNewTag());
        this.getStatementsList().setTag_Break(this.getTag_End());
        this.getStatementsList().setTag_Continue(this.getTag_Repeat());
        resultado.append(this.getCondition().generarCodigo());
        resultado.append(String.format("br i1 %1$s, label %%%2$s, label %%%3$s \n\n", this.getCondition().getIr_ref(), this.getTag_Repeat(), this.getTag_End()));
        resultado.append(String.format("\n%s: \n\n", getTag_Repeat()));
        resultado.append(this.getStatementsList().generarCodigo().replace("\n", "\n\t"));
        resultado.append(this.getCondition().generarCodigo());
        resultado.append(String.format("br i1 %1$s, label %%%2$s, label %%%3$s \n\n", this.getCondition().getIr_ref(), this.getTag_Repeat(), this.getTag_End()));
        resultado.append(String.format("%s: \n\n", getTag_End()));
        return resultado.toString();
    }
}
