package ui;

import model.Node;
import algorithm.*;
import javax.swing.*;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.*;
import java.util.List;

/**
 * Frame utama aplikasi pencarian rute dengan fitur animasi smooth
 * 
 * @author Farhan Fauzan Azima
 * @npm 20230202001
 */
public class MainFrame extends JFrame {
    private List<Node> nodes;
    private Map<String, Node> nodeMap;
    private GraphPanel graphPanel;
    
    // Hasil pencarian
    private List<String> gbfsPath;
    private List<String> bfsPath;
    private List<String> dfsPath;
    
    public MainFrame() {
        setTitle("Pencarian Rute Singaparna → Bandung | Farhan Fauzan A (20230202001)");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // Inisialisasi graph
        buildGraph();
        
        // Panel visualisasi
        graphPanel = new GraphPanel(nodes);
        
        // Load background map
        loadMapImage();
        
        add(graphPanel, BorderLayout.CENTER);
        
        // Panel kontrol
        JPanel controlPanel = createControlPanel();
        add(controlPanel, BorderLayout.SOUTH);
        
        // Info panel
        JPanel infoPanel = createInfoPanel();
        add(infoPanel, BorderLayout.NORTH);
        
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    /**
     * Load screenshot peta sebagai background
     */
    private void loadMapImage() {
        try {
            File mapFile = new File("assets/map.png");
            if (mapFile.exists()) {
                BufferedImage img = ImageIO.read(mapFile);
                graphPanel.setMapImage(img);
                System.out.println("✓ Peta berhasil dimuat: " + mapFile.getAbsolutePath());
            } else {
                System.out.println("⚠ File peta tidak ditemukan: assets/map.png");
                System.out.println("  Program tetap berjalan tanpa background peta.");
            }
        } catch (Exception e) {
            System.out.println("⚠ Gagal memuat peta: " + e.getMessage());
        }
    }
    
    /**
     * Panel informasi mahasiswa
     */
    private JPanel createInfoPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(44, 62, 80));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        
        JLabel titleLabel = new JLabel("UTS Nomor 10: Perbandingan Algoritma dengan Animasi Smooth Sliding");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(Color.WHITE);
        
        JLabel studentLabel = new JLabel("Nama: Farhan Fauzan Azima | NPM: 20230202001");
        studentLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        studentLabel.setForeground(new Color(236, 240, 241));
        
        JPanel textPanel = new JPanel(new GridLayout(2, 1));
        textPanel.setBackground(new Color(44, 62, 80));
        textPanel.add(titleLabel);
        textPanel.add(studentLabel);
        
        panel.add(textPanel, BorderLayout.WEST);
        
        return panel;
    }
    
    /**
     * Panel kontrol dengan tombol algoritma + animasi
     */
    private JPanel createControlPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));
        panel.setBackground(new Color(236, 240, 241));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        // === ROW 1: Algoritma Normal (Tanpa Animasi) ===
        JPanel normalPanel = new JPanel(new GridLayout(1, 4, 15, 0));
        normalPanel.setBackground(new Color(236, 240, 241));
        
        JButton gbfsButton = createStyledButton("🎯 Run GBFS", new Color(46, 204, 113));
        gbfsButton.addActionListener(e -> runGreedySearch());
        
        JButton bfsButton = createStyledButton("🔵 Run BFS", new Color(52, 152, 219));
        bfsButton.addActionListener(e -> runBFS());
        
        JButton dfsButton = createStyledButton("🟣 Run DFS", new Color(155, 89, 182));
        dfsButton.addActionListener(e -> runDFS());
        
        JButton compareButton = createStyledButton("📊 Bandingkan Semua", new Color(230, 126, 34));
        compareButton.addActionListener(e -> compareAllAlgorithms());
        
        normalPanel.add(gbfsButton);
        normalPanel.add(bfsButton);
        normalPanel.add(dfsButton);
        normalPanel.add(compareButton);
        
        // === ROW 2: Algoritma dengan Animasi Smooth ===
        JPanel animationPanel = new JPanel(new GridLayout(1, 3, 15, 0));
        animationPanel.setBackground(new Color(236, 240, 241));
        
        JButton animGbfsButton = createStyledButton("🎬 Animasi GBFS", new Color(39, 174, 96));
        animGbfsButton.addActionListener(e -> runAnimatedGBFS());
        
        JButton animBfsButton = createStyledButton("🎬 Animasi BFS", new Color(41, 128, 185));
        animBfsButton.addActionListener(e -> runAnimatedBFS());
        
        JButton animDfsButton = createStyledButton("🎬 Animasi DFS", new Color(142, 68, 173));
        animDfsButton.addActionListener(e -> runAnimatedDFS());
        
        animationPanel.add(animGbfsButton);
        animationPanel.add(animBfsButton);
        animationPanel.add(animDfsButton);
        
        // === ROW 3: Utility Buttons ===
        JPanel utilityPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        utilityPanel.setBackground(new Color(236, 240, 241));
        
        JButton clearButton = createStyledButton("🗑️ Clear", new Color(231, 76, 60));
        clearButton.addActionListener(e -> {
            graphPanel.clearPath();
            System.out.println("\n--- Visualisasi dibersihkan ---\n");
        });
        
        utilityPanel.add(clearButton);
        
        panel.add(normalPanel);
        panel.add(animationPanel);
        panel.add(utilityPanel);
        
        return panel;
    }
    
    /**
     * Helper untuk membuat tombol dengan style
     */
    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 13));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });
        
        return button;
    }
    
    // ========== ALGORITMA TANPA ANIMASI ==========
    
    /**
     * Jalankan Greedy Best-First Search
     */
    private void runGreedySearch() {
        System.out.println("\n=== GREEDY BEST-FIRST SEARCH ===");
        long startTime = System.nanoTime();
        
        Node start = nodeMap.get("SNG");
        Node goal = nodeMap.get("BND");
        
        gbfsPath = GreedySearch.search(start, goal);
        int distance = GreedySearch.calculateDistance(gbfsPath, nodeMap);
        
        long endTime = System.nanoTime();
        double executionTime = (endTime - startTime) / 1_000_000.0;
        
        System.out.println("Jalur: " + String.join(" → ", gbfsPath));
        System.out.println("Total Jarak: " + distance + " km");
        System.out.printf("Waktu Eksekusi: %.3f ms\n", executionTime);
        
        graphPanel.setPath(gbfsPath, new Color(46, 204, 113));
    }
    
    /**
     * Jalankan Breadth-First Search
     */
    private void runBFS() {
        System.out.println("\n=== BREADTH-FIRST SEARCH ===");
        long startTime = System.nanoTime();
        
        Node start = nodeMap.get("SNG");
        Node goal = nodeMap.get("BND");
        
        bfsPath = BreadthFirstSearch.search(start, goal);
        int distance = BreadthFirstSearch.calculateDistance(bfsPath, nodeMap);
        
        long endTime = System.nanoTime();
        double executionTime = (endTime - startTime) / 1_000_000.0;
        
        System.out.println("Jalur: " + String.join(" → ", bfsPath));
        System.out.println("Total Jarak: " + distance + " km");
        System.out.printf("Waktu Eksekusi: %.3f ms\n", executionTime);
        
        graphPanel.setPath(bfsPath, new Color(52, 152, 219));
    }
    
    /**
     * Jalankan Depth-First Search
     */
    private void runDFS() {
        System.out.println("\n=== DEPTH-FIRST SEARCH ===");
        long startTime = System.nanoTime();
        
        Node start = nodeMap.get("SNG");
        Node goal = nodeMap.get("BND");
        
        dfsPath = DepthFirstSearch.search(start, goal);
        int distance = DepthFirstSearch.calculateDistance(dfsPath, nodeMap);
        
        long endTime = System.nanoTime();
        double executionTime = (endTime - startTime) / 1_000_000.0;
        
        System.out.println("Jalur: " + String.join(" → ", dfsPath));
        System.out.println("Total Jarak: " + distance + " km");
        System.out.printf("Waktu Eksekusi: %.3f ms\n", executionTime);
        
        graphPanel.setPath(dfsPath, new Color(155, 89, 182));
    }
    
    // ========== ALGORITMA DENGAN ANIMASI SMOOTH ==========
    
    /**
     * Jalankan GBFS dengan animasi smooth sliding
     */
    private void runAnimatedGBFS() {
        System.out.println("\n=== ANIMASI GREEDY BEST-FIRST SEARCH ===");
        
        Node start = nodeMap.get("SNG");
        Node goal = nodeMap.get("BND");
        
        gbfsPath = GreedySearch.search(start, goal);
        
        AnimationController.startAnimation(graphPanel, gbfsPath, nodeMap, new Color(46, 204, 113));
    }
    
    /**
     * Jalankan BFS dengan animasi smooth sliding
     */
    private void runAnimatedBFS() {
        System.out.println("\n=== ANIMASI BREADTH-FIRST SEARCH ===");
        
        Node start = nodeMap.get("SNG");
        Node goal = nodeMap.get("BND");
        
        bfsPath = BreadthFirstSearch.search(start, goal);
        
        AnimationController.startAnimation(graphPanel, bfsPath, nodeMap, new Color(52, 152, 219));
    }
    
    /**
     * Jalankan DFS dengan animasi smooth sliding
     */
    private void runAnimatedDFS() {
        System.out.println("\n=== ANIMASI DEPTH-FIRST SEARCH ===");
        
        Node start = nodeMap.get("SNG");
        Node goal = nodeMap.get("BND");
        
        dfsPath = DepthFirstSearch.search(start, goal);
        
        AnimationController.startAnimation(graphPanel, dfsPath, nodeMap, new Color(155, 89, 182));
    }
    
    /**
     * Bandingkan semua algoritma
     */
    private void compareAllAlgorithms() {
        System.out.println("\n╔═══════════════════════════════════════════════════════════╗");
        System.out.println("║       PERBANDINGAN ALGORITMA PENCARIAN RUTE              ║");
        System.out.println("║  Singaparna (Tasikmalaya) → Bandung (Jl. Sunda)         ║");
        System.out.println("╚═══════════════════════════════════════════════════════════╝\n");
        
        Node start = nodeMap.get("SNG");
        Node goal = nodeMap.get("BND");
        
        // GBFS
        long startTime1 = System.nanoTime();
        gbfsPath = GreedySearch.search(start, goal);
        int gbfsDistance = GreedySearch.calculateDistance(gbfsPath, nodeMap);
        long endTime1 = System.nanoTime();
        double gbfsTime = (endTime1 - startTime1) / 1_000_000.0;
        
        // BFS
        long startTime2 = System.nanoTime();
        bfsPath = BreadthFirstSearch.search(start, goal);
        int bfsDistance = BreadthFirstSearch.calculateDistance(bfsPath, nodeMap);
        long endTime2 = System.nanoTime();
        double bfsTime = (endTime2 - startTime2) / 1_000_000.0;
        
        // DFS
        long startTime3 = System.nanoTime();
        dfsPath = DepthFirstSearch.search(start, goal);
        int dfsDistance = DepthFirstSearch.calculateDistance(dfsPath, nodeMap);
        long endTime3 = System.nanoTime();
        double dfsTime = (endTime3 - startTime3) / 1_000_000.0;
        
        // Tampilkan hasil
        System.out.printf("%-30s | %-35s | %-15s | %-15s\n", 
            "Algoritma", "Jalur", "Total Jarak", "Waktu Eksekusi");
        System.out.println("─".repeat(110));
        
        System.out.printf("%-30s | %-35s | %-15s | %.3f ms\n", 
            "Greedy Best-First Search", 
            String.join(" → ", gbfsPath), 
            gbfsDistance + " km",
            gbfsTime);
        
        System.out.printf("%-30s | %-35s | %-15s | %.3f ms\n", 
            "Breadth-First Search", 
            String.join(" → ", bfsPath), 
            bfsDistance + " km",
            bfsTime);
        
        System.out.printf("%-30s | %-35s | %-15s | %.3f ms\n", 
            "Depth-First Search", 
            String.join(" → ", dfsPath), 
            dfsDistance + " km",
            dfsTime);
        
        System.out.println("\n" + "═".repeat(110) + "\n");
        
        // Analisis
        System.out.println("📊 ANALISIS:");
        System.out.println("   • Algoritma tercepat (waktu): " + getFastestAlgorithm(gbfsTime, bfsTime, dfsTime));
        System.out.println("   • Jalur terpendek (jarak): " + getShortestPath(gbfsDistance, bfsDistance, dfsDistance));
        System.out.println();
        
        // Tampilkan semua jalur (overlay)
        graphPanel.setPath(gbfsPath, new Color(46, 204, 113));
        
        // Dialog perbandingan
        showComparisonDialog(gbfsPath, bfsPath, dfsPath, gbfsDistance, bfsDistance, dfsDistance);
    }
    
    private String getFastestAlgorithm(double gbfs, double bfs, double dfs) {
        if (gbfs <= bfs && gbfs <= dfs) return "GBFS (" + String.format("%.3f ms", gbfs) + ")";
        if (bfs <= gbfs && bfs <= dfs) return "BFS (" + String.format("%.3f ms", bfs) + ")";
        return "DFS (" + String.format("%.3f ms", dfs) + ")";
    }
    
    private String getShortestPath(int gbfs, int bfs, int dfs) {
        if (gbfs <= bfs && gbfs <= dfs) return "GBFS (" + gbfs + " km)";
        if (bfs <= gbfs && bfs <= dfs) return "BFS (" + bfs + " km)";
        return "DFS (" + dfs + " km)";
    }
    
    /**
     * Dialog perbandingan visual
     */
    private void showComparisonDialog(List<String> gbfs, List<String> bfs, List<String> dfs,
                                     int gbfsDist, int bfsDist, int dfsDist) {
        StringBuilder message = new StringBuilder();
        message.append("╔═══════════════════════════════════════════════╗\n");
        message.append("║     HASIL PERBANDINGAN ALGORITMA             ║\n");
        message.append("╚═══════════════════════════════════════════════╝\n\n");
        
        message.append("🎯 GREEDY BEST-FIRST SEARCH:\n");
        message.append("   Jalur: ").append(String.join(" → ", gbfs)).append("\n");
        message.append("   Jarak: ").append(gbfsDist).append(" km\n\n");
        
        message.append("🔵 BREADTH-FIRST SEARCH:\n");
        message.append("   Jalur: ").append(String.join(" → ", bfs)).append("\n");
        message.append("   Jarak: ").append(bfsDist).append(" km\n\n");
        
        message.append("🟣 DEPTH-FIRST SEARCH:\n");
        message.append("   Jalur: ").append(String.join(" → ", dfs)).append("\n");
        message.append("   Jarak: ").append(dfsDist).append(" km\n\n");
        
        message.append("─────────────────────────────────────────────\n");
        message.append("Farhan Fauzan Azima | NPM: 20230202001");
        
        JTextArea textArea = new JTextArea(message.toString());
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        textArea.setEditable(false);
        textArea.setBackground(new Color(236, 240, 241));
        
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(500, 350));
        
        JOptionPane.showMessageDialog(this, scrollPane, "Perbandingan Hasil", 
                                     JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Membangun graph dengan data koordinat Anda
     */
    private void buildGraph() {
        nodes = new ArrayList<>();
        nodeMap = new HashMap<>();
        
        // Buat semua node sesuai koordinat yang tepat di peta
        Node sng = new Node("SNG", "Singaparna", 698, 497, 85);
        Node grt = new Node("GRT", "Garut", 481, 360, 65);
        Node mlb = new Node("MLB", "Malangbong", 712, 216, 70);
        Node lmb = new Node("LMB", "Limbangan", 587, 168, 55);
        Node ngr = new Node("NGR", "Nagreg", 453, 160, 40);
        Node cln = new Node("CLN", "Cileunyi", 342, 78, 15);
        Node tbb = new Node("TBB", "Buah Batu", 246, 135, 5);
        Node cbr = new Node("CBR", "Cibiru", 271, 35, 10);
        Node bnd = new Node("BND", "Bandung", 170, 54, 0);
        
        // Tambahkan edge (koneksi antar kota) sesuai data Anda
        sng.addNeighbor(grt, 35);
        grt.addNeighbor(sng, 35);
        
        sng.addNeighbor(mlb, 25);
        mlb.addNeighbor(sng, 25);
        
        grt.addNeighbor(ngr, 30);
        ngr.addNeighbor(grt, 30);
        
        mlb.addNeighbor(lmb, 15);
        lmb.addNeighbor(mlb, 15);
        
        lmb.addNeighbor(ngr, 20);
        ngr.addNeighbor(lmb, 20);
        
        ngr.addNeighbor(cln, 25);
        cln.addNeighbor(ngr, 25);
        
        cln.addNeighbor(tbb, 12);
        tbb.addNeighbor(cln, 12);
        
        cln.addNeighbor(cbr, 8);
        cbr.addNeighbor(cln, 8);
        
        tbb.addNeighbor(bnd, 6);
        bnd.addNeighbor(tbb, 6);
        
        cbr.addNeighbor(bnd, 9);
        bnd.addNeighbor(cbr, 9);
        
        // Simpan ke list dan map
        nodes.addAll(Arrays.asList(sng, grt, mlb, lmb, ngr, cln, tbb, cbr, bnd));
        
        for (Node node : nodes) {
            nodeMap.put(node.getCode(), node);
        }
        
        System.out.println("✓ Graph berhasil dibuat dengan " + nodes.size() + " node");
    }
}