package ar.edu.unnoba.comp.compilertp.ast.statement;

import java.util.ArrayList;

public class StatementList extends Statement{
    private ArrayList<Statement> statements;
    public StatementList(Statement statement){
        statements = new ArrayList<Statement>();
        statements.add(statement);
    }

    public StatementList(Statement statement, StatementList statementList){
        statements = new ArrayList<Statement>();
        statements.add(statement);
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
}
