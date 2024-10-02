import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;
import java.io.BufferedReader; 
import java.io.FileReader;
import java.util.List;
import java.io.IOException; 

public class Tarjan{
    static class Grafo{
       List<List<Integer>> grafo;

        public Grafo(List<List<Integer>> g){
            grafo = g;
        }

        public List<Integer> getAdjacentes(int vertice){
            return grafo.get(vertice);           
        }
        public int size(){
            return grafo.size(); // Posicao 0 + vértices do grafo
        }
        
        public int sucessoresSize(int vertice){
            return grafo.get(vertice).size();
        }
    }
    public static Grafo criarGrafo(String arq) throws IOException{

        FileReader fr = new FileReader(arq); 
        BufferedReader br = new BufferedReader(fr);

        String linha = br.readLine();
        linha.trim();

        String numeros[] = linha.split("\\s+");
        int n = Integer.parseInt(numeros[0]); // Número de vértices
        //Integer.parseInt(numeros[1]); // Pular o número de arestas

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
                    int entrada = Integer.parseInt(teste[0]); // Número de vértices
                    int saida = Integer.parseInt(teste[1]); // Número de arestas
                    listaAdjacencia.get(entrada).add(saida);
                    listaAdjacencia.get(saida).add(entrada);
                }            
        }

        Grafo grafo = new Grafo(listaAdjacencia);

        br.close();

        return grafo;
    }

    public static class Aresta {
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
        private int t;
        private int td[];
        private int lowpt[];
        
        List<Aresta> arvore;
        List<Aresta> retorno;

        Grafo g;
        Stack<Aresta> pilha;
        List<Biconexo> listaBiconexo;

        public int getT(){
            return t;
        }

        public int getTdAt(int v){
            return td[v];
        } 

       
        public boolean visto(int v){
            return td[v] != 0;
        }

        public DFS(Grafo g){
            t = 0;
            td = new int[g.size()];
            lowpt = new int[g.size()];
            lowpt[1] = 1;
            for(int i = 0; i < g.size(); i++){
                lowpt[i] = Integer.MAX_VALUE;
            }
  
            arvore = new ArrayList<>();
            retorno = new ArrayList<>();

            this.g = g;
            pilha = new Stack<>();
            listaBiconexo = new ArrayList<>();

            dfs();
        }


        private void dfs(){

            for(int w = 1; w < g.size(); w++){
                if(td[w] == 0){
                    tarjan(pilha, w, 0);
                    pilha.clear();
                    t = 0;
                }
            }
        }
        
        Set<Aresta> arestasVisitadas = new HashSet<>();

        private void tarjan(Stack<Aresta> pilha, int v, int u) {
            t++;
            td[v] = t;

            List<Integer> adj = g.getAdjacentes(v);
            for (int w : adj) {
                Aresta a = new Aresta(v, w);
                Aresta duplicada = new Aresta(w, v); // Inverso da aresta A 
                // Verifica se a aresta já foi processada
                if (!arestasVisitadas.contains(duplicada)) { // Evita repetir a aresta
                    arestasVisitadas.add(duplicada);

                    if (td[w] == 0) {
                        pilha.add(a);
                        tarjan(pilha, w, v);
                        lowpt[v] = Math.min(lowpt[v], lowpt[w]);

                        if (lowpt[w] >= td[v]) {
                            Biconexo comp = new Biconexo(g.size());
                            Aresta topo = pilha.peek();
                            int u1 = topo.getOrigem();

                            while (td[u1] >= td[w]) {
                                pilha.pop();
                                comp.addAresta(topo);
                                topo = pilha.peek();
                                u1 = topo.getOrigem();
                            }
                            pilha.pop();
                            comp.addAresta(topo);
                            listaBiconexo.add(comp);
                        }
                    } else if (td[w] < td[v] && w != u) {
                        pilha.push(a);
                        lowpt[v] = Math.min(lowpt[v], td[w]);
                    }
                }
            }
        }

        //graph-test-6.txt
        //graph-test-100-1.txt
        //grafo100.txt

    }
    public static void main(String[] args) throws IOException{
        Scanner sc = new Scanner(System.in);

        System.out.println("Nome do arquivo: ");
        String arq = sc.next(); 

        Grafo grafo = criarGrafo(arq);
        
        DFS dfs = new DFS(grafo);
        int i = 0;
        for(Biconexo b : dfs.listaBiconexo){
            i++;
            System.out.println("Componente " + i);
            b.imprimir();
            System.out.println();
        }


        sc.close();
    }
    
}