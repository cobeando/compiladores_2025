package ar.edu.unnoba.comp.jflextp.ast.expression;

import ar.edu.unnoba.comp.jflextp.ast.Nodo;
import ar.edu.unnoba.comp.jflextp.ast.factor.DataType;

public abstract class Expression extends Nodo{
    private String ir_ref;

    private DataType type;

    public DataType getType() {
        return type;
    }

    public void setType(DataType type) {
        this.type = type;
    }
}
