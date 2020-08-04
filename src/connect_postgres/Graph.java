package connect_postgres;

import java.lang.*;
import java.io.*;
import java.util.*;

public class Graph {
    double[][] adjacencyMatrix ;
    int nVertices ;
    int n_edge = 0 ;
    public double[] shortestDistances ;
    int[] parents ;
    private static final int NO_PARENT = -1;


    public Graph(int vertex){
        nVertices = vertex ;
        adjacencyMatrix = new double[nVertices][nVertices] ;
        shortestDistances = new double[nVertices] ;
        parents = new int[nVertices] ;
        for (int i = 0; i < nVertices ; i++) {
            for (int j = 0; j < nVertices ; j++) {
                adjacencyMatrix[i][j] = 0 ;
            }
        }
    }
    public void addEdge(int u , int v , double dist){
        adjacencyMatrix[u][v] = dist ;
        adjacencyMatrix[v][u] = dist ;
        n_edge ++ ;
    }

    public double[] dijkstra( int startVertex) {

        boolean[] added = new boolean[nVertices];

        for (int vertexIndex = 0; vertexIndex < nVertices; vertexIndex++) {
            shortestDistances[vertexIndex] = Integer.MAX_VALUE;
            added[vertexIndex] = false;
        }
        shortestDistances[startVertex] = 0;
        parents[startVertex] = NO_PARENT;

        for (int i = 1; i < nVertices; i++) {
            int nearestVertex = -1;
            double shortestDistance = Double.POSITIVE_INFINITY;
            for (int vertexIndex = 0; vertexIndex < nVertices; vertexIndex++) {
                if (!added[vertexIndex] && shortestDistances[vertexIndex] < shortestDistance) {
                    nearestVertex = vertexIndex;
                    shortestDistance = shortestDistances[vertexIndex];
                }
            }

            added[nearestVertex] = true;

            //System.out.println("Here");

            for (int vertexIndex = 0; vertexIndex < nVertices; vertexIndex++) {
                double edgeDistance = adjacencyMatrix[nearestVertex][vertexIndex];

                if (edgeDistance > 0 && ((shortestDistance + edgeDistance) < shortestDistances[vertexIndex])) {
                    parents[vertexIndex] = nearestVertex;
                    shortestDistances[vertexIndex] = shortestDistance +
                            edgeDistance;
                }
            }
        }

        return shortestDistances ;
    }

    public void print()
    {
        for (int i = 0; i < nVertices ; i++) {
            for (int j = 0; j < nVertices ; j++) {
                System.out.print(adjacencyMatrix[i][j] + "\t\t\t") ;
            }
            System.out.println();
        }
    }
    /*private String printSolution(int startVertex , int destination) {
        int nVertices = shortestDistances.length;
        /*System.out.print("Vertex\t Distance\tPath");

        for (int vertexIndex = 0;
             vertexIndex < nVertices;
             vertexIndex++)
        {
            if (vertexIndex != startVertex)
            {
                System.out.print("\n" + startVertex + " -> ");
                System.out.print(vertexIndex + " \t\t ");
                System.out.print(shortestDistances[vertexIndex] + "\t\t");
                printPath(vertexIndex, parents);
            }
        }
        String path = printPath(destination);
        return  path ;
    }
    */

    public String printPath(int currentVertex)
    {
        if (currentVertex == NO_PARENT)
        {
            return "";
        }
        String s = printPath(parents[currentVertex]);
        System.out.print(currentVertex + " ");
        return currentVertex+","+s ;
    }

    public static void main(String[] args) {
        Graph g = new Graph(9) ;
        g.addEdge(0,1,4);
        g.addEdge(1,2,8);
        g.addEdge(2,3,7);
        g.addEdge(3,4,9);
        g.addEdge(4,5,.8);
        g.addEdge(5,6,2.7);
        g.addEdge(6,7,1);
        g.addEdge(7,0,8);
        g.addEdge(1,7,11);
        g.addEdge(7,8,7);
        g.addEdge(2,8,2.3);
        g.addEdge(6,8,0.9);
        g.addEdge(2,5,4);
        g.addEdge(3,5,15);

        g.dijkstra(0) ;
        String path = g.printPath(8) ;
        path = path.substring(0, path.length() - 1) ;
        path = new StringBuilder(path).reverse().toString() ;
        System.out.println("\n\n");
        System.out.println(path);
    }



}
