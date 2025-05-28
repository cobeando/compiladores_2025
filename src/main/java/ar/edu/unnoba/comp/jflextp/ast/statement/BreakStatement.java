package ar.edu.unnoba.comp.jflextp.ast.statement;

public class BreakStatement extends Statement{
    private String tagBreak;

    public BreakStatement() {
    }

    public String getEtiqueta(){
        return "BREAK";
    }

    public String getTag_Break() {
        return tagBreak;
    }

    public void setTag_Break(String tagBreak) {
        this.tagBreak = tagBreak;
    }

    @Override
    public String toString(){
        return "BREAK";
    }

    @Override
    public String graficar(String idPadre) {
        return super.graficar(idPadre);
    }

    @Override
    public String generarCodigo(){
        if(getTag_Break() == null){
            throw new RuntimeException("Error: Break outside Repeat Block");
        }
        StringBuilder resultado = new StringBuilder();
        resultado.append(String.format("br label %%%s \n",getTag_Break()));
        return resultado.toString();
    }

}