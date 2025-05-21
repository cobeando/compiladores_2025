package ar.edu.unnoba.comp.jflextp.ast.statement;

import ar.edu.unnoba.comp.jflextp.ast.Nodo;

public class Id extends Nodo{
    private String id;

    public Id(String id){
        this.id = id;
    }


    public String getEtiqueta(){
        return id;
    }

    @Override
    public String toString(){
        return id;
    }
}
