package com.nasyaa.sim_perpus

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class NotifikasiFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_notifikasi, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.rv_notifikasi)
        recyclerView.layoutManager = LinearLayoutManager(context)

        // Ambil data dari "keranjang" DataNotifikasi
        val listPesan = DataNotifikasi.daftarPesan

        if (listPesan.isEmpty()) {
            // Jika belum ada notifikasi, tampilkan pesan default
            val dataDefault = listOf("Belum ada notifikasi aktivitas terbaru.")
            recyclerView.adapter = NotifAdapter(dataDefault)
        } else {
            // Tampilkan notifikasi asli
            recyclerView.adapter = NotifAdapter(listPesan)
        }
    }

    // Adapter Internal untuk Notifikasi
    inner class NotifAdapter(private val listPesan: List<String>) : RecyclerView.Adapter<NotifAdapter.NotifViewHolder>() {

        inner class NotifViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val tvPesan: TextView = itemView.findViewById(R.id.tv_pesan_notif)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotifViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_notifikasi, parent, false)
            return NotifViewHolder(view)
        }

        override fun onBindViewHolder(holder: NotifViewHolder, position: Int) {
            holder.tvPesan.text = listPesan[position]
        }

        override fun getItemCount(): Int = listPesan.size
    }
}