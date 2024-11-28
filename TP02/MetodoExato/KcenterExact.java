package TP02.MetodoExato;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class KcenterExact{

    
    
    public static void main(String[] args) throws IOException{
        Scanner sc = new Scanner(System.in);
        System.out.println("Caminho do Arquivo: ");
        String path = sc.next();

        Grafo g = new Grafo(path);
        g.imprimir();


        sc.close();
    }
}