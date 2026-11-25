package com.nasyaa.sim_perpus
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment

class DendaFragment : Fragment() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnBayar = view.findViewById<Button>(R.id.btn_bayar_denda)
        val btnSimulasiKembali = view.findViewById<Button>(R.id.btn_simulasi_kembali)

        btnBayar.setOnClickListener {
            Toast.makeText(context, "Mengajukan Pembayaran...", Toast.LENGTH_SHORT).show()
        }

        // LOGIKA SIMULASI PENGEMBALIAN BUKU
        btnSimulasiKembali.setOnClickListener {
            if (DataPeminjaman.daftarPinjamanAktif.isNotEmpty()) {
                // Ambil buku pertama di list, lalu hapus (kembalikan)
                val bukuDikembalikan = DataPeminjaman.daftarPinjamanAktif.first()
                DataPeminjaman.kembalikanBuku(bukuDikembalikan)

                Toast.makeText(
                    context,
                    "Buku \"$bukuDikembalikan\" berhasil dikembalikan!",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(context, "Tidak ada buku yang sedang dipinjam.", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}