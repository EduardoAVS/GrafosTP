package TP02.MetodoExato;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class Grafo{
    public int[][] adjMatrix;
    public int size; // Número de vértices + '0'
    public int nEdge;
    public int k;
    public int radius;
    public int[][] floydWarshall;
    public List<Integer> centers;

    public Grafo(String arq) throws IOException{
        FileReader fr = new FileReader(arq); 
        BufferedReader br = new BufferedReader(fr);

        String linha = br.readLine();
        linha = linha.trim(); 

        String numeros[] = linha.split("\\s+");
        int n = Integer.parseInt(numeros[0]); // Número de vértices
        int e = Integer.parseInt(numeros[1]); // Número de Arestas
        int k = Integer.parseInt(numeros[2]); // K

        int[][] adjMatrix = new int[n + 1][n + 1];
        this.size = n;
        this.nEdge = e;
        this.k = k;

        // Adicionando arestas
        while((linha = br.readLine()) != null){
            linha = linha.trim();
                if (!linha.isEmpty()) {
                    String[] teste = linha.split("\\s+");
                    
                    int entrada = Integer.parseInt(teste[0]); 
                    int saida = Integer.parseInt(teste[1]); 
                    int capacidade = Integer.parseInt(teste[2]);
                    adjMatrix[entrada][saida] = capacidade;
                    adjMatrix[saida][entrada] = capacidade;
                   
                }            
        }

        this.adjMatrix= adjMatrix;
        this.radius = Integer.MAX_VALUE;
        floydWarshall();
        br.close();

    }

    public void floydWarshall() {
        int[][] dist = new int[size + 1][size + 1];

        // Inicializa a matriz de distâncias com os valores da matriz de adjacência
        for (int i = 1; i <= size; i++) {
            for (int j = 1; j <= size; j++) {
                if (i == j) {
                    dist[i][j] = 0; // A distância de um vértice para ele mesmo é 0
                } else {
                    dist[i][j] = adjMatrix[i][j] == 0 ? Integer.MAX_VALUE : adjMatrix[i][j];
                }
            }
        }

        // Algoritmo de Floyd-Warshall
        for (int k = 1; k <= size; k++) {
            for (int i = 1; i <= size; i++) {
                for (int j = 1; j <= size; j++) {
                    if (dist[i][k] != Integer.MAX_VALUE && dist[k][j] != Integer.MAX_VALUE) {
                        dist[i][j] = Math.min(dist[i][j], dist[i][k] + dist[k][j]);
                    }
                }
            }
        }
        floydWarshall = dist;
    }
    public void imprimir(){
        for(int i = 1; i < size; i++){
            for(int j = 1; j < size; j++){
                System.out.print(adjMatrix[i][j] + " ");
            }
            System.out.println();
        }
    }

}