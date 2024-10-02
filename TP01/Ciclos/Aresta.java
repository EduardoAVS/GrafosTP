package Ciclos;

public class Aresta {
   
    private int origem;
    private int destino;

    public Aresta(int a, int b) {
        this.origem = a;
        this.destino = b;
    }

    public int getOrigem() {
        return origem;
    }

    public int getDestino() {
        return destino;
    }

    @Override
    public String toString() {
        return "[" + origem + ", " + destino + "]";
    }
    
}
