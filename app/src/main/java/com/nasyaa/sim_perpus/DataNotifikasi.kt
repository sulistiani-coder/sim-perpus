package com.nasyaa.sim_perpus

// Ini adalah "keranjang" untuk menyimpan notifikasi sementara
object DataNotifikasi {
    // List untuk menyimpan pesan-pesan notifikasi
    val daftarPesan = mutableListOf<String>()
    var jumlahNotifBaru: Int = 0

    // Fungsi untuk menambah notifikasi baru
    fun tambahPesan(pesan: String) {
        daftarPesan.add(0, pesan) // Tambahkan di paling atas (terbaru)
        jumlahNotifBaru++
    }

    // Reset counter setelah dibaca
    fun resetNotifBaru() {
        jumlahNotifBaru = 0
    }
}