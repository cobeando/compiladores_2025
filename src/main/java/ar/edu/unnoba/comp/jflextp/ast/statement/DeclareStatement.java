package ar.edu.unnoba.comp.jflextp.ast.statement;

import java.util.ArrayList;

import ar.edu.unnoba.comp.jflextp.ast.factor.DataType;
import ar.edu.unnoba.comp.jflextp.ast.llvm.CodeGeneratorHelper;

public class DeclareStatement extends Statement{
    private ArrayList<Id> idAssignmentDeclarations;
    private DataType type;

    public DeclareStatement(DataType type, String list){
        this.idAssignmentDeclarations = new ArrayList<Id>();
        this.type = type;
        for (String name : list.split(",")) {
            addId(new Id(name.trim()));
        }
    }

    public void addId(Id idAssignment){
        idAssignmentDeclarations.add(idAssignment);
    }

    public String getEtiqueta(){
        return type.getNombre();
    }

    public String graficar(String idPadre) {
        final String miId = this.getId();
        String graficarDeclarations = "";
        for (Id idAssignment : idAssignmentDeclarations) {
            graficarDeclarations += idAssignment.graficar(miId);
        }
        return super.graficar(idPadre) + graficarDeclarations;
    }

    @Override
    public String toString() {
        return String.format("%s: %s", type, idAssignmentDeclarations);
    }

    public String generarCodigo(){
        StringBuilder resultado = new StringBuilder();
        String llvmType = this.type.getLlvmSymbol();
        String initialValue = this.type.getInitialValue();
        for (Id idAssignment : idAssignmentDeclarations) {
            String pointer = CodeGeneratorHelper.getNewGlobalPointerForId(idAssignment.getEtiqueta());
            resultado.append(String.format("%1$s = global %2$s %3$s\n", pointer, llvmType, initialValue));
        }
        return resultado.toString();
    }
}
