package com.nasyaa.sim_perpus
object DataSurat {
    val daftarSurat = mutableListOf<String>()
    fun tambahSurat(nomorSurat: String, tanggal: String) {
        daftarSurat.add(0, "$nomorSurat | $tanggal")
    }
}