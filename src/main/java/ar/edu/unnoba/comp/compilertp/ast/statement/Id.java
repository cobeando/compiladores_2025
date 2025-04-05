package ar.edu.unnoba.comp.compilertp.ast.statement;

import ar.edu.unnoba.comp.compilertp.ast.Nodo;

public class Id extends Nodo{
    private String id;

    public Id(String id){
        this.id = id;
    }


    public String getEtiqueta(){
        return id;
    }
}
