package ar.edu.unnoba.comp.compilertp.ast.statement;

import java.util.ArrayList;

public class DeclareStatement extends Statement{
    private ArrayList<Id> idDeclarations;
    private String type;

    public DeclareStatement(String type, String list){
        this.idDeclarations = new ArrayList<Id>();
        this.type = type;
        for (String name : list.split(",")) {
            addId(new Id(name));
        }
    }

    public void addId(Id id){
        idDeclarations.add(id);
    }

    public String getEtiqueta(){
        return type;
    }

    public String graficar(String idPadre) {
        final String miId = this.getId();
        String graficarDeclarations = "";
        for (Id id : idDeclarations) {
            graficarDeclarations += id.graficar(miId);
        }
        return super.graficar(idPadre) + graficarDeclarations;
    }


}
