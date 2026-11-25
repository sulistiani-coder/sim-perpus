package com.nasyaa.sim_perpus

object DataPeminjaman {
    // List untuk menyimpan buku yang sedang dipinjam
    val daftarPinjamanAktif = mutableListOf<String>()

    // Status denda belum lunas
    var adaDendaBelumLunas: Boolean = false

    // Getter untuk mengecek apakah masih ada pinjaman aktif
    val adaPinjamanAktif: Boolean
        get() = daftarPinjamanAktif.isNotEmpty()

    // Fungsi untuk mengecek apakah masih ada pinjaman
    fun adaPinjaman(): Boolean {
        return adaPinjamanAktif
    }

    // Fungsi untuk menambah pinjaman baru
    fun tambahPinjaman(judulBuku: String) {
        daftarPinjamanAktif.add(judulBuku)
    }

    // Fungsi untuk mengembalikan buku (menghapus dari list)
    fun kembalikanBuku(judulBuku: String) {
        daftarPinjamanAktif.remove(judulBuku)
    }
}