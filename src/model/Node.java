package model;

import java.util.*;

/**
 * Representasi Node (Kota) dalam Graf
 * Digunakan untuk algoritma pencarian rute
 * 
 * @author Farhan Fauzan Azima
 * @npm 20230202001
 */
public class Node {
    private String code;           // Kode kota (SNG, GRT, dll)
    private String name;           // Nama lengkap kota
    private int x, y;              // Koordinat pixel di peta
    private int heuristic;         // Nilai heuristik ke tujuan (km)
    private List<Node> neighbors;  // Daftar tetangga
    private Map<Node, Integer> cost; // Jarak ke tetangga (km)
    
    public Node(String code, String name, int x, int y, int heuristic) {
        this.code = code;
        this.name = name;
        this.x = x;
        this.y = y;
        this.heuristic = heuristic;
        this.neighbors = new ArrayList<>();
        this.cost = new HashMap<>();
    }
    
    /**
     * Menambahkan tetangga dengan jarak tertentu
     */
    public void addNeighbor(Node neighbor, int distance) {
        if (!neighbors.contains(neighbor)) {
            neighbors.add(neighbor);
            cost.put(neighbor, distance);
        }
    }
    
    /**
     * Mendapatkan jarak ke tetangga tertentu
     */
    public int getCostTo(Node neighbor) {
        return cost.getOrDefault(neighbor, Integer.MAX_VALUE);
    }
    
    // Getters
    public String getCode() { return code; }
    public String getName() { return name; }
    public int getX() { return x; }
    public int getY() { return y; }
    public int getHeuristic() { return heuristic; }
    public List<Node> getNeighbors() { return neighbors; }
    
    @Override
    public String toString() {
        return code;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Node)) return false;
        Node other = (Node) obj;
        return code.equals(other.code);
    }
    
    @Override
    public int hashCode() {
        return code.hashCode();
    }
}