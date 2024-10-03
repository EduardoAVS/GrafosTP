package Ciclos;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.List;
import java.io.IOException; 


public class GraphCircuit {
    
    public static class Biconexo {
        List<List<Integer>> componente;
        int tGrafo;
        public Biconexo(int tam){
            componente = new ArrayList<>();
            tGrafo = tam;
            for (int i = 0; i < tam; i++) {
                componente.add(new ArrayList<>());
            }
        }

        public void addAresta(Aresta a){
            int v = a.getOrigem();
            int w = a.getDestino();
            componente.get(v).add(w); // Adiciona os vértices na lista de adjacência um do outro
            componente.get(w).add(v);
        }

        
        public void imprimir(){
            for(int i = 1; i < tGrafo; i++){
                for(int j = 0; j < componente.get(i).size(); j++){
                        System.out.print(" {" + i + ", " + componente.get(i).get(j) + "} ");
                }
            }
        }
    }
    

    public static class EncontrarCiclos{
        Grafo g;
        int teste = 0;
        
        boolean marcado[];
        List<List<Integer>> ciclos;
       
        List<Biconexo> listaBiconexo;
        Set<Aresta> arestasVisitadas;
        Set<Integer> ciclo;
 
        public EncontrarCiclos(Grafo g){

            
            marcado = new boolean[g.size()];
            ciclos = new ArrayList<>();

            this.g = g;

            arestasVisitadas = new HashSet<>();

        }


        public List<List<Integer>> encontrarCiclos() {
            for (int v = 1; v < g.size(); v++) {
                
                List<Integer> caminho = new ArrayList<>();
                dfs(v, 0, v, caminho, new boolean[g.size()]);
                
            }

          
            return eliminarSubconjuntos(ciclos);
        }

        // Função recursiva DFS para explorar os ciclos
        private void dfs(int v, int pai, int inicio, List<Integer> caminho, boolean bloqueado[]) {
            caminho.add(v);
            bloqueado[v] = true;
            
            for (int w : g.getAdjacentes(v)) {

                Aresta oposta = new Aresta(w, v);
                
                // Verifica se a aresta já foi processada
                if (!arestasVisitadas.contains(oposta)) { // Evita repetir a aresta
                    arestasVisitadas.add(oposta);

                    if (!bloqueado[w]) {
                        dfs(w, v, inicio, caminho, bloqueado);
                    }

                    else if (w == inicio && caminho.size() > 2 && w != pai) { // Evita ciclos triviais ( < 2 vértices)
                        ciclos.add(new ArrayList<>(caminho));
                    }
                }
            }
            
            caminho.remove(caminho.size() - 1);
        }

        private List<List<Integer>> eliminarSubconjuntos(List<List<Integer>> ciclos){

            Set<List<Integer>> subconjuntosRemovidos = new HashSet<>(); // Evita remover durante a iteracão

            for (int i = 0; i < ciclos.size(); i++) {

                Set<Integer> conjuntoI = new HashSet<>(ciclos.get(i)); // Converte o ciclo i para um Set
    
                for (int j = i + 1; j < ciclos.size(); j++) {
                    
                    Set<Integer> conjuntoJ = new HashSet<>(ciclos.get(j)); // Converte o ciclo j para um Set

                    // containsAll verifica se j é subconjunto de i
                    if (conjuntoJ.containsAll(conjuntoI)) {
                        subconjuntosRemovidos.add(ciclos.get(i));
                    }

                        // Se i for subconjunto de j remove i
                    else if(conjuntoI.containsAll(conjuntoJ)) {
                        subconjuntosRemovidos.add(ciclos.get(j));
                    }
                    
                }

            }

            ciclos.removeAll(subconjuntosRemovidos);

            return ciclos;


        }
        
        
        // graph-test.txt
        //graph-test-100-1.txt
        //grafo100.txt

    }
   public static void main(String[] args) throws IOException{
        Scanner sc = new Scanner(System.in);

        System.out.println("Nome do arquivo: ");
        String arq = sc.next(); 

        Grafo grafo = new Grafo(arq);      

        EncontrarCiclos ec = new EncontrarCiclos(grafo);

        List<List<Integer>> ciclos = ec.encontrarCiclos();
        
        for (List<Integer> ciclo : ciclos) {
            System.out.println(ciclo);
        }
        sc.close();
    } 
    
}

