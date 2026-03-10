package algorithm;

import model.Node;
import java.util.*;

/**
 * Implementasi Depth-First Search
 * Menjelajahi sedalam mungkin sebelum backtrack
 * 
 * @author Farhan Fauzan Azima
 * @npm 20230202001
 */
public class DepthFirstSearch {
    
    /**
     * Mencari jalur dari start ke goal menggunakan DFS (iteratif dengan Stack)
     * @return List nama node yang dilalui
     */
    public static List<String> search(Node start, Node goal) {
        Stack<Node> stack = new Stack<>();
        Map<Node, Node> parent = new HashMap<>();
        Set<Node> visited = new HashSet<>();
        
        stack.push(start);
        parent.put(start, null);
        
        while (!stack.isEmpty()) {
            Node current = stack.pop();
            
            // Skip jika sudah dikunjungi
            if (visited.contains(current)) {
                continue;
            }
            
            visited.add(current);
            
            // Jika mencapai tujuan, rekonstruksi path
            if (current.equals(goal)) {
                return reconstructPath(parent, start, goal);
            }
            
            // Tambahkan tetangga ke stack (reverse order untuk urutan yang konsisten)
            List<Node> neighbors = new ArrayList<>(current.getNeighbors());
            Collections.reverse(neighbors);
            
            for (Node neighbor : neighbors) {
                if (!visited.contains(neighbor) && !parent.containsKey(neighbor)) {
                    parent.put(neighbor, current);
                    stack.push(neighbor);
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