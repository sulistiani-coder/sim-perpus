package com.nasyaa.sim_perpus

data class Buku(
    val judul: String,
    val penulis: String,
    val gambarResource: Int,
    val tipe: String, // Ebook / Buku Fisik
    val deskripsi: String = "Buku ini berisi panduan dan referensi mengenai bab-bab penting dalam bidang ilmu komputer dan perpustakaan STMIK Lombok."
)