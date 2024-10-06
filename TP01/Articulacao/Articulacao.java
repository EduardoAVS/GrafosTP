// package Articulacao;
// Metodo 2 - TP1

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Stack;
import java.util.Iterator;

import java.io.BufferedReader; 
import java.io.FileReader;
import java.util.List;
import java.io.IOException;

public class Articulacao{
   
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


       
    

    public static class DFS{
        private int t;
        private int TD[];
        private int TT[];
        private int pai[];
        
        List<Aresta> arvore;
        List<Aresta> arestasVerEscolhido;

        public DFS(){

        }

        public int geTT(){
            return t;
        }

        public int geTdAt(int v){
            return TD[v];
        } 

        public int geTtAt(int v){
            return TT[v];
        } 
 

        public DFS(Grafo g, int inicio) {
                int n = g.size();
                TD = new int[n];
                TT = new int[n];
                pai = new int[n];
                Arrays.fill(TD, -1); // Inicializa todos os vértices como não visitados
                Arrays.fill(TT, -1);
                t = 0;
                arvore = new ArrayList<>();
                busca(g, inicio);  // Inicia a DFS no vértice de início
            }

            private int[] busca(Grafo g, int inicio) {
                Stack<Integer> pilha = new Stack<>();
                pilha.push(inicio);
                TD[inicio] = ++t;

                while (!pilha.isEmpty()) {
                    int v = pilha.peek();
                    List<Integer> suc = g.getSucessores(v);
                    boolean encontrouSucessor = false;

                    for (int w : suc) {
                        if (TD[w] == -1) {  // Se o vértice ainda não foi visitado
                            TD[w] = ++t;
                            pai[w] = v;
                            arvore.add(new Aresta(v, w));
                            pilha.push(w);
                            encontrouSucessor = true;
                            break;
                        }
                    }
                    if (!encontrouSucessor) {
                        TT[v] = ++t;
                        pilha.pop();
                    }
                }
                return TD;
            }

            public boolean visto(int v) {
                return TD[v] != -1;  // Verifica se o vértice foi visitado
            }

            public int[] getTD() {
                return TD;  // Retorna a tabela de tempos de descoberta
            }
        }

        public static List<List<int[]>> articulacoes(Grafo g, int numV) {
            List<List<int[]>> articulacoes = new ArrayList<>();

            for (int v = 1; v < numV; v++) {
                if (g.sucessoresSize(v) > 0) {
                    List<int[]> arestasRemovidas = g.removerV(v);
                    
                    int inicio = (v == 1) ? 2 : 1;  
                    if (g.sucessoresSize(inicio) > 0) {
                        DFS dfs = new DFS(g, inicio);
                        int[] TD = dfs.getTD();
                        

                        // Verifica se há algum vértice conectado não visitado (TD == -1)
                        boolean conexo = true;
                        for (int i = 1; i < numV; i++) {
                            if (TD[i] == -1 && i!= v && i != 0 ) {
                                conexo = false;  // Grafo desconexo
                                break;
                            }
                        }

                        // Se o grafo não estiver conectado, o vértice removido é uma articulação
                        if (!conexo) {
                            articulacoes.add(arestasRemovidas);
                        }
                    }
                    g.restaurarV(arestasRemovidas);  // Restaura o vértice removido
                }
            }
            return articulacoes;
        }



        public static List<List<Integer>> blocos (List<List<int[]>> articulacoes, int numV, Grafo grafo){
            List<List<Integer>> blocos = new ArrayList<>();
            Grafo g = new Grafo(new ArrayList<>(grafo.grafo));

            boolean[] visitado = new boolean[numV];

            int[] art = new int[articulacoes.size()];
            int index = 0;
            for(List<int[]> arestas : articulacoes){
                for(int[] a : arestas){
                    art[index] = a[0];
                    index++;
                    break;
                }
            }
            

            for(List<int[]> a : articulacoes){
                for(int[] ar : a){
               g.removerV(ar[0]);
            }
            }

            for(int v =1; v< numV; v++){
                if(!visitado[v]){
                    List<Integer> blocoAtual = new ArrayList<>();

                    DFS dfs = new DFS(grafo, v);

                    for(int i=1; i< numV;i++){
                        if(dfs.visto(i)){
                            blocoAtual.add(i);
                            visitado[i] = true;
                        }
                    }

                    blocos.add(blocoAtual);

                }
            }

            for(List<int[]> a : articulacoes){
                 grafo.restaurarV(a);
            }

            

            for (int a : art) {
                Iterator<List<Integer>> iterator = blocos.iterator();
                while (iterator.hasNext()) {
                    List<Integer> b = iterator.next();
                    if (b.contains(a)) {
                        iterator.remove();
                    }
                }
            }


            for(int a : art){
               List<Integer> adj = grafo.getSucessores(a);
    
                for(List<Integer> b : blocos){
                    boolean find = false;

                    for(int v : adj){
                        if(b.contains(v)){
                        find = true;
                        break;
                            
                    }
                    }
                    if(find){
                        b.add(a);
                        b.sort(null);
                    }
                }
            }
          

            
        return blocos;
        }

    
    public static void main(String[] args) throws IOException{
        Scanner sc = new Scanner(System.in);
        long tempoInicial = System.nanoTime();


        System.out.println("Selecione o tamanho do arquivo: ");
        System.out.println("\n1 = 100 vertices || 2 = 10000 vertices");
        int caso = sc.nextInt();
        String arq = ""; 
        switch (caso) {
            case 1:
                arq = "grafos100.txt";
                break;
            case 2:
                arq = "grafo10000.txt";
                break;
            default:
                break;
        }
        
        
        Grafo grafo = criarGrafo(arq);

        List<List<int[]>> art = Articulacao.articulacoes(grafo, grafo.size());

        List<List<Integer>> blocos = Articulacao.blocos(art, grafo.size(), grafo);

         System.out.println("Blocos: ");
         for(List<Integer> b : blocos){
             System.out.println(b);
         }
    
        long tempoFinal = System.nanoTime();

        System.out.println("Tempo de execucao é: " + (tempoFinal - tempoInicial)/ 1_000_000 + " milisegundos");

        sc.close();
    }
    
}