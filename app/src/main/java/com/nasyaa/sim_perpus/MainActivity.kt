package com.nasyaa.sim_perpus

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var bottomNav: BottomNavigationView
    private lateinit var tvUsername: TextView
    private lateinit var icProfile: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 1. Setup Bottom Navigation
        bottomNav = findViewById(R.id.bottomNav)

        if (savedInstanceState == null) {
            loadFragment(KoleksiFragment())
        }

        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_koleksi -> loadFragment(KoleksiFragment())
                R.id.nav_denda -> loadFragment(DendaFragment())
                R.id.nav_notifikasi -> {
                    DataNotifikasi.resetNotifBaru()
                    bottomNav.removeBadge(R.id.nav_notifikasi)
                    loadFragment(NotifikasiFragment())
                }
                R.id.nav_surat -> loadFragment(SuratFragment())
            }
            true
        }

        // 2. Setup Header (Search & Profil)
        val btnSearch = findViewById<ImageView>(R.id.btn_search)
        tvUsername = findViewById(R.id.tv_username)
        icProfile = findViewById(R.id.ic_user_profile)

        // Load Data User dari Session
        tampilkanDataUser()

        // Logika Klik Tombol Search
        btnSearch.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
        }

        // 3. Logika Klik Profil (Munculkan Dialog Detail Profil, Edit, & Logout)
        val bukaDialogProfil = {
            tampilkanDialogProfil()
        }

        icProfile.setOnClickListener { bukaDialogProfil() }
        tvUsername.setOnClickListener { bukaDialogProfil() }
    }

    private fun getProfileData(): ProfileData {
        val prefs = getSharedPreferences("UserSession", MODE_PRIVATE)
        val emailSaved = prefs.getString("USER_EMAIL", "andi@student.com") ?: "andi@student.com"
        val namaCustom = prefs.getString("USER_NAME", null)
        val nimSaved = prefs.getString("USER_NIM", "SI21240013") ?: "SI21240013"
        val prodiSaved = prefs.getString("USER_PRODI", "Sistem Informasi") ?: "Sistem Informasi"

        val nama = namaCustom ?: when {
            emailSaved.startsWith("andi") -> "Andi"
            emailSaved.startsWith("sulis") -> "Sulistiani"
            emailSaved.startsWith("liza") -> "Lizainatul"
            else -> "Anggota"
        }

        return ProfileData(nama, emailSaved, nimSaved, prodiSaved)
    }

    private fun tampilkanDataUser() {
        val data = getProfileData()
        tvUsername.text = data.nama
        icProfile.text = data.nama.take(1).uppercase()
    }

    private fun tampilkanDialogProfil() {
        val prefs = getSharedPreferences("UserSession", MODE_PRIVATE)
        val data = getProfileData()

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Detail Profil Anggota")
        builder.setMessage(
            "Nama  : ${data.nama}\n" +
                    "Email : ${data.email}\n" +
                    "NIM   : ${data.nim}\n" +
                    "Prodi : ${data.prodi}\n" +
                    "Status: Mahasiswa STMIK Lombok (Aktif)",
        )

        builder.setPositiveButton("Edit Profil") { dialog, _ ->
            dialog.dismiss()
            tampilkanDialogEditProfil(data)
        }

        builder.setNeutralButton("Logout") { dialog, _ ->
            dialog.dismiss()
            prefs.edit().apply {
                clear()
                apply()
            }
            Toast.makeText(this, "Anda telah Logout", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        builder.setNegativeButton("Tutup") { dialog, _ ->
            dialog.dismiss()
        }

        builder.show()
    }

    private fun tampilkanDialogEditProfil(dataSekarang: ProfileData) {
        val context = this
        val layout = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(40, 20, 40, 20)
        }

        val etNama = EditText(context).apply {
            hint = "Nama Lengkap"
            setText(dataSekarang.nama)
        }
        val etEmail = EditText(context).apply {
            hint = "Email"
            setText(dataSekarang.email)
        }
        val etNim = EditText(context).apply {
            hint = "NIM"
            setText(dataSekarang.nim)
        }
        val etProdi = EditText(context).apply {
            hint = "Program Studi"
            setText(dataSekarang.prodi)
        }

        layout.addView(etNama)
        layout.addView(etEmail)
        layout.addView(etNim)
        layout.addView(etProdi)

        val builder = AlertDialog.Builder(context)
        builder.setTitle("Edit Data Profil Anggota")
        builder.setView(layout)

        builder.setPositiveButton("Simpan") { dialog, _ ->
            val namaBaru = etNama.text.toString().trim()
            val emailBaru = etEmail.text.toString().trim()
            val nimBaru = etNim.text.toString().trim()
            val prodiBaru = etProdi.text.toString().trim()

            if (namaBaru.isNotEmpty()) {
                val prefs = getSharedPreferences("UserSession", MODE_PRIVATE)
                prefs.edit().apply {
                    putString("USER_NAME", namaBaru)
                    putString("USER_EMAIL", emailBaru)
                    putString("USER_NIM", nimBaru)
                    putString("USER_PRODI", prodiBaru)
                    apply()
                }
                tampilkanDataUser()
                Toast.makeText(this, "Profil berhasil diperbarui!", Toast.LENGTH_SHORT).show()
            }
            dialog.dismiss()
        }

        builder.setNegativeButton("Batal") { dialog, _ -> dialog.dismiss() }
        builder.show()
    }

    private fun updateNotifBadge() {
        val count = DataNotifikasi.jumlahNotifBaru
        if (count > 0) {
            val badge = bottomNav.getOrCreateBadge(R.id.nav_notifikasi)
            badge.number = count
            badge.isVisible = true
        } else {
            bottomNav.removeBadge(R.id.nav_notifikasi)
        }
    }

    override fun onResume() {
        super.onResume()
        updateNotifBadge()
        tampilkanDataUser()
    }

    // Fungsi untuk mengganti halaman
    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }

    private data class ProfileData(
        val nama: String,
        val email: String,
        val nim: String,
        val prodi: String,
    )
}