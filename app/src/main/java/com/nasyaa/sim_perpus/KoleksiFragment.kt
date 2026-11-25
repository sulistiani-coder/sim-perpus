package com.nasyaa.sim_perpus

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class KoleksiFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_koleksi, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1. DATA MODEL
        val daftarBuku = listOf(
            Buku(
                judul = "Dasar-Dasar Basis Data",
                penulis = "Tere Liye",
                gambarResource = R.drawable.basis_data,
                tipe = "Buku Fisik",
                deskripsi = "Buku ini membahas konsep dasar sistem basis data, perancangan ERD, normalisasi tabel, hingga query SQL dasar & lanjut untuk mahasiswa STMIK Lombok."
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

        // 2. SETUP RECYCLERVIEW
        val recyclerView = view.findViewById<RecyclerView>(R.id.rv_buku)
        recyclerView.layoutManager = GridLayoutManager(context, 2)

        // 3. ADAPTER & HUBUNGKAN KE DETAIL BUKU
        val adapter = BukuAdapter(daftarBuku) { buku ->
            val intent = Intent(context, DetailBukuActivity::class.java)
            intent.putExtra("JUDUL", buku.judul)
            intent.putExtra("PENULIS", buku.penulis)
            intent.putExtra("TIPE", buku.tipe)
            intent.putExtra("GAMBAR", buku.gambarResource)
            intent.putExtra("DESKRIPSI", buku.deskripsi)
            startActivity(intent)
        }

        recyclerView.adapter = adapter
    }
}