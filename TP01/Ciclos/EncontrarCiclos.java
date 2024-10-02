package Ciclos;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;
import java.util.List;
import java.io.IOException; 


public class EncontrarCiclos {
    
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
    

    public static class DFS{
        private boolean marcado[];
        private boolean marcado2[];
        private List<HashSet<Integer>> ciclos;
        
        List<Aresta> arvore;
        List<Aresta> retorno;

        Grafo g;
        Stack<Aresta> pilha;
        List<Biconexo> listaBiconexo;

        Set<Aresta> arestasVisitadas;
        Set<Aresta> arestasVisitadas2;
        Set<Integer> ciclo;
        List<Integer> ordemVertices;
        List<Integer> arestasRemovidas;
    
        public boolean visto(int v){
            return marcado[v];
        }

        public DFS(Grafo g){

            marcado = new boolean[g.size()];
            

            arvore = new ArrayList<>();
            retorno = new ArrayList<>();

            ciclos = new ArrayList<>();

            this.g = g;
            pilha = new Stack<>();

            arestasVisitadas = new HashSet<>();
            
            ordemVertices = new ArrayList<>();
            

            dfs();
        }


        private void dfs(){
            for(int w = 1; w < g.size(); w++){
                if(!marcado[w]){
                    encontrarCiclos(w, 0);
                    
                }
            }
        }
        
        

        private void encontrarCiclos(int v, int u) {
            marcado[v] = true;
        
            // Criar uma cópia da lista de adjacentes para evitar modificação concorrente
            List<Integer> adj = new ArrayList<>(g.getAdjacentes(v));
            for (int w : adj) {
                Aresta duplicada = new Aresta(w, v); // Inverso da aresta A 
                // Verifica se a aresta já foi processada
                if (!arestasVisitadas.contains(duplicada)) { // Evita repetir a aresta
                    arestasVisitadas.add(duplicada);
        
                    if (!marcado[w]) {
                        if (!xInOrdemVertice(v)) {
                            ordemVertices.add(v);
                        }
                        encontrarCiclos(w, v); // Chamada recursiva
                    } else if (w != u) {
                        if (!xInOrdemVertice(v)) {
                            ordemVertices.add(v);
                        }
                        if (!xInOrdemVertice(w)) {
                            ordemVertices.add(w);
                        }
        
                        ciclo = new HashSet<>();
                        // Agora percorre a lista de ordemVertices sem modificá-la durante a iteração
                        List<Integer> copiaOrdemVertices = new ArrayList<>(ordemVertices);
                        for (int i = 0; i < copiaOrdemVertices.size() - 1; i++) {
                            for (int j = i + 1; j < copiaOrdemVertices.size(); j++) {
                                int a = copiaOrdemVertices.get(i);
                                int b = copiaOrdemVertices.get(j);
                                
                                if (existeCiclo(a, b) && (a != b)) {
                                    ciclo.add(a);
                                    ciclo.add(b);
                                }
                            }
                        }
                        
                        
                        ciclos.add(new HashSet<>(ciclo));
                    }
                    
                }
            }
        }
        
        public boolean existeCiclo(int origem, int destino) {
            arestasVisitadas2 = new HashSet<>();
            marcado2 = new boolean[g.size()];
            arestasRemovidas = new ArrayList<>();
        
            boolean primeiroCaminho = contarCaminho(destino, origem, 0);
            boolean segundoCaminho = contarCaminho(destino, origem, 0);
        
            // Verifica o tamanho da lista antes de tentar adicionar as arestas removidas
            if (arestasRemovidas.size() >= 2) {
                g.adicionarAresta(arestasRemovidas.get(0), arestasRemovidas.get(1)); // Adiciona as arestas removidas novamente
            }
            if (arestasRemovidas.size() >= 4 && segundoCaminho) {
                g.adicionarAresta(arestasRemovidas.get(2), arestasRemovidas.get(3));
            }
        
            return (primeiroCaminho && segundoCaminho);
        }
        
        public boolean contarCaminho(int obj, int v, int u) {
            marcado2[v] = true;
        
            List<Integer> adj2 = g.getAdjacentes(v);
            for (int w : adj2) {
                Aresta duplicada = new Aresta(w, v); // Inverso da aresta A 
                // Verifica se a aresta já foi processada
                if (!arestasVisitadas2.contains(duplicada)) { // Evita repetir a aresta
                    arestasVisitadas2.add(duplicada);
        
                    if (!marcado2[w]) {
                        if (w == obj) {
                            g.removerAresta(v, w); // Remove a aresta para verificar se existe mais caminhos
                            arestasRemovidas.add(v);
                            arestasRemovidas.add(w);
                            return true;
                        }
        
                        // Chamada recursiva: se o caminho foi encontrado, retorna true
                        if (contarCaminho(obj, w, v)) {
                            return true; // Propaga o true para as chamadas anteriores
                        }
                    }
                }
            }
            return false;
        }
        

        public boolean xInOrdemVertice(int x){
            for(int i : ordemVertices){
                if(i == x){
                    return true;
                }
            }
            return false;
        }


        public void ciclos() {
            for(HashSet<Integer> c : ciclos){
                System.out.println();
                for(int i : c){
                    System.out.println(i + " ");
                }
                System.out.println();
            }
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

        DFS dfs = new DFS(grafo);

        dfs.ciclos();

        for(int i = 1; i < grafo.size(); i++){
            System.out.println(i + ":  " + grafo.getAdjacentes(i));
        }

        sc.close();
    } 
    
}

