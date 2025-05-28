package ar.edu.unnoba.comp.jflextp.ast;

import ar.edu.unnoba.comp.jflextp.ast.statement.DeclareStatement;
import ar.edu.unnoba.comp.jflextp.ast.statement.Statement;
import ar.edu.unnoba.comp.jflextp.ast.statement.StatementList;
import ar.edu.unnoba.comp.jflextp.utils.SymbolTable;
import ar.edu.unnoba.comp.jflextp.ast.llvm.CodeGeneratorHelper;

public class Program extends Nodo {
    private StatementList statement;
    private StatementList statement2;

    public Program(StatementList statement) {
        this.statement = statement;
    }

    public Program(StatementList statement, StatementList statement2) {
        this.statement = statement;
        this.statement2 = statement2;
    }

    public Program() {
    }

    public Statement getStatement() {
        return statement;
    }

    public String getEtiqueta() {
        return "Program";
    }

    public String graficar() {
        StringBuilder resultado = new StringBuilder();
        resultado.append("graph G {");
        resultado.append(this.graficar(null));
        resultado.append(this.statement.graficar(this.getId()));
        if (statement2 != null) {
            resultado.append(this.statement2.graficar(this.getId()));
        }
        resultado.append("}");
        return resultado.toString();
    }

    public String generarCodigo() {
        throw new RuntimeException("Not expected call");
    }

    public String generarCodigo(SymbolTable symbolTable) {
        CodeGeneratorHelper.setSymbolTable(symbolTable);
        StringBuilder resultado = new StringBuilder();
        resultado.append(";Programa: Prueba\n");
        resultado.append("source_filename = \"Prueba.txt\"\n");
        resultado.append("declare i32 @printf(i8*, ...)\n");
        resultado.append("declare float @llvm.fabs.f32(float)\n");
        resultado.append("\n");
        resultado.append("@.integer = private constant [4 x i8] c\"%d\\0A\\00\"\n");
        resultado.append("@.float = private constant [4 x i8] c\"%f\\0A\\00\"\n");
        resultado.append("@.duple1 = private constant [6 x i8] c\"(%f, \\00\"\n");
        resultado.append("@.duple2 = private constant [5 x i8] c\"%f)\\0A\\00\"\n");
        resultado.append("declare i32 @scanf(i8* %0, ...)\n");
        resultado.append("@int_read_format = unnamed_addr constant [3 x i8] c\"%d\\00\"\n");
        resultado.append("@double_read_format = unnamed_addr constant [4 x i8] c\"%d\\0A\\00\"\n");


        resultado.append("%struct.Tuple = type { float, float }\n");

        resultado.append("\n");
        resultado.append(CodeGeneratorHelper.generateStringConstants());
        

        if (statement.getStatements().get(0).getClass() == DeclareStatement.class) {
            resultado.append(statement.generarCodigo());
            resultado.append("define i32 @main(i32, i8**) {\n\t");

            StringBuilder resultado_programa = new StringBuilder();
            if (statement2 != null) {
                resultado_programa.append(symbolTable.generateDuplePointers());
                resultado_programa.append(this.statement2.generarCodigo());
            }

            resultado.append(resultado_programa.toString().replaceAll("\n", "\n\t"));
        } else {
            resultado.append("define i32 @main(i32, i8**) {\n\t");

            resultado.append(statement.generarCodigo());

            StringBuilder resultado_programa = new StringBuilder();

            resultado_programa.append(this.getStatement().generarCodigo());

            if (statement2 != null) {
                resultado_programa.append(this.statement2.generarCodigo());
            }

            resultado.append(resultado_programa.toString().replaceAll("\n", "\n\t"));

        }


        resultado.append("\tret i32 0\n");
        resultado.append("}\n\n");


        return resultado.toString();


    }
}
