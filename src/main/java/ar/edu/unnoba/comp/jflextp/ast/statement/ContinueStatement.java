package ar.edu.unnoba.comp.jflextp.ast.statement;

public class ContinueStatement extends Statement{
    private String tagContinue;

    public ContinueStatement(){
    }

    public String getEtiqueta(){
        return "CONTINUE";
    }

    public String getTag_Continue() {
        return tagContinue;
    }

    public void setTag_Continue(String tagContinue) {
        this.tagContinue = tagContinue;
    }

    @Override
    public String generarCodigo(){
        if(getTag_Continue() == null){
            throw new RuntimeException("Error: Continue outside Repeat Block");
        }
        StringBuilder resultado = new StringBuilder();
        resultado.append(String.format("br label %%%s \n",getTag_Continue()));
        return resultado.toString();
    }
}