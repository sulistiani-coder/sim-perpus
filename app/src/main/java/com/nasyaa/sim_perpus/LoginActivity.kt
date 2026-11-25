package com.nasyaa.sim_perpus

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Cek apakah sudah ada session login sebelumnya (SharedPreferences)
        val sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE)
        val isLoggedIn = sharedPreferences.getBoolean("IS_LOGGED_IN", false)
        if (isLoggedIn) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
            return
        }

        setContentView(R.layout.activity_login)

        val etEmail = findViewById<EditText>(R.id.et_email)
        val etPassword = findViewById<EditText>(R.id.et_password)
        val btnLogin = findViewById<Button>(R.id.btn_login)

        btnLogin.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()

            val validUsers = mapOf(
                "andi@student.com" to "password",
                "sulis@student.com" to "sulis",
                "liza@student.com" to "liza",
            )

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Email dan Password wajib diisi!", Toast.LENGTH_SHORT).show()
            } else if (validUsers[email] == password) {
                // Simpan session ke SharedPreferences
                sharedPreferences.edit().apply {
                    putBoolean("IS_LOGGED_IN", true)
                    putString("USER_EMAIL", email)
                    apply()
                }

                // JIKA LOGIN BERHASIL
                Toast.makeText(this, "Login Berhasil!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish() // Menutup halaman login
            } else {
                // JIKA LOGIN GAGAL
                Toast.makeText(this, "Email atau Password salah!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}