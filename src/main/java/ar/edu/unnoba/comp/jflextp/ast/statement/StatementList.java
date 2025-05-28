package ar.edu.unnoba.comp.jflextp.ast.statement;

import java.util.ArrayList;

public class StatementList extends Statement{
    private String tagBreak;
    private String tagContinue;
    private ArrayList<Statement> statements;

    public StatementList(Statement statement){
        statements = new ArrayList<Statement>();
        statements.add(statement);
    }

    public StatementList(Statement statement, StatementList statementList){
        statements = new ArrayList<Statement>();
        statements.add(statement);
    }

    public String getTag_Continue() {
        return tagContinue;
    }

    public void setTag_Continue(String tagContinue) {
        this.tagContinue = tagContinue;
    }

    public String getTag_Break() {
        return tagBreak;
    }

    public void setTag_Break(String tagBreak) {
        this.tagBreak = tagBreak;
    }

    public ArrayList<Statement> getStatements(){
        return statements;
    }

    public void addStatement(Statement statement){
        statements.add(statement);
    }

    public StatementList addStatementToList(Statement statement){
        statements.add(statement);
        return this;
    }

    public String graficar(String idPadre){
        StringBuilder graphic = new StringBuilder();
        for (Statement statement : statements) {
            graphic.append(statement.graficar(idPadre));
        }
        return graphic.toString();
    }

    @Override
    public String generarCodigo(){
        StringBuilder resultado = new StringBuilder();
        for (Statement statement : statements) {
            statement.setTag_Break(this.getTag_Break());
            statement.setTag_Continue(this.getTag_Continue());
            resultado.append(statement.generarCodigo());
        }
        return resultado.toString();
    }
}
