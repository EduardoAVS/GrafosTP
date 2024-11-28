package TP02.MetodoExato;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Grafo{
    private int[][] grafo;
    private int nVertex; // Número de vértices + '0'
    private int nEdge;
    private int k;

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
        this.nVertex = n + 1; // Conta o valor do 0 no número de vértices
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

        this.grafo = adjMatrix;
        br.close();

    }

    public void imprimir(){
        for(int i = 1; i < nVertex; i++){
            for(int j = 1; j < nVertex; j++){
                System.out.print(grafo[i][j] + " ");
            }
            System.out.println();
        }
    }
}