package ar.edu.unnoba.comp.jflextp.ast.factor;

public enum DataType {
    INTEGER("INTEGER", "i32"),
    FLOAT("FLOAT", "double"),
    DUPLE("DUPLE", "%struct.Tuple", "{ float 0.000000e+00, float 0.000000e+00 }"),
    BOOLEAN("BOOLEAN", "i1"),
    FLOAT_ARRAY("FLOAT_ARRAY", "float*"),
    STRING("STRING","No Symbol" );

    private final String nombre;
    private final String llvmsymbol;
    private String initialValue = "0";

    DataType(String nombre, String llvmsymbol) {
        this.nombre = nombre;
        this.llvmsymbol = llvmsymbol;
    }

    DataType(String nombre, String llvmsymbol, String initialValue) {
        this.nombre = nombre;
        this.llvmsymbol = llvmsymbol;
        this.initialValue = initialValue;
    }

    public boolean compatibleWith(DataType other) {
        switch (this) {
            case INTEGER:
                return other == INTEGER;
            case FLOAT:
                return other == FLOAT || other == INTEGER;
            case BOOLEAN:
                return other == BOOLEAN;
            case DUPLE:
                return other == DUPLE || other == FLOAT || other == INTEGER;
            case FLOAT_ARRAY:
                return other == FLOAT_ARRAY;
            default:
                return false;
        }
    }

    public String getNombre() {
        return nombre;
    }

    public String getLlvmSymbol() {
        return llvmsymbol;
    }

    public DataType updateType(DataType type) {
        if (this == type) {
            return this;
        } else if (this == FLOAT && type == INTEGER) {
            return FLOAT;
        } else if (this == INTEGER && type == FLOAT) {
            return FLOAT;
        } else if (this == DUPLE && type == INTEGER) {
            return DUPLE;
        } else if (this == INTEGER && type == DUPLE) {
            return DUPLE;
        } else if (this == FLOAT && type == DUPLE) {
            return DUPLE;
        } else if (this == DUPLE && type == FLOAT) {
            return DUPLE;
        } else {
            throw new RuntimeException("Incompatible types when updating types");
        }
    }

    public String getInitialValue() {
        return initialValue;
    }
}
