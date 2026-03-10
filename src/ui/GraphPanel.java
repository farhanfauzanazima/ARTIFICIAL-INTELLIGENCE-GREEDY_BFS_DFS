package ui;

import model.Node;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.util.*;
import java.util.List;

/**
 * Panel untuk menggambar peta dan visualisasi jalur dengan animasi smooth
 * 
 * @author Farhan Fauzan Azima
 * @npm 20230202001
 */
public class GraphPanel extends JPanel {
    private List<Node> nodes;
    private List<String> currentPath;
    private List<String> completePath;
    private Color pathColor;
    private BufferedImage mapImage;
    private Image animationIcon;
    
    // Untuk animasi smooth sliding
    private boolean animationActive = false;
    private double animationX;
    private double animationY;
    private int currentSegment = 0;
    @SuppressWarnings("unused")
    private double segmentProgress = 0.0;
    
    public GraphPanel(List<Node> nodes) {
        this.nodes = nodes;
        this.currentPath = new ArrayList<>();
        this.completePath = new ArrayList<>();
        this.pathColor = Color.RED;
        setPreferredSize(new Dimension(900, 600));
        setBackground(Color.WHITE);
        
        // Load gambar animasi
        loadAnimationIcon();
    }
    
    /**
     * Load gambar animasi dari assets/animasi.png
     */
    private void loadAnimationIcon() {
        try {
            File iconFile = new File("assets/animasi.png");
            if (iconFile.exists()) {
                animationIcon = ImageIO.read(iconFile);
                System.out.println("✓ Gambar animasi berhasil dimuat: " + iconFile.getAbsolutePath());
            } else {
                System.out.println("⚠ File animasi.png tidak ditemukan di assets/");
                System.out.println("  Animasi akan menggunakan ikon default.");
                // Buat ikon default (lingkaran merah)
                BufferedImage defaultIcon = new BufferedImage(40, 40, BufferedImage.TYPE_INT_ARGB);
                Graphics2D g = defaultIcon.createGraphics();
                g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g.setColor(new Color(231, 76, 60));
                g.fillOval(0, 0, 40, 40);
                g.setColor(Color.WHITE);
                g.setStroke(new BasicStroke(3));
                g.drawOval(2, 2, 36, 36);
                g.dispose();
                animationIcon = defaultIcon;
            }
        } catch (Exception e) {
            System.out.println("⚠ Gagal memuat gambar animasi: " + e.getMessage());
        }
    }
    
    /**
     * Set path yang akan digambar (tanpa animasi)
     */
    public void setPath(List<String> path, Color color) {
        this.currentPath = path;
        this.completePath = path;
        this.pathColor = color;
        this.animationActive = false;
        repaint();
    }
    
    /**
     * Set jalur lengkap setelah animasi selesai
     */
    public void setCompletePath(List<String> path) {
        this.completePath = path;
    }
    
    /**
     * Set background image (screenshot peta)
     */
    public void setMapImage(BufferedImage image) {
        this.mapImage = image;
        repaint();
    }
    
    /**
     * Aktifkan mode animasi
     */
    public void setAnimationActive(boolean active) {
        this.animationActive = active;
        if (!active) {
            // Setelah animasi selesai, tampilkan jalur lengkap
            this.currentPath = this.completePath;
        }
    }
    
    /**
     * Set posisi animasi saat ini (untuk smooth sliding)
     */
    public void setAnimationPosition(double x, double y) {
        this.animationX = x;
        this.animationY = y;
    }
    
    /**
     * Set progress animasi di segment saat ini
     */
    public void setAnimationProgress(int segment, double progress) {
        this.currentSegment = segment;
        this.segmentProgress = progress;
    }
    
    /**
     * Set warna jalur
     */
    public void setPathColor(Color color) {
        this.pathColor = color;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Gambar background peta jika ada
        if (mapImage != null) {
            g2.drawImage(mapImage, 0, 0, getWidth(), getHeight(), this);
        }
        
        // Gambar semua edge (jalur antar kota) - abu-abu tipis
        g2.setColor(new Color(200, 200, 200, 100));
        g2.setStroke(new BasicStroke(2));
        for (Node node : nodes) {
            for (Node neighbor : node.getNeighbors()) {
                g2.drawLine(node.getX(), node.getY(), neighbor.getX(), neighbor.getY());
            }
        }
        
        // Jika animasi aktif, gambar jalur bertahap
        if (animationActive && completePath.size() > 1) {
            drawAnimatedPath(g2);
        } else if (!currentPath.isEmpty()) {
            // Gambar jalur lengkap (tanpa animasi)
            drawCompletePath(g2);
        }
        
        // Gambar semua node
        drawNodes(g2);
        
        // Jika animasi aktif, gambar ikon bergerak
        if (animationActive && animationIcon != null) {
            drawMovingIcon(g2);
        }
    }
    
    /**
     * Gambar jalur yang sedang dianimasikan (bertahap)
     */
    private void drawAnimatedPath(Graphics2D g2) {
        g2.setColor(pathColor);
        g2.setStroke(new BasicStroke(4));
        
        // Gambar segment yang sudah selesai
        for (int i = 0; i < currentSegment; i++) {
            Node n1 = findNode(completePath.get(i));
            Node n2 = findNode(completePath.get(i + 1));
            
            if (n1 != null && n2 != null) {
                g2.drawLine(n1.getX(), n1.getY(), n2.getX(), n2.getY());
            }
        }
        
        // Gambar segment yang sedang berjalan (partial)
        if (currentSegment < completePath.size() - 1) {
            Node n1 = findNode(completePath.get(currentSegment));
            if (n1 != null) {
                g2.drawLine(n1.getX(), n1.getY(), (int)animationX, (int)animationY);
            }
        }
    }
    
    /**
     * Gambar jalur lengkap (setelah animasi atau mode non-animasi)
     */
    private void drawCompletePath(Graphics2D g2) {
        g2.setColor(pathColor);
        g2.setStroke(new BasicStroke(4));
        
        for (int i = 0; i < currentPath.size() - 1; i++) {
            Node n1 = findNode(currentPath.get(i));
            Node n2 = findNode(currentPath.get(i + 1));
            
            if (n1 != null && n2 != null) {
                g2.drawLine(n1.getX(), n1.getY(), n2.getX(), n2.getY());
            }
        }
    }
    
    /**
     * Gambar semua node (kota)
     */
    private void drawNodes(Graphics2D g2) {
        for (Node node : nodes) {
            boolean isInPath = currentPath.contains(node.getCode()) || 
                             completePath.contains(node.getCode());
            
            // Warna node
            if (node.getCode().equals("SNG")) {
                g2.setColor(new Color(46, 204, 113)); // Hijau untuk start
            } else if (node.getCode().equals("BND")) {
                g2.setColor(new Color(231, 76, 60)); // Merah untuk goal
            } else if (isInPath) {
                g2.setColor(new Color(255, 200, 100)); // Oranye untuk jalur
            } else {
                g2.setColor(new Color(52, 152, 219)); // Biru untuk node biasa
            }
            
            // Gambar lingkaran node
            int nodeSize = 30;
            g2.fillOval(node.getX() - nodeSize/2, node.getY() - nodeSize/2, nodeSize, nodeSize);
            
            // Border hitam
            g2.setColor(Color.BLACK);
            g2.setStroke(new BasicStroke(2));
            g2.drawOval(node.getX() - nodeSize/2, node.getY() - nodeSize/2, nodeSize, nodeSize);
            
            // Label kota
            g2.setFont(new Font("Arial", Font.BOLD, 11));
            FontMetrics fm = g2.getFontMetrics();
            String label = node.getCode();
            int labelWidth = fm.stringWidth(label);
            
            // Background putih untuk label
            g2.setColor(new Color(255, 255, 255, 200));
            g2.fillRect(node.getX() - labelWidth/2 - 3, node.getY() + 20, labelWidth + 6, 16);
            
            // Text label
            g2.setColor(Color.BLACK);
            g2.drawString(label, node.getX() - labelWidth/2, node.getY() + 32);
        }
    }
    
    /**
     * Gambar ikon bergerak (motor/mobil/foto) di posisi animasi
     */
    private void drawMovingIcon(Graphics2D g2) {
        int iconSize = 40;
        
        // Shadow untuk efek 3D
        g2.setColor(new Color(0, 0, 0, 50));
        g2.fillOval((int)animationX - iconSize/2 + 2, (int)animationY - iconSize/2 + 2, 
                   iconSize, iconSize);
        
        // Gambar ikon
        g2.drawImage(animationIcon, 
                    (int)animationX - iconSize/2, 
                    (int)animationY - iconSize/2, 
                    iconSize, iconSize, this);
        
        // Border untuk ikon
        g2.setColor(new Color(255, 255, 255, 200));
        g2.setStroke(new BasicStroke(2));
        g2.drawOval((int)animationX - iconSize/2, (int)animationY - iconSize/2, 
                   iconSize, iconSize);
    }
    
    /**
     * Mencari node berdasarkan kode
     */
    private Node findNode(String code) {
        for (Node n : nodes) {
            if (n.getCode().equals(code)) {
                return n;
            }
        }
        return null;
    }
    
    /**
     * Clear path visualization
     */
    public void clearPath() {
        this.currentPath = new ArrayList<>();
        this.completePath = new ArrayList<>();
        this.animationActive = false;
        repaint();
    }
}