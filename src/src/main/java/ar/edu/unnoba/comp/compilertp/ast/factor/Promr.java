package ar.edu.unnoba.comp.compilertp.ast.factor;

import ar.edu.unnoba.comp.compilertp.ast.condition.CompareCondition;
import ar.edu.unnoba.comp.compilertp.ast.expression.ConstantExpression;
import ar.edu.unnoba.comp.compilertp.ast.expression.LogicExpression;
import ar.edu.unnoba.comp.compilertp.ast.operation.DivideOperation;
import ar.edu.unnoba.comp.compilertp.ast.operation.PlusOperation;
import ar.edu.unnoba.comp.compilertp.ast.statement.AssignStatement;
import ar.edu.unnoba.comp.compilertp.ast.statement.StatementList;
import ar.edu.unnoba.comp.compilertp.ast.statement.UnlessStatement;

public class Promr extends Factor{
    private String operator;
    private String pivot;

    private String numberList;

    public Promr(String pivot, String operator, String numberList){
        this.pivot = pivot;
        this.operator = operator;
        this.numberList = numberList;
    }

    public String getEtiqueta(){
        return "PROMR";
    }

    public String getNumberList() {
        return numberList;
    }

    public String graficar(String idPadre) {
        final String miId = this.getId();
        final String auxSumId = "_auxSum";
        final String auxCountId = "_auxCount";
        final String auxResultId = "_auxResult";
        AssignStatement result = new AssignStatement(auxResultId, new Constant("0"));
        AssignStatement auxSum = new AssignStatement(auxSumId, new Constant("0"));
        AssignStatement auxCount = new AssignStatement(auxCountId, new Constant("0"));
        StatementList actionBlock = new StatementList(result);
        actionBlock.addStatement(auxSum);
        actionBlock.addStatement(auxCount);
        for (String number : this.getNumberList().split(",")) {
            UnlessStatement unlessStatement = getUnlessStatementForNumber(number, auxSumId, auxCountId);
            actionBlock.addStatement(unlessStatement);
        }
        UnlessStatement resultStatement = getUnlessStatement(auxCountId, auxSumId, auxResultId);
        actionBlock.addStatement(resultStatement);
        return super.graficar(idPadre) +
                actionBlock.graficar(miId);
    }

    //This method calculate the condition for each number in the list and return the unless statement
    private UnlessStatement getUnlessStatementForNumber(String number, String auxSumId, String auxCountId) {
        CompareCondition condition = new CompareCondition(new Constant(pivot), operator, new Constant(number));
        PlusOperation plusOperation = new PlusOperation(new Constant(auxSumId), new Constant(number));
        AssignStatement assignStatement = new AssignStatement(auxSumId, plusOperation);
        AssignStatement assignStatementCount = new AssignStatement(auxCountId, new PlusOperation(new Constant(auxCountId), new Constant("1")));
        StatementList actionBlockAux = new StatementList(assignStatement);
        actionBlockAux.addStatement(assignStatementCount);
        UnlessStatement unlessStatement = new UnlessStatement(condition, actionBlockAux);
        return unlessStatement;
    }

    private static UnlessStatement getUnlessStatement(String auxCountId, String auxSumId, String auxResultId) {
        CompareCondition sumNotZero = new CompareCondition(new Constant(auxCountId), ">", new Constant("0"));
        CompareCondition countNotZero = new CompareCondition(new Constant(auxSumId), ">", new Constant("0"));
        LogicExpression logicExpression = new LogicExpression(sumNotZero, countNotZero, "AND");
        return new UnlessStatement(logicExpression,
                new StatementList(
                        new AssignStatement(
                                auxResultId, new DivideOperation(new Constant(auxSumId), new Constant(auxCountId)))));
    }
}
