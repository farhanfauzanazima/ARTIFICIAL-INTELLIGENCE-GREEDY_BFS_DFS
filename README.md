# Sistem Pencarian Rute Optimal: Singaparna → Bandung

**Perbandingan Algoritma Pencarian Rute**

---

## Deskripsi Project

Implementasi dan perbandingan **3 algoritma pencarian rute** dari Singaparna (Tasikmalaya) menuju Bandung:

1. **Greedy Best-First Search (GBFS)** - Menggunakan heuristik untuk memilih jalur
2. **Breadth-First Search (BFS)** - Mencari jalur terpendek berdasarkan jumlah edge
3. **Depth-First Search (DFS)** - Menjelajahi sedalam mungkin sebelum backtrack

---

## Struktur Project

```
UTS_Greedy_BFS_DFS/
│
├── src/
│   ├── model/
│   │   └── Node.java                    # Model kota/node
│   │
│   ├── algorithm/
│   │   ├── GreedySearch.java            # Implementasi GBFS
│   │   ├── BreadthFirstSearch.java      # Implementasi BFS
│   │   └── DepthFirstSearch.java        # Implementasi DFS
│   │
│   ├── ui/
│   │   ├── GraphPanel.java              # Panel visualisasi peta
│   │   ├── MainFrame.java               # GUI utama
│   │   └── AnimationController.java     # Controller animasi
│   │
│   └── Main.java                        # Entry point program
│
├── assets/
│   ├── map.png                          # Screenshot peta (900x600px)
│   └── animasi.png                      # Gambar animasi
│
└── README.md                            # Dokumentasi ini
```

---

## Peta Node (Kota)

| Kode | Nama Lokasi | Koordinat (x, y) | Heuristik (km) |
|------|-------------|------------------|----------------|
| SNG | Singaparna   | (698, 497)       | 85             |
| GRT | Garut        | (481, 360)       | 65             |
| MLB | Malangbong   | (712, 216)       | 70             |
| LMB | Limbangan    | (587, 168)       | 55             |
| NGR | Nagreg       | (453, 160)       | 40             |
| CLN | Cileunyi     | (342, 78)        | 15             |
| TBB | Buah Batu (Toll) | (246, 135)   | 5              |
| CBR | Cibiru (Non-Toll) | (271, 35)   | 10             |
| BND | Bandung (Jl. Sunda) | (170, 54) | 0              |

---

## Koneksi Antar Kota (Edge)

| Dari | Ke  | Jarak (km) |
|------|-----|------------|
| SNG  | GRT | 35         |
| SNG  | MLB | 25         |
| GRT  | NGR | 30         |
| MLB  | LMB | 15         |
| LMB  | NGR | 20         |
| NGR  | CLN | 25         |
| CLN  | TBB | 12         |
| CLN  | CBR | 8          |
| TBB  | BND | 6          |
| CBR  | BND | 9          |

---

## Cara Menjalankan

### 1. Persiapan

Pastikan Anda sudah menginstal:
- **Java JDK 11** atau lebih baru
- **VS Code** dengan Extension Pack for Java

### 2. Setup Project

```bash
# Clone atau copy folder project
cd UTS_Greedy_BFS_DFS

# Letakkan screenshot peta di folder assets/
# File: assets/map.png (900x600 pixel)
```

### 3. Compile

```bash
# Compile semua file Java
javac -d bin src/**/*.java src/*.java
```

### 4. Run

```bash
# Jalankan program
java -cp bin Main
```

**Atau di VS Code:**
- Buka file `Main.java`
- Klik tombol **Run** atau tekan `F5`

---

## Cara Menggunakan Program

### Mode Normal (Tanpa Animasi):
1. **Run GBFS** - Jalankan algoritma Greedy Best-First Search
2. **Run BFS** - Jalankan algoritma Breadth-First Search
3. **Run DFS** - Jalankan algoritma Depth-First Search
4. **Bandingkan Semua** - Jalankan ketiga algoritma sekaligus dan tampilkan perbandingan

### Mode Animasi (Smooth Sliding): NEW!
5. **Animasi GBFS** - Lihat animasi smooth GBFS dengan gambar bergerak
6. **Animasi BFS** - Lihat animasi smooth BFS dengan gambar bergerak
7. **Animasi DFS** - Lihat animasi smooth DFS dengan gambar bergerak

### Utility:
8. **Clear** - Bersihkan visualisasi jalur

### Output

- **GUI**: Visualisasi jalur di atas peta dengan warna berbeda
  - Hijau: GBFS
  - Biru: BFS
  - Ungu: DFS
  
- **Animasi**: Gambar `animasi.png` (motor/mobil/foto) bergerak smooth mengikuti jalur
  
- **Console**: Detail jalur, jarak, dan waktu eksekusi

---

## Contoh Output Console

```
═════════════════════════════════════════════════
       PERBANDINGAN ALGORITMA PENCARIAN RUTE              
  Singaparna (Tasikmalaya) → Bandung (Jl. Sunda)         
═════════════════════════════════════════════════

Algoritma                      | Jalur                                   | Total Jarak | Waktu Eksekusi
────────────────────────────────────────────────────────────────────────────────────────────────────────
Greedy Best-First Search       | SNG → GRT → NGR → CLN → TBB → BND       | 108 km      | 0.245 ms
Breadth-First Search           | SNG → MLB → LMB → NGR → CLN → CBR → BND | 102 km      | 0.312 ms
Depth-First Search             | SNG → GRT → NGR → CLN → TBB → BND       | 108 km      | 0.198 ms

═══════════════════════════════════════════════════════════════════

ANALISIS:
   • Algoritma tercepat (waktu): DFS (0.198 ms)
   • Jalur terpendek (jarak): BFS (102 km)
```

---

## Analisis Algoritma

### Greedy Best-First Search (GBFS)
- **Kelebihan**: Cepat, efisien untuk graf besar
- **Kekurangan**: Tidak menjamin jalur optimal
- **Hasil**: Jalur cepat berdasarkan heuristik

### Breadth-First Search (BFS)
- **Kelebihan**: Menjamin jalur terpendek (jumlah edge)
- **Kekurangan**: Lebih lambat untuk graf besar
- **Hasil**: Jalur dengan jumlah edge minimum

### Depth-First Search (DFS)
- **Kelebihan**: Memory efficient, eksplorasi mendalam
- **Kekurangan**: Tidak menjamin jalur optimal
- **Hasil**: Jalur ditemukan cepat tapi tidak selalu terpendek

---

## Teknologi yang Digunakan

- **Bahasa**: Java 11+
- **GUI Framework**: Swing
- **Data Structure**: Graph (Adjacency List)
- **Algoritma**: GBFS, BFS, DFS
- **Visualisasi**: Graphics2D

---

## Catatan Penting

1. **Screenshot Peta**
   - File `map.png` harus diletakkan di folder `assets/`
   - Ukuran: 900x600 pixel
   - Jika tidak ada, program tetap berjalan tanpa background

2. **Gambar Animasi** NEW!
   - File `animasi.png` harus diletakkan di folder `assets/`
   - Format: PNG (lebih baik dengan background transparan)
   - Ukuran recommended: 40x40 pixel atau lebih besar
   - Bisa gambar motor, mobil, atau foto Anda!
   - Jika tidak ada, akan menggunakan ikon default (lingkaran merah)

3. **Koordinat Node**
   - Koordinat sudah disesuaikan dengan posisi di screenshot
   - Dapat diubah di method `buildGraph()` pada `MainFrame.java`

4. **Modifikasi Graf**
   - Tambah node: Edit method `buildGraph()`
   - Ubah edge: Gunakan method `addNeighbor()`
   - Update heuristik: Sesuaikan parameter Node

5. **Kecepatan Animasi**
   - Default: 40 frame per segment (~1.2 detik per node)
   - Dapat diubah di `AnimationController.java` (`framesPerSegment`)
   - Timer delay: 30ms (~33 FPS untuk smooth motion)

---

## Kesimpulan

Program ini berhasil mengimplementasikan **3 algoritma pencarian** dengan fitur:

Visualisasi interaktif dengan screenshot peta  
Perbandingan hasil (jalur, jarak, waktu)  
Struktur kode modular dan clean  
GUI user-friendly dengan Swing  
Output detail di console dan popup  
**Animasi Smooth Sliding** - Gambar bergerak halus mengikuti jalur
**Interpolasi posisi** - Tidak melompat, tapi sliding smooth  
**Visual effect** - Shadow dan border untuk ikon animasi  


---

## Kontak


---

**© 2024 - Artificial Intelligence - Perbandingan Algoritma Pencarian Rute**
