package ar.edu.unnoba.comp.jflextp.ast.statement;

import ar.edu.unnoba.comp.jflextp.ast.expression.Expression;
import ar.edu.unnoba.comp.jflextp.ast.factor.Constant;
import ar.edu.unnoba.comp.jflextp.llvm.CodeGeneratorHelper;

public class BackwardLoopStatement extends LoopStatement{
    public BackwardLoopStatement(Expression condition, StatementList codeBlock) {
        super(condition, codeBlock);
    }

    @Override
    public String getEtiqueta() {
        return "BACKWARD_LOOP_WHEN";
    }

    @Override
    public String graficar(String idPadre) {
        final String miId = this.getId();
        StringBuilder graphic = new StringBuilder(graficarEtiqueta(idPadre));

        Constant whenConst = new Constant("WHEN");
        graphic.append(getCondition().graficar(miId));
        graphic.append(whenConst.graficar(miId));

        Constant thenConst = new Constant("THEN");
        graphic.append(thenConst.graficar(miId));
        // NOTA: el orden de ejecución sería inverso, pero para graficar probablemente se muestre igual
        graphic.append(getStatementsList().graficar(thenConst.getId()));

        Constant endConst = new Constant("END_LOOP");
        graphic.append(endConst.graficar(miId));

        return graphic.toString();
    }

    @Override
    public String toString(){
        return String.format("BACKWARD_LOOP WHEN\n%s\nTHEN\n%s\nEND_LOOP", getCondition(), getStatementsList());
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
