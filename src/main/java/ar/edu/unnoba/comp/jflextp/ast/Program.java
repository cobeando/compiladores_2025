package ar.edu.unnoba.comp.jflextp.ast;

import ar.edu.unnoba.comp.jflextp.ast.statement.Statement;

public class Program extends Nodo{

    private Statement statement;
    private Statement statement2;

    public Program(Statement statement){
        this.statement = statement;
    }

    public Program(Statement statement, Statement statement2){
        this.statement = statement;
        this.statement2 = statement2;
    }

    public Program(){
        
    }

    public Statement getStatement(){
        return statement;
    }

    public String getEtiqueta(){
        return "Program";
    }

    public String graficar(){
        StringBuilder resultado = new StringBuilder();
        resultado.append("graph G {");
        resultado.append(this.graficar(null));
        resultado.append(this.statement.graficar(this.getId()));
        if (statement2 != null){
            resultado.append(this.statement2.graficar(this.getId()));
        }
        resultado.append("}");
        return resultado.toString();
    }

}
