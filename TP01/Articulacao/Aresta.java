// package Articulacao;
public class Aresta {
     private int origem;
        private int destino;

        public Aresta(int origem, int destino) {
            this.origem = origem;
            this.destino = destino;
        }
     

        public int getOrigem() {
            return origem;
        }

        public int geTDestino() {
            return destino;
        }

       

        
        public String toString() {
            return "[" + origem + " -> " + destino + "]";
        }

       

    }

