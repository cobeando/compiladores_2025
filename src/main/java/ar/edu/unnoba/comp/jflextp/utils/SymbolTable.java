package ar.edu.unnoba.comp.jflextp.utils;

import ar.edu.unnoba.comp.jflextp.ast.expression.StringConstant;
import ar.edu.unnoba.comp.jflextp.exceptions.*;
import ar.edu.unnoba.comp.jflextp.llvm.CodeGeneratorHelper;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import ar.edu.unnoba.comp.jflextp.ast.factor.DataType;
import ar.edu.unnoba.comp.jflextp.ast.factor.ValorMasCercano;
import ar.edu.unnoba.comp.jflextp.ast.factor.ValorMasCercano.*;

public class SymbolTable {
    private Map<String, Symbol> symbolTable;

    public SymbolTable() {
        this.symbolTable = new HashMap<>();
    }

    /**
     * Método para agregar un símbolo a la tabla
      * @param name
     * @param type
     * @param token
     */
    public void addSymbol(String name, DataType type, String token) throws SymbolException {
        if (symbolTable.containsKey(name) && !Objects.equals(type, DataType.STRING)) {
            throw new SymbolException("El símbolo '" + name + "' ya está registrado en la tabla de símbolos.");
        }
        if (symbolTable.containsKey(name)) {
            return;
        }
        Symbol symbol = new Symbol(name, type, token);
        symbolTable.put(name, symbol);
    }

    public void isRegistered(String name) throws SymbolException {
        if (!symbolTable.containsKey(name)) {
            throw new SymbolException("El símbolo '" + name + "' no está registrado en la tabla de símbolos.");
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Tabla de simbolos: \n");
        for (Symbol symbol : symbolTable.values()) {
            sb.append("Name: ").append(symbol.getName()).append(", ");
            sb.append("Type: ").append(symbol.getType()).append(", ");
            sb.append("Token: ").append(symbol.getToken()).append("\n");
        }
        return sb.toString();
    }

    public String getPointer(String id){
        return symbolTable.get(id).getLlvmPointer();
    }

    public String getSecondPointer(String id){
        return symbolTable.get(id).getLlvmPointerTwo();
    }

    public void addPointer(String id, String pointer) {
        symbolTable.get(id).setLlvmPointer(pointer);
    }

    public String generateStringConstants() {
        addVMCConstants();
        StringBuilder sb = new StringBuilder();
        for (Symbol symbol : symbolTable.values()) {
            if (symbol.getType().equals(DataType.STRING)) {
                StringConstant stringValue= new StringConstant(symbol.getName());
                sb.append(String.format("%s = private constant [%d x i8] c\"%s\"\n",
                        CodeGeneratorHelper.getNewGlobalPointerForId(symbol.getName()),
                        stringValue.getLength(),
                        stringValue.getValue()));
            }
        }
        symbolTable.put("VMCSum", new Symbol("VMCSum", DataType.FLOAT, "VMC_helper"));
        symbolTable.put("VMCCount", new Symbol("VMCCount", DataType.INTEGER, "VMC_helper"));
        sb.append(String.format("%1$s = global %2$s %3$s\n", CodeGeneratorHelper.getNewGlobalPointerForId("VMCSum"), DataType.FLOAT.getLlvmSymbol(), DataType.FLOAT.getInitialValue()));
        sb.append(String.format("%1$s = global %2$s %3$s\n", CodeGeneratorHelper.getNewGlobalPointerForId("VMCCount"), DataType.INTEGER.getLlvmSymbol(), DataType.INTEGER.getInitialValue()));

        return sb.toString();
    }

    private void addVMCConstants() {
        //symbolTable.put(VMC_NO_ELEMENTS, new Symbol(VMC_NO_ELEMENTS, DataType.STRING, "VMC_helper"));
        symbolTable.put(ValorMasCercano.VMC_EMPTY_LIST, new Symbol(ValorMasCercano.VMC_EMPTY_LIST, DataType.STRING, "VMC_helper"));
        symbolTable.put(ValorMasCercano.VMC_RESULT, new Symbol(ValorMasCercano.VMC_RESULT, DataType.STRING, "VMC_helper"));
    }


    public DataType getType(String id) {
        return symbolTable.get(id).getType();
    }

    public void setVariableAsInitialized(String etiqueta) {
        symbolTable.get(etiqueta).setInitialized();
    }

    public boolean isVariableInitialized(String id) {
        return symbolTable.get(id).isInitialized();
    }

    public String generateDuplePointers() {
        StringBuilder sb = new StringBuilder();
        for (Symbol symbol : symbolTable.values()) {
            if (symbol.getType().equals(DataType.DUPLE)) {
                String firstPointer = CodeGeneratorHelper.getNewPointer();
                String secondPointer = CodeGeneratorHelper.getNewPointer();
                sb.append(String.format("%1$s = getelementptr %3$s, %3$s* %2$s, i32 0, i32 0 \n",
                        firstPointer, symbol.getLlvmPointer(), DataType.DUPLE.getLlvmSymbol()));
                sb.append(String.format("%1$s = getelementptr %3$s, %3$s* %2$s, i32 0, i32 1 \n",
                        secondPointer, symbol.getLlvmPointer(), DataType.DUPLE.getLlvmSymbol()));
                symbol.setLlvmPointer(firstPointer);
                symbol.setLlvmPointerTwo(secondPointer);
            }
        }
        return sb.toString();
    }

    private class Symbol {
        private String name;
        private DataType type;
        private String token;

        private String llvmPointer;

        private String llvmPointerTwo;

        private boolean initialized = false;

        public Symbol(String name, DataType type, String token) {
            this.name = name;
            this.type = type;
            this.token = token;
        }

        public String getName() {
            return name;
        }

        public DataType getType() {
            return type;
        }

        public String getToken() {
            return token;
        }

        public void setLlvmPointer(String pointer){
            this.llvmPointer = pointer;
        }

        public void setLlvmPointerTwo(String pointer){
            this.llvmPointerTwo = pointer;
        }

        public String getLlvmPointerTwo(){
            return this.llvmPointerTwo;
        }

        public String getLlvmPointer(){
            return this.llvmPointer;
        }

        public void setInitialized() {
            this.initialized = true;
        }

        public boolean isInitialized() {
            return initialized;
        }
    }
}
