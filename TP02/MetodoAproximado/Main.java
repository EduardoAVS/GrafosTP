package TP02.MetodoAproximado;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);

        String path = "TP02/Teste/pmed40.txt";

        String outputPath = "TP02/Resultados/Aproximado/pmed40_result.txt";

        Grafo g = new Grafo(path);

        KcenterApprox kcenter = new KcenterApprox(g);
        long startTime = System.nanoTime();

        kcenter.GreedyMethod();
        kcenter.calculateRadius();

        long endTime = System.nanoTime();
        long duration = (endTime - startTime);

        // Resultados
        String numK = "Numero de clusters:" + g.getK();
        String centros = "Centros encontrados: " + kcenter.getCenters();
        String raio = "Raio: " + kcenter.getRadius();
        String tempoExecucao = "Tempo de execucao: " + duration / 1_000_000.0 + " ms";


        System.out.println(numK);
        System.out.println(centros);
        System.out.println(raio);
        System.out.println(tempoExecucao);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath))) {

            writer.write(numK);
            writer.newLine();
            writer.write(centros);
            writer.newLine();
            writer.write(raio);
            writer.newLine();
            writer.write(tempoExecucao);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Erro ao escrever no arquivo: " + e.getMessage());
        }

        sc.close();
    }
}
