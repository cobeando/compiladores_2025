package ar.edu.unnoba.comp.jflextp.utils;

import ar.edu.unnoba.comp.jflextp.exceptions.*;

import java.util.HashMap;
import java.util.Map;

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
    public void addSymbol(String name, String type, String token) throws SymbolException {
        if (symbolTable.containsKey(name)) {
            throw new SymbolException("El símbolo '" + name + "' ya está registrado en la tabla de símbolos.");
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

    private class Symbol {
        private String name;
        private String type;
        private String token;

        public Symbol(String name, String type, String token) {
            this.name = name;
            this.type = type;
            this.token = token;
        }

        public String getName() {
            return name;
        }

        public String getType() {
            return type;
        }

        public String getToken() {
            return token;
        }
    }
}
