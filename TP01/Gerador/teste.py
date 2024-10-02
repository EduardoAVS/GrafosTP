import networkx as nx

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

def find_articulation_points(graph):
    return list(nx.articulation_points(graph))  # Encontra os pontos de articulação no grafo

def find_blocks(graph):
    return list(nx.biconnected_components(graph))

# Caminho para o arquivo
file_path = 'grafos100.txt'

# Leitura do grafo a partir do arquivo
graph = read_graph_from_file(file_path)

# Encontrar pontos de articulação
articulation_points = find_articulation_points(graph)
blocks = find_blocks(graph)

# Imprimir os pontos de articulação
print("Pontos de Articulação:", articulation_points)
print("Blocos biconexos:", blocks)
