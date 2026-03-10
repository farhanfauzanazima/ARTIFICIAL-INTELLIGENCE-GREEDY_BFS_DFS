package algorithm;

import model.Node;
import java.util.*;

/**
 * Implementasi Breadth-First Search
 * Menjelajahi level per level untuk menemukan jalur terpendek
 * 
 * @author Farhan Fauzan Azima
 * @npm 20230202001
 */
public class BreadthFirstSearch {
    
    /**
     * Mencari jalur dari start ke goal menggunakan BFS
     * @return List nama node yang dilalui
     */
    public static List<String> search(Node start, Node goal) {
        Queue<Node> queue = new LinkedList<>();
        Map<Node, Node> parent = new HashMap<>();
        Set<Node> visited = new HashSet<>();
        
        queue.add(start);
        visited.add(start);
        parent.put(start, null);
        
        while (!queue.isEmpty()) {
            Node current = queue.poll();
            
            // Jika mencapai tujuan, rekonstruksi path
            if (current.equals(goal)) {
                return reconstructPath(parent, start, goal);
            }
            
            // Jelajahi semua tetangga
            for (Node neighbor : current.getNeighbors()) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    parent.put(neighbor, current);
                    queue.add(neighbor);
                }
            }
        }
        
        // Jika tidak menemukan jalur
        return new ArrayList<>();
    }
    
    /**
     * Rekonstruksi jalur dari parent map
     */
    private static List<String> reconstructPath(Map<Node, Node> parent, Node start, Node goal) {
        List<String> path = new ArrayList<>();
        Node current = goal;
        
        // Bangun path dari goal ke start
        while (current != null) {
            path.add(0, current.getCode()); // Tambahkan di depan
            current = parent.get(current);
        }
        
        return path;
    }
    
    /**
     * Menghitung total jarak dari jalur yang ditempuh
     */
    public static int calculateDistance(List<String> path, Map<String, Node> nodeMap) {
        int total = 0;
        
        for (int i = 0; i < path.size() - 1; i++) {
            Node current = nodeMap.get(path.get(i));
            Node next = nodeMap.get(path.get(i + 1));
            
            if (current != null && next != null) {
                total += current.getCostTo(next);
            }
        }
        
        return total;
    }
}