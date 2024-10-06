package Ciclos;

import java.io.*;
import java.util.*;

public class Ciclos {

    private static List<List<Integer>> listaAdjacencia = new ArrayList<>();
    private static List<List<Integer>> ciclos = new ArrayList<>();
    private static List<Set<Integer>> blocos = new ArrayList<>();

    // Função para carregar o grafo do arquivo e gerar a lista de adjacências
    public static void criarGrafo(String caminhoArquivo) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo));
        String linha = br.readLine();

        // O primeiro número do arquivo indica o número de vértices
        int n = Integer.parseInt(linha.trim());

        // Inicializando as listas internas para a lista de adjacências
        listaAdjacencia = new ArrayList<>(n + 1);
        for (int i = 0; i <= n; i++) {
            listaAdjacencia.add(new ArrayList<>());
        }

        // Adicionando as arestas
        while ((linha = br.readLine()) != null) {
            linha = linha.trim();
            if (!linha.isEmpty()) {
                String[] teste = linha.split("\\s+");
                int entrada = Integer.parseInt(teste[0]);
                int saida = Integer.parseInt(teste[1]);
                listaAdjacencia.get(entrada).add(saida);
                listaAdjacencia.get(saida).add(entrada);  // Grafo não direcionado
            }
        }

        br.close();
    }

    // Função para encontrar novos ciclos
    private static void ciclos(List<Integer> caminho) {
        Integer raiz = caminho.get(0);  // Usando Integer em vez de int
        List<Integer> subCaminho;

        for (Integer adj : listaAdjacencia.get(raiz)) {
            if (!caminho.contains(adj)) {
                subCaminho = new ArrayList<>();
                subCaminho.add(adj);
                subCaminho.addAll(caminho);
                ciclos(subCaminho);
            } else if (caminho.size() > 2 && adj.equals(caminho.get(caminho.size() - 1))) {
                List<Integer> ciclo = rotacionar(caminho);
                List<Integer> cicloInvertido = inverter(ciclo);

                if (ehNovo(ciclo) && ehNovo(cicloInvertido)) {
                    ciclos.add(ciclo);
                }
            }
        }
    }

    // Função para encontrar blocos biconexos a partir dos ciclos
    private static void blocos() {
        Set<Integer> visitados = new HashSet<>();

        for (List<Integer> ciclo : ciclos) {
            boolean ehNovoBloco = false;
            Set<Integer> blocoAtual = new HashSet<>();

            for (Integer v : ciclo) {
                if (!visitados.contains(v)) {
                    ehNovoBloco = true;
                    break;
                }
            }

            if (ehNovoBloco) {
                blocoAtual.addAll(ciclo);
                visitados.addAll(ciclo);

                for (List<Integer> outroCiclo : ciclos) {
                    if (!outroCiclo.equals(ciclo) && temVerticeComum(ciclo, outroCiclo)) {
                        blocoAtual.addAll(outroCiclo);
                        visitados.addAll(outroCiclo);
                    }
                }
                blocos.add(blocoAtual);
            }
        }
    }

    // Verifica se dois ciclos têm vértices em comum
    private static boolean temVerticeComum(List<Integer> ciclo1, List<Integer> ciclo2) {
        for (Integer v : ciclo1) {
            if (ciclo2.contains(v)) {
                return true;
            }
        }
        return false;
    }

    // Inverte o caminho encontrado
    private static List<Integer> inverter(List<Integer> caminho) {
        List<Integer> caminhoInvertido = new ArrayList<>(caminho);
        Collections.reverse(caminhoInvertido);
        return rotacionar(caminhoInvertido);
    }

    // Gira o ciclo para começar pelo menor valor
    private static List<Integer> rotacionar(List<Integer> caminho) {
        int menorNo = Collections.min(caminho);
        int indiceMenor = caminho.indexOf(menorNo);
        List<Integer> caminhoRotacionado = new ArrayList<>(caminho.size());

        for (int i = 0; i < caminho.size(); i++) {
            caminhoRotacionado.add(caminho.get((i + indiceMenor) % caminho.size()));
        }

        return caminhoRotacionado;
    }

    // Verifica se o ciclo é novo
    private static boolean ehNovo(List<Integer> caminho) {
        for (List<Integer> cicloExistente : ciclos) {
            if (cicloExistente.equals(caminho)) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        long tempoInicial = System.nanoTime();

        System.out.println("Selecione o tamanho do arquivo: ");
        System.out.println("\n0 = 10 vertices || 1 = 100 vertices || 2 = 1000 vertices || 3 = 10000 vertices || 4 = 100000 vertices");
        int caso = sc.nextInt();
        String arq = ""; 
        switch (caso) {
            case 0:
                arq = "grafo10.txt";
            break;
            case 1:
                arq = "grafo100.txt";
                break;
            case 2:
                arq = "grafo1000.txt";
                break;
            case 3:
                arq = "grafo10000.txt";
                break;
            case 4:
                arq = "grafo100000.txt";
                break;
            default:
                System.out.println("Opção inválida.");
                return;
        }

        // Carregar o grafo a partir do arquivo
        try {
            criarGrafo(arq);  // Use o caminho correto para o arquivo aqui
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
            return;
        }

        // Passo 1: Encontrar todos os ciclos no grafo
        for (int i = 1; i < listaAdjacencia.size(); i++) {
            for (int vizinho : listaAdjacencia.get(i)) {
                ciclos(Collections.singletonList(i));
            }
        }

        // Passo 2: Encontrar os blocos biconexos a partir dos ciclos
        blocos();

        // Exibir os blocos biconexos
        System.out.println("\nBlocos Biconexos:");
        for (Set<Integer> bloco : blocos) {
            System.out.println(bloco);
        }

        System.out.println("Fim");
        long tempoFinal = System.nanoTime();
        System.out.println("Tempo de execução: " + (tempoFinal - tempoInicial) / 1_000_000 + " milissegundos");
    }
}
