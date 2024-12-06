package TP02.MetodoAproximado;

import java.util.HashSet;
import java.util.Set;

public class KcenterApprox {
    private int n;
    private int k;
    private int[][] adjMatrix;
    private int[][] distMatrix;
    private Set<Integer> centers;
    private int r;

    public KcenterApprox(Grafo graph) {
        this.n = graph.getNvertex();
        this.k = graph.getK();
        this.adjMatrix = graph.getAdjMatrix();
        this.distMatrix = FloydWarshall();
        this.centers = new HashSet<>();
        this.r = -1;
    }

    public int[][] FloydWarshall() {
        int[][] distMatrix = new int[n][n];
        for (int i = 1; i < n; i++) {
            System.arraycopy(adjMatrix[i], 0, distMatrix[i], 0, n);
        }
        for (int k = 1; k < n; k++) {
            for (int i = 1; i < n; i++) {
                for (int j = 1; j < n; j++) {
                    if (distMatrix[i][k] > 0 && distMatrix[k][j] > 0) {
                        distMatrix[i][j] = distMatrix[i][j] == 0 
                            ? distMatrix[i][k] + distMatrix[k][j]
                            : Math.min(distMatrix[i][j], distMatrix[i][k] + distMatrix[k][j]);
                    }
                }
            }
        }
        return distMatrix;
    }

    public int HighestDegree() {
        int maiorGrau = -1;
        int vertice = -1;
        for (int i = 1; i < n; i++) {
            int grau = 0;
            for (int j = 1; j < n; j++) {
                if (adjMatrix[i][j] != 0) {
                    grau++;
                }
            }
            if (grau > maiorGrau) {
                maiorGrau = grau;
                vertice = i;
            }
        }
        return vertice;
    }

    public void GreedyMethod() {
        int k1 = HighestDegree();
        centers.add(k1);

        int[] minDist = distMatrix[k1].clone();

        while (centers.size() < k) {
            int maxMinDist = -1;
            int newK = -1;
            for (int i = 1; i < n; i++) {
                if (!centers.contains(i) && minDist[i] > maxMinDist) {
                    maxMinDist = minDist[i];
                    newK = i;
                }
            }
            centers.add(newK);
            for (int i = 1; i < n; i++) {
                minDist[i] = Math.min(minDist[i], distMatrix[i][newK]);
            }
        }
    }

    public int calculateRadius() {
        r = -1;

        for (int i = 1; i < n; i++) {
            int distMin = Integer.MAX_VALUE;
            for (int center : centers) {
                distMin = Math.min(distMin, distMatrix[i][center]);
            }

            r = Math.max(r, distMin);
            
        }

        return r;
    }

    public int getRadius() {
        return r;
    }

    public Set<Integer> getCenters() {
        return centers;
    }

    public int[][] getDistMatrix() {
        return distMatrix;
    }
}
