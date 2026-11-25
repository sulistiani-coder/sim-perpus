package com.nasyaa.sim_perpus

import android.app.AlertDialog
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class DetailBukuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_buku)

        // 1. Ambil data yang dikirim dari KoleksiFragment
        val judul = intent.getStringExtra("JUDUL")
        val penulis = intent.getStringExtra("PENULIS")
        val tipe = intent.getStringExtra("TIPE")
        val gambar = intent.getIntExtra("GAMBAR", 0)
        val deskripsi = intent.getStringExtra("DESKRIPSI")
            ?: "Buku ini merupakan koleksi unggulan di Perpustakaan STMIK Lombok yang memberikan wawasan serta pemahaman praktis bagi mahasiswa."

        // 2. Tampilkan data ke layout
        findViewById<TextView>(R.id.tv_detail_judul).text = judul
        findViewById<TextView>(R.id.tv_detail_penulis).text = "Penulis: $penulis"
        findViewById<TextView>(R.id.tv_detail_tipe).text = tipe
        findViewById<TextView>(R.id.tv_detail_deskripsi).text = deskripsi
        findViewById<ImageView>(R.id.img_detail_cover).setImageResource(gambar)

        // 3. LOGIKA PEMBEDA E-BOOK vs BUKU FISIK
        val btnAksi = findViewById<Button>(R.id.btn_aksi_buku)

        if (tipe == "Ebook") {
            // Jika Ebook, ubah tombol menjadi Baca / Download PDF
            btnAksi.text = "BACA / DOWNLOAD PDF"
            btnAksi.setBackgroundColor(0xFF2196F3.toInt()) // Warna Biru
            btnAksi.setOnClickListener {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Akses E-Book")
                builder.setMessage("Pilih opsi untuk mengakses e-book \"$judul\":")
                builder.setPositiveButton("Baca Online") { dialog, _ ->
                    Toast.makeText(this, "Membuka Reader E-Book...", Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                }
                builder.setNeutralButton("Download PDF") { dialog, _ ->
                    Toast.makeText(this, "Mengunduh file PDF ke penyimpanan HP...", Toast.LENGTH_LONG).show()
                    dialog.dismiss()
                }
                builder.setNegativeButton("Batal") { dialog, _ -> dialog.dismiss() }
                builder.show()
            }
        } else {
            // Jika Buku Fisik, jalankan mekanisme Pinjam
            btnAksi.text = "PINJAM BUKU"
            btnAksi.setBackgroundColor(0xFF2196F3.toInt()) // Warna Biru
            btnAksi.setOnClickListener {
                tampilkanDialogKonfirmasi()
            }
        }
    }

    // Fungsi Dialog Konfirmasi Peminjaman
    private fun tampilkanDialogKonfirmasi() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Konfirmasi Peminjaman")
        builder.setMessage("Apakah Anda yakin ingin meminjam buku ini?")

        builder.setPositiveButton("Ya, Pinjam!") { dialog, _ ->
            val judulBuku = intent.getStringExtra("JUDUL") ?: "Buku"

            // 1. Tambahkan ke daftar pinjaman otomatis
            DataPeminjaman.tambahPinjaman(judulBuku)

            // 2. Kirim ke Notifikasi
            DataNotifikasi.tambahPesan("Peminjaman buku \"$judulBuku\" berhasil diajukan dan menunggu persetujuan admin.")

            Toast.makeText(this, "Pengajuan peminjaman berhasil!", Toast.LENGTH_LONG).show()
            dialog.dismiss()
            finish()
        }

        builder.setNegativeButton("Batal") { dialog, _ ->
            dialog.dismiss()
        }

        val dialog = builder.create()
        dialog.show()
    }
}