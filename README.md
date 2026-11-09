# TokoApp - Aplikasi Daftar Produk Toko

Tugas Individu Pemrograman Lanjut (Promlan): memodifikasi aplikasi hasil praktikum menjadi aplikasi pencatatan produk toko menggunakan **JavaFX**, **FXML**, dan **Maven**.

**Nama:** isi nama kamu
**NIM:** isi NIM kamu
**Kelas:** isi kelas kamu

## Fitur

1. **Form tambah produk** dengan isian nama, kategori (Makanan/Minuman), dan harga. Kode produk dibuat otomatis (PR001, PR002, ...).
2. **Tabel produk** yang menampilkan kode, nama, kategori, dan harga.
3. **Pencarian berdasarkan nama** produk lewat tombol **Cari** (juga lewat tombol Enter). Tombol **Reset** menampilkan semua produk lagi.
4. **Tombol Lihat Detail** untuk membuka halaman detail produk yang dipilih di tabel.

## Aturan Validasi

- Nama produk tidak boleh kosong.
- Harga harus berupa bilangan bulat lebih dari nol.
- Kategori harus Makanan atau Minuman.
- Input yang tidak valid ditolak dan pesan kesalahan tampil di bawah form.

## Struktur Proyek

```
TokoApp/
├── pom.xml
└── src/main/
    ├── java/kelas/
    │   ├── MainApp.java              # entry point aplikasi
    │   ├── Navigator.java            # pindah halaman, mengirim objek Produk
    │   ├── model/Produk.java         # class model (private, constructor, getter)
    │   ├── data/DataProduk.java      # penyimpanan sementara di RAM
    │   └── controller/
    │       ├── DaftarController.java # form, pencarian, tabel
    │       └── DetailController.java # halaman detail
    └── resources/kelas/
        ├── daftar.fxml
        ├── detail.fxml
        └── styles/app.css
```

## Penerapan OOP

- **Class `Produk`** memiliki atribut `private final` (kode, nama, kategori, harga), constructor, dan getter. Constructor menolak data yang tidak valid, sehingga objek `Produk` yang sudah terbentuk selalu valid.
- **Halaman detail menerima objek `Produk`** yang dipilih, bukan teks yang ditulis langsung di kode (`Navigator.bukaDetail(Produk)`).
- **Data disimpan di RAM** selama aplikasi berjalan (`DataProduk`). Tidak memakai database, gambar, atau inheritance.

## Cara Menjalankan

**Kebutuhan:** JDK 17 atau lebih baru, Maven, dan koneksi internet saat pertama kali (untuk mengunduh dependensi JavaFX).

Dari terminal:

```
cd TokoApp
mvn clean javafx:run
```

Dari NetBeans: buka folder `TokoApp` sebagai proyek, klik kanan proyek, pilih **Run Maven > Goals...**, isi `clean javafx:run`, lalu OK.

> Gunakan goal `javafx:run`, bukan tombol Run biasa, agar komponen JavaFX terbaca.

## Hasil Pengujian

Data uji yang dimasukkan:

| Kode  | Nama      | Kategori | Harga |
|-------|-----------|----------|-------|
| PR001 | bakso     | Makanan  | 15000 |
| PR002 | mie ayam  | Makanan  | 12000 |
| PR003 | teh tawar | Minuman  | 2000  |

Ringkasan:

| No | Skenario pengujian | Hasil |
|----|--------------------|-------|
| 1 | Ketiga produk muncul pada tabel | Berhasil |
| 2 | Pencarian menampilkan produk yang sesuai | Berhasil |
| 3 | Tombol Lihat Detail menampilkan produk yang dipilih | Berhasil |
| 4 | Nama kosong atau harga tidak valid ditolak | Berhasil |

### 1. Ketiga produk muncul pada tabel

Setelah tiga produk ditambahkan, ketiganya tampil pada tabel lengkap dengan kode, nama, kategori, dan harga. Pesan hijau "Produk PR003 berhasil ditambahkan." muncul setelah produk terakhir disimpan.

![Tiga produk pada tabel](GANTI-NAMA-FILE-1.png)

### 2. Pencarian menampilkan produk yang sesuai

Kata kunci `teh tawar` diketik pada kolom pencarian lalu tombol **Cari** ditekan. Tabel hanya menampilkan PR003 (teh tawar), sedangkan produk lain disaring. Tombol **Reset** mengembalikan seluruh produk.

![Hasil pencarian teh tawar](GANTI-NAMA-FILE-2.png)

### 3. Tombol Lihat Detail menampilkan produk yang dipilih

Baris PR002 (mie ayam) dipilih pada tabel, lalu tombol **Lihat Detail** ditekan. Halaman detail menampilkan data objek yang dipilih: kode PR002, nama mie ayam, kategori Makanan, dan harga Rp12.000. Tombol **Kembali ke Daftar** membawa pengguna kembali ke tabel.

![Halaman detail produk](GANTI-NAMA-FILE-3.png)

### 4. Nama kosong dan harga tidak valid ditolak

**a. Nama kosong.** Kolom nama dikosongkan dan harga diisi `10000`, lalu **Tambah Produk** ditekan. Produk tidak disimpan dan muncul pesan merah "Nama produk wajib diisi." Tabel tetap berisi tiga produk.

![Nama kosong ditolak](GANTI-NAMA-FILE-4.png)

**b. Harga tidak valid.** Harga yang bukan bilangan bulat (misalnya `abc`) menghasilkan pesan "Harga harus berupa bilangan bulat (contoh: 15000)." Harga `0` atau negatif menghasilkan pesan "Harga harus lebih dari nol." Pada kedua kasus produk tidak disimpan.

![Harga tidak valid ditolak](GANTI-NAMA-FILE-5.png)

## Teknologi

Java 17, JavaFX 21, FXML, CSS, Maven.
