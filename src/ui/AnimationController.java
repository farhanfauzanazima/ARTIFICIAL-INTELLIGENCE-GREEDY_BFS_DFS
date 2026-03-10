package ui;

import model.Node;
import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;

/**
 * Controller untuk animasi smooth sliding
 * Gambar bergerak halus mengikuti jalur, bukan melompat
 * 
 * @author Farhan Fauzan Azima
 * @npm 20230202001
 */
public class AnimationController {
    
    private GraphPanel panel;
    private List<String> path;
    private Map<String, Node> nodeMap;
    private Timer animationTimer;
    
    // Interpolasi posisi
    private int currentSegment = 0;  // Segment jalur yang sedang dilewati
    private int currentFrame = 0;    // Frame saat ini dalam segment
    private int framesPerSegment = 40; // Jumlah frame untuk transisi antar node
    
    private double interpolatedX;
    private double interpolatedY;
    
    private Color pathColor;
    
    /**
     * Mulai animasi smooth sliding
     */
    public static void startAnimation(GraphPanel panel, List<String> path, 
                                     Map<String, Node> nodeMap, Color pathColor) {
        AnimationController controller = new AnimationController(panel, path, nodeMap, pathColor);
        controller.start();
    }
    
    /**
     * Constructor
     */
    private AnimationController(GraphPanel panel, List<String> path, 
                               Map<String, Node> nodeMap, Color pathColor) {
        this.panel = panel;
        this.path = path;
        this.nodeMap = nodeMap;
        this.pathColor = pathColor;
        
        // Set posisi awal di node pertama
        if (!path.isEmpty()) {
            Node startNode = nodeMap.get(path.get(0));
            if (startNode != null) {
                this.interpolatedX = startNode.getX();
                this.interpolatedY = startNode.getY();
            }
        }
    }
    
    /**
     * Mulai animasi
     */
    private void start() {
        if (path.size() < 2) {
            System.out.println("⚠ Path terlalu pendek untuk animasi");
            return;
        }
        
        System.out.println("\n🎬 === ANIMASI DIMULAI ===");
        System.out.println("Jalur: " + String.join(" → ", path));
        System.out.println("Total Segment: " + (path.size() - 1));
        System.out.println();
        
        // Reset state
        currentSegment = 0;
        currentFrame = 0;
        panel.setAnimationActive(true);
        panel.setPathColor(pathColor);
        
        // Timer untuk animasi (30ms = ~33 FPS untuk smooth motion)
        animationTimer = new Timer(30, e -> updateAnimation());
        animationTimer.start();
    }
    
    /**
     * Update setiap frame animasi
     */
    private void updateAnimation() {
        if (currentSegment >= path.size() - 1) {
            // Animasi selesai
            finishAnimation();
            return;
        }
        
        // Node awal dan tujuan di segment ini
        Node fromNode = nodeMap.get(path.get(currentSegment));
        Node toNode = nodeMap.get(path.get(currentSegment + 1));
        
        if (fromNode == null || toNode == null) {
            currentSegment++;
            currentFrame = 0;
            return;
        }
        
        // Interpolasi posisi (smooth sliding)
        double progress = (double) currentFrame / framesPerSegment;
        interpolatedX = fromNode.getX() + (toNode.getX() - fromNode.getX()) * progress;
        interpolatedY = fromNode.getY() + (toNode.getY() - fromNode.getY()) * progress;
        
        // Update panel dengan posisi baru
        panel.setAnimationPosition(interpolatedX, interpolatedY);
        panel.setAnimationProgress(currentSegment, progress);
        panel.repaint();
        
        // Lanjut ke frame berikutnya
        currentFrame++;
        
        // Jika segment ini selesai, pindah ke segment berikutnya
        if (currentFrame >= framesPerSegment) {
            currentSegment++;
            currentFrame = 0;
            
            // Log progress
            if (currentSegment < path.size()) {
                System.out.println("✓ Mencapai node: " + path.get(currentSegment) + 
                                 " (" + (currentSegment + 1) + "/" + path.size() + ")");
            }
        }
    }
    
    /**
     * Selesaikan animasi
     */
    private void finishAnimation() {
        animationTimer.stop();
        panel.setAnimationActive(false);
        panel.setCompletePath(path); // Tampilkan jalur lengkap
        panel.repaint();
        
        System.out.println("\n✅ === ANIMASI SELESAI ===");
        System.out.println("Jalur lengkap: " + String.join(" → ", path));
        System.out.println();
        
        // Hitung total jarak
        int totalDistance = calculateTotalDistance();
        
        // Dialog hasil
        showCompletionDialog(totalDistance);
    }
    
    /**
     * Hitung total jarak jalur
     */
    private int calculateTotalDistance() {
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
    
    /**
     * Dialog setelah animasi selesai
     */
    private void showCompletionDialog(int distance) {
        SwingUtilities.invokeLater(() -> {
            StringBuilder message = new StringBuilder();
            message.append("🎬 ANIMASI SELESAI!\n\n");
            message.append("═══════════════════════════════════\n\n");
            message.append("📍 Jalur yang dilalui:\n");
            message.append("   ").append(String.join(" → ", path)).append("\n\n");
            message.append("📏 Total Jarak: ").append(distance).append(" km\n\n");
            message.append("═══════════════════════════════════\n\n");
            message.append("Nama: Farhan Fauzan Azima\n");
            message.append("NPM: 20230202001");
            
            JTextArea textArea = new JTextArea(message.toString());
            textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
            textArea.setEditable(false);
            textArea.setBackground(new Color(236, 240, 241));
            
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(400, 300));
            
            JOptionPane.showMessageDialog(null, scrollPane, 
                "Animasi Selesai", JOptionPane.INFORMATION_MESSAGE);
        });
    }
    
    /**
     * Set kecepatan animasi
     * @param speed 1=lambat, 2=normal, 3=cepat
     */
    public void setSpeed(int speed) {
        switch (speed) {
            case 1: // Lambat
                framesPerSegment = 60;
                if (animationTimer != null) animationTimer.setDelay(40);
                break;
            case 2: // Normal
                framesPerSegment = 40;
                if (animationTimer != null) animationTimer.setDelay(30);
                break;
            case 3: // Cepat
                framesPerSegment = 20;
                if (animationTimer != null) animationTimer.setDelay(20);
                break;
        }
    }
}