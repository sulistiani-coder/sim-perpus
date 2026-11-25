package com.nasyaa.sim_perpus

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BukuAdapter(
    private val listBuku: List<Buku>,
    private val onItemClick: (Buku) -> Unit
) : RecyclerView.Adapter<BukuAdapter.BukuViewHolder>() {

    class BukuViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgCover: ImageView = itemView.findViewById(R.id.img_cover)
        val tvJudul: TextView = itemView.findViewById(R.id.tv_judul)
        val tvPenulis: TextView = itemView.findViewById(R.id.tv_penulis)
        val tvTipe: TextView = itemView.findViewById(R.id.tv_tipe)
        val tvLihatDetail: TextView = itemView.findViewById(R.id.tv_lihat_detail)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BukuViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_buku, parent, false)
        return BukuViewHolder(view)
    }

    override fun onBindViewHolder(holder: BukuViewHolder, position: Int) {
        val buku = listBuku[position]
        holder.tvJudul.text = buku.judul
        holder.tvPenulis.text = buku.penulis
        holder.tvTipe.text = buku.tipe // Tampilkan tipe
        holder.imgCover.setImageResource(buku.gambarResource)

        // Warnai label tipe (Opsional: Hijau untuk Fisik, Biru untuk Ebook)
        if (buku.tipe == "Buku Fisik") {
            holder.tvTipe.setBackgroundColor(0xFFC8E6C9.toInt()) // Hijau Muda
        } else {
            holder.tvTipe.setBackgroundColor(0xFFBBDEFB.toInt()) // Biru Muda
        }

        holder.itemView.setOnClickListener { onItemClick(buku) }
        holder.tvLihatDetail.setOnClickListener { onItemClick(buku) }
    }

    override fun getItemCount(): Int = listBuku.size
}