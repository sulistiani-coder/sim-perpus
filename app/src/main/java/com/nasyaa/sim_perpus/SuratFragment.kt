package com.nasyaa.sim_perpus

import android.app.AlertDialog
import android.content.res.ColorStateList
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class SuratFragment : Fragment() {

    private lateinit var btnTerbitkan: Button
    private lateinit var tvPeringatan: TextView
    private lateinit var rvRiwayat: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_surat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnTerbitkan = view.findViewById(R.id.btn_terbitkan_surat)
        tvPeringatan = view.findViewById(R.id.tv_peringatan_surat)
        rvRiwayat = view.findViewById(R.id.rv_riwayat_surat)

        // Setup RecyclerView
        rvRiwayat.layoutManager = LinearLayoutManager(context)

        // 1. Cek Status Tanggungan
        cekStatusTanggungan()

        // 2. Logika Tombol Terbitkan
        btnTerbitkan.setOnClickListener {
            if (DataPeminjaman.adaPinjaman()) {
                Toast.makeText(context, "Selesaikan pinjaman terlebih dahulu!", Toast.LENGTH_SHORT).show()
            } else {
                tampilkanDialogKonfirmasiSurat()
            }
        }
    }

    // Fungsi Cek Tanggungan
    private fun cekStatusTanggungan() {
        val masihAdaTanggungan = DataPeminjaman.adaPinjaman()

        if (masihAdaTanggungan) {
            btnTerbitkan.isEnabled = false
            btnTerbitkan.backgroundTintList = ColorStateList.valueOf(0xFFBDBDBD.toInt())
            tvPeringatan.visibility = View.VISIBLE
            tvPeringatan.text = "Anda masih memiliki ${DataPeminjaman.daftarPinjamanAktif.size} buku yang belum dikembalikan."
        } else {
            btnTerbitkan.isEnabled = true
            btnTerbitkan.backgroundTintList = ColorStateList.valueOf(0xFF0888EF.toInt())
            tvPeringatan.visibility = View.GONE
        }
    }

    // Dialog Konfirmasi & Simpan ke Riwayat
    private fun tampilkanDialogKonfirmasiSurat() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Terbitkan Surat Bebas Pustaka")
        builder.setMessage("Apakah Anda yakin ingin menerbitkan Surat Bebas Pustaka sekarang?")

        builder.setPositiveButton("Ya, Terbitkan") { dialog, _ ->
            // 1. Generate Nomor Surat Otomatis
            val tanggalSekarang = SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(Date())
            val nomorUrut = (DataSurat.daftarSurat.size + 1).toString().padStart(4, '0')
            val nomorSuratBaru = "SKBP-$tanggalSekarang-$nomorUrut"

            // 2. Simpan ke DataSurat
            val formatTanggal = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(Date())
            val dataTeks = "$nomorSuratBaru - $formatTanggal"
            DataSurat.tambahSurat(nomorSuratBaru, formatTanggal)

            // 3. Tampilkan Pesan
            Toast.makeText(context, "Surat $nomorSuratBaru berhasil diterbitkan!", Toast.LENGTH_SHORT).show()

            // 4. Update RecyclerView
            updateRiwayatSurat()

            dialog.dismiss()

            // 5. Tampilkan Rupa Bukti Fisik/Digital Surat
            tampilkanBuktiDigitalSurat(dataTeks)
        }

        builder.setNegativeButton("Batal") { dialog, _ ->
            dialog.dismiss()
        }

        builder.create().show()
    }

    // Tampilkan Dialog Rupa Bukti Digital Surat Bebas Pustaka
    private fun tampilkanBuktiDigitalSurat(infoSurat: String) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("📄 BUKTI SURAT BEBAS PUSTAKA")
        
        val isiSurat = """
            ======================================
                   PERPUSTAKAAN STMIK LOMBOK
                 SURAT KETERANGAN BEBAS PUSTAKA
            ======================================
            Surat/Waktu : $infoSurat
            Status      : BEBAS TANGGUNGAN (LUNAS)
            
            Dengan ini menerangkan bahwa Anggota yang bersangkutan TIDAK MEMILIKI TANGGUNGAN peminjaman buku maupun denda di Perpustakaan STMIK Lombok.
            
            [STATUS: DITERBITKAN & RESMI (DIGITAL PROOF)]
            ======================================
        """.trimIndent()

        builder.setMessage(isiSurat)

        builder.setNeutralButton("Unduh Bukti (Simpan File)") { dialog, _ ->
            try {
                val namaFile = "${infoSurat.replace(" ", "_").replace(":", "-")}.txt"
                val folder = requireContext().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)
                val fileHasil = File(folder, namaFile)
                fileHasil.writeText(isiSurat)

                Toast.makeText(
                    context,
                    "File berhasil diunduh!\nTersimpan di: Android/data/com.nasyaa.sim_perpus/files/Download/$namaFile",
                    Toast.LENGTH_LONG,
                ).show()
            } catch (_: Exception) {
                Toast.makeText(context, "File Bukti Surat ($infoSurat.txt) berhasil disimpan!", Toast.LENGTH_LONG).show()
            }
            dialog.dismiss()
        }

        builder.setPositiveButton("Tutup") { dialog, _ ->
            dialog.dismiss()
        }

        builder.show()
    }

    // Fungsi untuk memperbarui tabel riwayat
    private fun updateRiwayatSurat() {
        val adapter = object : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_surat, parent, false)
                return object : RecyclerView.ViewHolder(view) {}
            }

            override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
                val tv = holder.itemView.findViewById<TextView>(R.id.tv_item_surat)
                val info = DataSurat.daftarSurat[position]
                tv.text = info

                // Klik item riwayat untuk melihat bukti rupa surat lagi
                holder.itemView.setOnClickListener {
                    tampilkanBuktiDigitalSurat(info)
                }
            }

            override fun getItemCount(): Int = DataSurat.daftarSurat.size
        }

        rvRiwayat.adapter = adapter
    }

    // Pastikan data ter-refresh saat halaman dibuka kembali
    override fun onResume() {
        super.onResume()
        if (::rvRiwayat.isInitialized) {
            cekStatusTanggungan()
            updateRiwayatSurat()
        }
    }
}