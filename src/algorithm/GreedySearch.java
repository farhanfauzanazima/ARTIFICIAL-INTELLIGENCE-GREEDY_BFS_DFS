package algorithm;

import model.Node;
import java.util.*;

/**
 * Implementasi Greedy Best-First Search
 * Memilih node dengan heuristik terkecil ke tujuan
 * 
 * @author Farhan Fauzan Azima
 * @npm 20230202001
 */
public class GreedySearch {
    
    /**
     * Mencari jalur dari start ke goal menggunakan GBFS
     * @return List nama node yang dilalui
     */
    public static List<String> search(Node start, Node goal) {
        List<String> path = new ArrayList<>();
        Set<Node> visited = new HashSet<>();
        
        // Priority Queue berdasarkan heuristik (nilai terkecil diprioritaskan)
        PriorityQueue<Node> queue = new PriorityQueue<>(
            Comparator.comparingInt(Node::getHeuristic)
        );
        
        queue.add(start);
        
        while (!queue.isEmpty()) {
            Node current = queue.poll();
            
            // Jika sudah dikunjungi, skip
            if (visited.contains(current)) {
                continue;
            }
            
            // Tambahkan ke path dan visited
            path.add(current.getCode());
            visited.add(current);
            
            // Jika mencapai tujuan
            if (current.equals(goal)) {
                return path;
            }
            
            // Tambahkan tetangga yang belum dikunjungi ke queue
            for (Node neighbor : current.getNeighbors()) {
                if (!visited.contains(neighbor)) {
                    queue.add(neighbor);
                }
            }
        }
        
        return path; // Tidak menemukan jalur
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