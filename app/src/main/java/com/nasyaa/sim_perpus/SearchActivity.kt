package com.nasyaa.sim_perpus

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SearchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        // 1. Tombol Kembali
        findViewById<ImageView>(R.id.btn_back).setOnClickListener {
            finish()
        }

        // 2. Daftar Buku Asli (Harus sama dengan di KoleksiFragment)
        val daftarBukuAsli = listOf(
            Buku(
                judul = "Dasar-Dasar Basis Data",
                penulis = "Tere Liye",
                gambarResource = R.drawable.basis_data,
                tipe = "Buku Fisik",
                deskripsi = "Buku ini membahas konsep dasar sistem basis data, perancangan ERD, normalisasi tabel, hingga query SQL dasar & lanjut untuk mahasiswa STMIK Lombok.",
            ),
            Buku(
                judul = "Langsung Mahir Python",
                penulis = "Hendri",
                gambarResource = R.drawable.pyton,
                tipe = "Ebook",
                deskripsi = "Ebook praktis sintaks dasar Python, pemrograman berorientasi objek (OOP), pengolahan data, hingga pembuatan modul sederhana yang mudah dipahami."
            ),
            Buku(
                judul = "Pemrograman Web HTML",
                penulis = "Heru",
                gambarResource = R.drawable.web,
                tipe = "Buku Fisik",
                deskripsi = "Panduan lengkap membangun struktur website modern dari dasar menggunakan HTML5, CSS3, dan dasar JavaScript untuk antarmuka web interaktif."
            )
        )

        // 3. Setup RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.rv_hasil_search)
        recyclerView.layoutManager = GridLayoutManager(this, 2)

        // Fungsi untuk pindah ke Detail
        val bukaDetail: (Buku) -> Unit = { buku ->
            val intent = Intent(this, DetailBukuActivity::class.java)
            intent.putExtra("JUDUL", buku.judul)
            intent.putExtra("PENULIS", buku.penulis)
            intent.putExtra("TIPE", buku.tipe)
            intent.putExtra("GAMBAR", buku.gambarResource)
            intent.putExtra("DESKRIPSI", buku.deskripsi)
            startActivity(intent)
        }

        // Tampilkan semua buku saat halaman pertama kali dibuka
        var adapter = BukuAdapter(daftarBukuAsli, bukaDetail)
        recyclerView.adapter = adapter

        // 4. Logika Pencarian Real-time
        val etSearch = findViewById<EditText>(R.id.et_search)
        etSearch.requestFocus() // Langsung fokus ke keyboard

        etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val keyword = s.toString().lowercase()

                // Filter berdasarkan judul atau penulis
                val dataTerfilter = daftarBukuAsli.filter { buku ->
                    buku.judul.lowercase().contains(keyword) ||
                            buku.penulis.lowercase().contains(keyword)
                }

                adapter = BukuAdapter(dataTerfilter, bukaDetail)
                recyclerView.adapter = adapter
            }
        })
    }
}