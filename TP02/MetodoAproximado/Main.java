package TP02.MetodoAproximado;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        
        String path = "TP02/Teste/pmed3.txt";  
        
        Grafo g = new Grafo(path);  

        KcenterApprox kcenter = new KcenterApprox(g);  
        long startTime = System.nanoTime();  

        kcenter.GreedyMethod();
        kcenter.calculateRadius();  

        long endTime = System.nanoTime(); 
        long duration = (endTime - startTime);  
        
        System.out.println("Centros encontrados: " + kcenter.getCenters());
        System.out.println("Raio: " + kcenter.getRadius() );
        System.out.println("Tempo de execução: " + duration / 1_000_000.0 + " ms");  

        sc.close();  
    }
}
