import java.util.*;
import library.WeightedGraph;


public class MainProgram {
    public static void main(String[] args) {
        // Create vertices
        MyVertex v1 = new MyVertex("a");
        MyVertex v2 = new MyVertex("b");
        MyVertex v3 = new MyVertex("c");
        MyVertex v4 = new MyVertex("d");
        MyVertex v5 = new MyVertex("e");
        MyVertex v6 = new MyVertex("f");

        // Create a weighted graph
        WeightedGraph<MyVertex> WG = new WeightedGraph<>(false); // Directed
        Scanner scan = new Scanner(System.in);

        // Add edges to the graph
        WG.addEdge(v1, v2, 4.0f, "2 + 2");
        WG.addEdge(v1, v3, 6.0f, "5 + 1");
        WG.addEdge(v2, v3, 3.0f, "3");
        WG.addEdge(v2, v4, 2.0f, "1 + 1");
        WG.addEdge(v3, v6, 2.0f, "2");
        WG.addEdge(v4, v5, 4.0f, "2 + 2");
        WG.addEdge(v5, v3, 2.0f, "2");
        WG.addEdge(v4, v6, 2.0f, "2");
        WG.addEdge(v5, v6, 1.0f, "1");

        boolean n = true;
        while (n) {
            // The graph visualization
            System.out.println("Dijkstra Minigame Map:");
            WG.printGraph();
            System.out.println("\nFind the shortest path to " + v6);

            // Find paths
            float b = WG.findPath(v1, v6);
            float a = WG.dijkstra(v1, v6, b);

            // Calculate error rate
            float err = ((float) Math.abs(a - b) / a) * 100;

            System.out.println("\nDijkstra weight: " + a);
            System.out.println("\nUser weight: " + b);

            // Output error rate
            if (a != b) {
                System.out.println("\n You got the wrong path!");
            }
            else{
                System.out.println("Congratulations!");
            }
            System.out.println("Your Error Rate is = " + err + "%");

            System.out.println("\n Do you want to play again? (yes/no)");

            String playAgain = scan.next().trim().toLowerCase();

            if (!playAgain.equals("yes")) {
                System.out.println("Thanks for playing!");
                n = false;
            }
        }
        scan.close();
    }
}
