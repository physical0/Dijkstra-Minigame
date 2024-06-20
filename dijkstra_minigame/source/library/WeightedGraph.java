package library;
import java.util.*;

// Class to represent an edge in the graph
class Edge<T> {
    T neighbor; // The vertex that this edge connects to
    float weight; // The weight of the edge
    String label; // The label for the edge

    // Constructor, initializes the neighbor, weight, and label
    // Time complexity: O(1)
    // Space complexity: O(1)
    public Edge(T v, float w, String l) {
        this.neighbor = v;
        this.weight = w;
        this.label = l;
    }

    // Override toString method to provide a string representation of the edge
    @Override
    public String toString() {
        return "(" + neighbor + ", " + label + ")";
    }
}

public class WeightedGraph<T> {
    private Map<T, List<Edge<T>>> adj; // Adjacency list to store the graph
    boolean directed;

    // Constructor, initializes the adjacency list and the type of graph
    public WeightedGraph(boolean type) {
        adj = new HashMap<>();
        directed = type; // false: undirected, true: directed
    }

    // Add edges including adding nodes, Time O(1) Space O(1)
    public float addEdge(T a, T b, float w, String label) {
        adj.putIfAbsent(a, new ArrayList<>()); // add node
        adj.putIfAbsent(b, new ArrayList<>()); // add node
        Edge<T> edge1 = new Edge<>(b, w, label); // Create edge with label
        adj.get(a).add(edge1); // add edge
        if (!directed) { // undirected
            Edge<T> edge2 = new Edge<>(a, w, label); // Create reverse edge with label
            adj.get(b).add(edge2);
        }
        return w;
    }

    // Print graph as hashmap, Time O(V+E), Space O(1)
    public void printGraph() {
        for (T key : adj.keySet()) {
            System.out.print(key.toString() + " : ");
            List<Edge<T>> edges = adj.get(key);
            for (Edge<T> edge : edges) {
                System.out.print(edge);
            }
            System.out.println();
        }
    }

    // Shortest Paths from vertex v to all other vertices using Dijkstra's Algorithm
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public float dijkstra(T start, T end, float m) {
        Map<T, Float> res = new HashMap<>();
        PriorityQueue<Map.Entry<T, Float>> pq = new PriorityQueue<>((a, b) -> Float.compare(a.getValue(), b.getValue()));
        Map<T, T> prev = new HashMap<>();

        for (T key : adj.keySet())
            res.put(key, Float.MAX_VALUE);

        pq.offer(new AbstractMap.SimpleEntry(start, 0f));
        res.put(start, 0f);
        while (!pq.isEmpty()) {
            T u = pq.poll().getKey();

            List<Edge<T>> edges = adj.get(u);

            for (Edge<T> edge : edges) {
                T v = edge.neighbor;
                float weight = edge.weight;
                if (res.get(v) > res.get(u) + weight) {
                    res.put(v, res.get(u) + weight);
                    pq.offer(new AbstractMap.SimpleEntry(v, res.get(v)));
                    prev.put(v, u);
                }
            }
        }

        float n = res.getOrDefault(end, Float.MAX_VALUE);

        if (n == m) {
            System.out.print("\nShortest path from " + start + " to " + end + ": ");
            if (n == Float.MAX_VALUE) {
                System.out.println("There is no path");
            } else {
                printPath(end, prev);
                System.out.println(" with the total weight of " + n);
            }
        }
        
        return n;
    }

    // Helper function to print path
    private void printPath(T vertex, Map<T, T> prev) {
        if (prev.containsKey(vertex)) {
            printPath(prev.get(vertex), prev);
        }
        System.out.print(vertex + " ");
    }

    public float findPath(T start, T end) {
        if (!adj.containsKey(start) || !adj.containsKey(end)) {
            System.out.println("Start or end node is not valid.");
            return 0;
        }

        Scanner scanner = new Scanner(System.in);
        T current = start;
        float res = 0;

        System.out.println("Starting from node: " + current);

        while (!current.equals(end)) {
            System.out.println("Current node: " + current);

            // Get the list of edges for the current node
            List<Edge<T>> edges = adj.get(current);

            // Print available options
            System.out.print("Choose the next node: ");
            for (Edge<T> edge : edges) {
                System.out.print(edge.neighbor + " ");
            }
            System.out.println();

            // Read user input
            T next = null;
            while (next == null) {
                String userInput = scanner.nextLine().trim().toLowerCase();
                for (Edge<T> edge : edges) {
                    if (edge.neighbor.toString().equals(userInput)) {
                        res += edge.weight;
                        next = edge.neighbor;
                        break;
                    }
                }
                if (next == null) {
                    System.out.println("Invalid node. Please choose from the list.");
                }
            }

            // Move to the next node
            current = next;
        }
        System.out.println("Reached end node: " + current);

        return res;
    }
}
