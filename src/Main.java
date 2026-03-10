import ui.MainFrame;
import javax.swing.*;

/**
 * UTS Nomor 10: Implementasi dan Perbandingan Algoritma
 * Greedy Best-First Search, BFS, dan DFS
 * untuk Pencarian Rute Singaparna → Bandung
 * 
 * @author Farhan Fauzan Azima
 * @npm 20230202001
 * @date November 2024
 */
public class Main {
    public static void main(String[] args) {
        // Set Look and Feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // Gunakan default jika gagal
        }
        
        // Header console
        printHeader();
        
        // Jalankan GUI di Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            new MainFrame();
        });
    }
    
    /**
     * Tampilkan header informasi di console
     */
    private static void printHeader() {
        System.out.println("\n╔═══════════════════════════════════════════════════════════════════╗");
        System.out.println("║                  SISTEM PENCARIAN RUTE OPTIMAL                    ║");
        System.out.println("║            Singaparna (Tasikmalaya) → Bandung                     ║");
        System.out.println("╠═══════════════════════════════════════════════════════════════════╣");
        System.out.println("║  Tugas        : UTS Nomor 10 - Perbandingan Algoritma            ║");
        System.out.println("║  Algoritma    : Greedy Best-First Search, BFS, DFS               ║");
        System.out.println("║  Nama         : Farhan Fauzan Azima                               ║");
        System.out.println("║  NPM          : 20230202001                                       ║");
        System.out.println("╚═══════════════════════════════════════════════════════════════════╝\n");
        
        System.out.println("📍 Node yang tersedia:");
        System.out.println("   SNG: Singaparna (Start)");
        System.out.println("   GRT: Garut");
        System.out.println("   MLB: Malangbong");
        System.out.println("   LMB: Limbangan");
        System.out.println("   NGR: Nagreg");
        System.out.println("   CLN: Cileunyi");
        System.out.println("   TBB: Turangga/Buah Batu (Toll Exit)");
        System.out.println("   CBR: Cibiru (Non-Toll Entry)");
        System.out.println("   BND: Bandung/Jl. Sunda (Goal)\n");
        
        System.out.println("🎮 Cara Menggunakan:");
        System.out.println("   1. Klik tombol algoritma untuk melihat jalur");
        System.out.println("   2. Gunakan 'Bandingkan Semua' untuk melihat perbandingan");
        System.out.println("   3. Hasil detail ditampilkan di console ini");
        System.out.println("   4. Visualisasi ditampilkan di window GUI\n");
        
        System.out.println("⚠️  Catatan:");
        System.out.println("   • Letakkan file 'map.png' di folder 'assets/'");
        System.out.println("   • Jika peta tidak ditemukan, program tetap berjalan");
        System.out.println("   • Visualisasi akan menampilkan node dan jalur\n");
        
        System.out.println("═".repeat(71));
        System.out.println();
    }
}