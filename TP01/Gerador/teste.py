from itertools import combinations
import networkx as nx

# Função para ler o grafo a partir de um arquivo
def read_graph_from_file(file_path):
    with open(file_path, 'r') as file:
        n = int(file.readline().strip())  # Lê a primeira linha para obter o número de vértices
        edges = [
            tuple(map(int, line.strip().split()))  # Cria uma tupla de dois inteiros a partir de cada linha
            for line in file if line.strip()  # Lê apenas as linhas não vazias
        ]
    
    G = nx.Graph()
    G.add_edges_from(edges)  # Adiciona as arestas ao grafo
    return G

# Função para encontrar os caminhos disjuntos de vértice entre todos os pares de vértices
def find_disjoint_paths(graph):
    pares_vertices = combinations(graph.nodes(), 2)  # Gera combinações de todos os pares de vértices

    for origem, destino in pares_vertices:
        try:
            caminhos_vertice_disjuntos = list(nx.node_disjoint_paths(graph, origem, destino))
        except nx.NetworkXNoPath:
            caminhos_vertice_disjuntos = []  # Nenhum caminho encontrado

        # Imprime os caminhos disjuntos de vértice encontrados
        print(f'Caminhos disjuntos de vértice entre {origem} e {destino}:')
        if caminhos_vertice_disjuntos:
            for caminho in caminhos_vertice_disjuntos:
                print(caminho)
        else:
            print("Nenhum caminho disjunto de vértice encontrado.")

# Função para encontrar pontos de articulação no grafo
def find_articulation_points(graph):
    return list(nx.articulation_points(graph))  # Encontra os pontos de articulação no grafo

# Função para encontrar os blocos biconexos no grafo
def find_blocks(graph):
    return list(nx.biconnected_components(graph))  # Encontra os blocos biconexos

# Caminho para o arquivo contendo o grafo
file_path = 'grafos100.txt'

# Leitura do grafo a partir do arquivo
graph = read_graph_from_file(file_path)

# Encontrar e imprimir caminhos disjuntos de vértice entre todos os pares de vértices
find_disjoint_paths(graph)

# Encontrar pontos de articulação
articulation_points = find_articulation_points(graph)
print("\nPontos de Articulação:", articulation_points)

# Encontrar blocos biconexos
blocks = find_blocks(graph)
print("\nBlocos biconexos:")
for block in blocks:
    print(block)
