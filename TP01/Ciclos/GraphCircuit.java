package Ciclos;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;
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
                
                Stack<Integer> caminho = new Stack<>();
                dfs(v, v, caminho, new boolean[g.size()]);
                
            }

          
            return eliminarSubconjuntos(ciclos);
        }

        // Função recursiva DFS para explorar os ciclos
        private void dfs(int v, int inicio, Stack<Integer> caminho, boolean bloqueado[]) {
            caminho.add(v);
            bloqueado[v] = true;
            
            for (int w : g.getAdjacentes(v)) {
                Aresta duplicada = new Aresta(w, v); // Inverso da aresta A 
                // Verifica se a aresta já foi processada
                if (!arestasVisitadas.contains(duplicada)) { // Evita repetir a aresta
                    arestasVisitadas.add(duplicada);
                    if (w == inicio && caminho.size() > 2) { // Evita ciclos triviais ( < 2 vértices)
                        ciclos.add(new ArrayList<>(caminho));
                    }

                    else if (!bloqueado[w]) {
                        dfs(w, inicio, caminho, bloqueado);
                    }
                }
                    
                
            }
            //bloqueado[v] = false; Essa linha teoricamente tem mas se colocar ta piorando
            caminho.pop();

        }

        private List<List<Integer>> eliminarSubconjuntos(List<List<Integer>> ciclos){

            // Ordenar os ciclos em ordem decrescente de tamanho para garantir que ciclos maiores sejam comparados primeiro
            ciclos.sort((c1, c2) -> Integer.compare(c2.size(), c1.size()));

            List<List<Integer>> ciclosMaximais = new ArrayList<>();

            // Percorrer cada ciclo e verificar se ele é um subconjunto de algum ciclo maior já processado
            for (List<Integer> cicloAtual : ciclos) {
                boolean isSubconjunto = false;

                // Verificar se o ciclo atual é subconjunto de algum ciclo já presente na lista de maximais
                for (List<Integer> cicloMaximal : ciclosMaximais) {
                    Set<Integer> conjuntoMaximal = new HashSet<>(cicloMaximal);
                    Set<Integer> conjuntoAtual = new HashSet<>(cicloAtual);

                    if (conjuntoMaximal.containsAll(conjuntoAtual)) {
                        isSubconjunto = true;
                        break; // Se já é subconjunto, não precisa continuar verificando
                    }
                }

                // Se o ciclo atual não for subconjunto de nenhum ciclo maior, adiciona ele aos ciclos maximais
                if (!isSubconjunto) {
                    ciclosMaximais.add(cicloAtual);
                }
            }

            return ciclosMaximais;

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

