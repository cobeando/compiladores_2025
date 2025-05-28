package ar.edu.unnoba.comp.jflextp.ast;

public abstract class Nodo {
    private String nombre;

    private String ir_ref;

    private String ir_ref_2;

    public Nodo() {}

    public Nodo(String nombre) {
        this.nombre = nombre;
    }

    public String getIr_ref() {
        return ir_ref;
    }

    public String getIr_ref_2() {
        return ir_ref_2 == null ? ir_ref : ir_ref_2;
    }

    public void setIr_ref_2(String ir_ref_2) {
        this.ir_ref_2 = ir_ref_2;
    }

    public void setIr_ref(String ir_ref) {
        this.ir_ref = ir_ref;
    }

    public String getId() {
        return "nodo_" + this.hashCode();
    }

    public String getEtiqueta() {
        if (this.nombre != null) {
            return this.nombre;
        }
        final String name = this.getClass().getName();
        final int pos = name.lastIndexOf('.') + 1;
        return name.substring(pos);
    }

    public String graficar(String idPadre){
        StringBuilder grafico = new StringBuilder();
        grafico.append(String.format("%1$s[label=\"%2$s\"]\n", this.getId(), this.getEtiqueta()));
        if(idPadre != null)
            grafico.append(String.format("%1$s--%2$s\n", idPadre, this.getId()));
        return grafico.toString();
    }

    public abstract String generarCodigo();
}
