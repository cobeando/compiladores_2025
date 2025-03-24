package ar.edu.unnoba.comp.jflextp;

class MiToken{

    public final String nombre;
    public final int linea;
    public final int columna;
    public final Object valor;

    MiToken (String nombre) {
        this(nombre, null);
    }

    MiToken (String nombre, Object valor) {
        this(nombre, -1, -1, valor);
    }

    MiToken (String nombre, int linea, int columna) {
        this(nombre, linea, columna, null);
    }

    MiToken (String nombre, int linea, int columna, Object valor){
        this.nombre = nombre;
        this.valor = valor;
        this.linea = linea;
        this.columna = columna;
    }

    @Override
    public String toString() {
        String posicion = "";
        if (this.linea != -1 && this.columna != -1)
            posicion = String.format(" @ (L:%d, C:%d)", this.linea+1, this.columna+1);
        if (valor == null)
            return "[" + this.nombre + "]" + posicion;
        else
            return "[" + this.nombre + "] -> (" + this.valor + ")" + posicion;
    }
}