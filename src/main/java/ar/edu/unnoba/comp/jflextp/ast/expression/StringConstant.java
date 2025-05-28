package ar.edu.unnoba.comp.jflextp.ast.expression;

import java.nio.charset.StandardCharsets;

public class StringConstant extends Expression {
        private final String value;



        public StringConstant(String valor) {
            this.value = valor;
        }

        public String getValue() {
            //TODO THROW EXCEPTION IF \ comes alone
            return String.format("%s\\0A\\00", value
                    .replace("\\n", "\\0A")
                    .replace("\\t", "\\09")
                    .replace("\\\\", "\\"));
        }

        @Override
        public String getEtiqueta() {
            return this.value;
        }

        @Override
        public String generarCodigo() {
            return "";
        }

        public int countReplaces() {
            return (value.length() - value.replace("\\n", "").length() +
                    value.length() - value.replace("\\t", "").length() +
                    value.length() - value.replace("\\\\", "").length()) / 2;
        }


        public int getLength() {
            return value.getBytes(StandardCharsets.UTF_8).length + 2 - countReplaces();
        }
    }