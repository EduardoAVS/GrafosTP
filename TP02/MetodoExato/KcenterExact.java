package TP02.MetodoExato;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class KcenterExact{

    // Gerar todas as combinações
    static int iteracoes = 0;

    public static void combine(Grafo g) {
        
        int n = g.size - 1;
        int k = g.k;
        // Usando um array para manter a posição dos elementos selecionados
        int[] indices = new int[k];
        
        // Inicializando o array com os primeiros k índices
        for (int i = 1; i < k; i++) {
            indices[i] = i;
        }
        
        while (true) {
            // Adicionar a combinação atual
            List<Integer> combination = new ArrayList<>();
            for (int i = 0; i < k; i++) {
                combination.add(indices[i] + 1);  // Adicionando o valor correspondente ao índice (1-based)
            }

            // Calcular a Distância Máxima para cada Combinação

            biggestRadius(combination, g);

            System.out.println(combination + " Raio ->  " + g.radius);



            // Encontrar a próxima combinação
            int i = k - 1;
            while (i >= 0 && indices[i] == n - k + i) {
                i--;
            }
            
            // Se não for possível gerar uma nova combinação, termina
            if (i < 0) {
                break;
            }
            
            indices[i]++;
            for (int j = i + 1; j < k; j++) {
                indices[j] = indices[j - 1] + 1;
            }
        }
        
       
    }

    public static int biggestRadius(List<Integer> combination, Grafo g){
        int r = 0;
        
        int[] dist = new int[g.size + 1];
        for (int i = 1; i <= g.size; i++)
            dist[i] = Integer.MAX_VALUE;


        for(int c : combination){
            
            for(int i = 1; i < g.size; i++){
                int d = g.floydWarshall[i][c];
                if(d < dist[i]){
                    dist[i] = d;
                }
                
            }
        }

        for (int i = 1; i < g.size; i++)
            if (dist[i] > r)
                r = dist[i];

        if (r < g.radius)
            g.radius = r;

        return r;
    }
    

    // Encontrar a Combinação que Minimiza o Maior Raio

    // Retornar o Conjunto de Centros com Menor Raio
    
    // Teste/pmed1.txt
    public static void main(String[] args) throws IOException{
        Scanner sc = new Scanner(System.in);
        System.out.println("Caminho do Arquivo: ");
        String path = sc.next();

        Grafo g = new Grafo(path);
        
        long tempoInicial = System.nanoTime();
        combine(g);

        long tempoFinal = System.nanoTime();

        
        System.out.println(iteracoes);

        System.out.println("Tempo de execucao é: " + (tempoFinal - tempoInicial)/ 1_000_000 + " milisegundos");

        System.out.println("Raio: " + g.radius);
        sc.close();
    }
}