package ar.edu.unnoba.comp.jflextp.ast.statement;

import java.util.ArrayList;
import java.util.stream.Collectors;

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

    
    @Override
    public String toString(){
        return statements.stream()
                     .map(Object::toString)
                     .collect(Collectors.joining("\n\t"));
    }
}
