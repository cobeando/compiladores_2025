package ar.edu.unnoba.comp.jflextp.ast.condition;

public enum Operator {
    EQEQ("==", "eq","oeq"),
    DIFFERENT("!=", "ne", "one"),
    LESS("<", "slt", "olt"),
    GREATER(">", "sgt", "ogt"),
    LEQ("<=", "sle", "ole"),
    GEQ(">=", "sge", "oge"),

    AND("AND", "and", "and"),
    OR("OR", "or", "or");


    private final String symbol;
    private final String llvmCode;

    private final String llvmCodeFloat;


    Operator(String symbol, String llvmCode, String llvmCodeFloat) {
        this.symbol = symbol;
        this.llvmCode = llvmCode;
        this.llvmCodeFloat = llvmCodeFloat;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getLlvmCode(boolean isInteger) {
        return isInteger ? llvmCode : llvmCodeFloat;
    }
}