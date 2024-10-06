// package Articulacao;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Grafo {
           List<List<Integer>> grafo;

       public Grafo(){

       }

        public Grafo(List<List<Integer>> g){
            grafo = g;
        }

        public List<Integer> getSucessores(int vertice){
            return grafo.get(vertice);           
        }
        public int size(){
            return grafo.size();
        }
        
        public int sucessoresSize(int vertice){
            return grafo.get(vertice).size();
        }

        public List<int[]> removerV(int v) {
            List<int[]> arestasRemovidas = new ArrayList<>();

            // Obter uma cópia da lista de adjacências do vértice v
            List<Integer> adjacentes = new ArrayList<>(grafo.get(v));

            // Remove as arestas de w para v
            for (int w : adjacentes) {
                // Remove v da lista de adjacências de w
                grafo.get(w).remove(Integer.valueOf(v)); // Usar Integer.valueOf para garantir que remove o objeto, não o índice
                arestasRemovidas.add(new int[]{v, w});
            }

            // Limpa a lista de adjacências de v
            grafo.get(v).clear();


            return arestasRemovidas;
        }

        public static void removerV(Grafo g, List<List<int[]>> art) {
            // Iterar sobre cada lista de arestas
            for (List<int[]> arestas : art) {
                // Iterar sobre cada aresta na lista
                for (int[] aresta : arestas) {
                    int vertice = aresta[0];  // Supondo que deseja remover o primeiro vértice de cada aresta
                    g.removerV(vertice);      // Remover o vértice do grafo
                }
            }
        }


        public void restaurarV(List<int[]> arestR){

            //Adiciona as arestas tanto de v para w, como de w para v
            for(int[] a: arestR){
                grafo.get(a[0]).add(a[1]);
                grafo.get(a[1]).add(a[0]);
            }
        }

        public static Grafo criarGrafo(String arq) throws IOException{

        FileReader fr = new FileReader(arq); 
        BufferedReader br = new BufferedReader(fr);

        int n = Integer.parseInt(br.readLine());

        String linha = br.readLine();
        linha.trim();


        List<List<Integer>> listaAdjacencia = new ArrayList<>(n + 1);

        // Inicializando as listas internas
        for (int i = 0; i <= n; i++) {
            listaAdjacencia.add(new ArrayList<>());
        }

        // Adicionando arestas
        while((linha = br.readLine()) != null){
            linha = linha.trim();
                if (!linha.isEmpty()) {
                    String[] teste = linha.split("\\s+");
                    int entrada = Integer.parseInt(teste[0]);
                    int saida = Integer.parseInt(teste[1]); 
                    listaAdjacencia.get(entrada).add(saida);
                    listaAdjacencia.get(saida).add(entrada); 
                }            
        }

        Grafo grafo = new Grafo(listaAdjacencia);

        br.close();

        return grafo;
    }
    }

    
