package Ciclos;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Grafo {
    List<List<Integer>> grafo;

    public Grafo(String path) throws IOException{
        criarGrafo(path);
    }

    public List<Integer> getAdjacentes(int vertice){
        return grafo.get(vertice);           
    }

    public void removerAresta(int origem, int destino){
        Integer a = (Integer) origem;
        Integer b = (Integer) destino;

        grafo.get(a).remove(b); // Remove a primeira ocorrência de a e b
        grafo.get(b).remove(a); // Construtor com objeto
        
    }

    public void adicionarAresta(int origem, int destino){
        Integer a = (Integer) origem;
        Integer b = (Integer) destino;

        grafo.get(a).add(b); // Remove a primeira ocorrência de a e b
        grafo.get(b).add(a); // Construtor com objeto
        
    }

    public int size(){
        return grafo.size(); // Posicao 0 + vértices do grafo
    }
    
    public int sucessoresSize(int vertice){
        return grafo.get(vertice).size();
    }
    
    public void criarGrafo(String arq) throws IOException{
        String path = "Ciclos/";
        path += arq;

        FileReader fr = new FileReader(path); 
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

        br.close();

        this.grafo = listaAdjacencia;
    }
}

