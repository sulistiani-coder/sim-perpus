# Sim-Perpus (Sistem Informasi Perpustakaan Digital)

Aplikasi Android Perpustakaan Digital (*Capstone Project*) yang dikembangkan untuk mata kuliah **Fundamental Android Programming**.

## 📱 Fitur Utama & Kepatuhan Capstone (Sub-CPMK 9 - 15)

1. **Slicing UI/UX & Navigation Design (Sub-CPMK 9)**:
   - Desain antarmuka modern dengan Material Design 3.
   - Navigasi bawah (`BottomNavigationView`) dengan 4 menu utama: Koleksi, Denda, Notifikasi, dan Surat.
   - Menu pop-up profil dan pencarian interaktif.

2. **Data Model & Business Logic (Sub-CPMK 10)**:
   - Implementasi data classes (`Buku`, `DataNotifikasi`, `DataPeminjaman`, `DataSurat`).
   - Logika validasi login dan *event handling* interaktif pada setiap komponen UI.

3. **Dynamic List & RecyclerView (Sub-CPMK 11)**:
   - Menampilkan katalog buku secara dinamis menggunakan `RecyclerView` dengan `GridLayoutManager` dan `BukuAdapter`.

4. **Persistence Data Storage (Sub-CPMK 12)**:
   - Implementasi `SharedPreferences` untuk pengelolaan **Session Login** pengguna (Auto-login & Logout).

5. **Full System Integration & Refactoring (Sub-CPMK 13)**:
   - Menggabungkan seluruh layar, fitur navigasi, list, dan storage lokal menjadi satu aplikasi utuh yang stabil.

6. **Testing & Bug Fixing (Sub-CPMK 14)**:
   - Pengujian fungsional manual, pembenahan bug, dan pembersihan *code smells* (Zero warnings & errors).

7. **Packaging & Documentation (Sub-CPMK 15)**:
   - Build APK sukses dan dokumentasi repository (README).

---

## 👥 Akun Uji Coba Login
- **Email:** `andi@student.com` | **Password:** `password`
- **Email:** `sulis@student.com` | **Password:** `sulis`
- **Email:** `liza@student.com` | **Password:** `liza`

---

## 🛠️ Teknologi yang Digunakan
- **Bahasa:** Kotlin
- **UI Framework:** Android Views, XML Layouts, Material Components (M3), Fragments, RecyclerView, BottomNavigationView
- **Storage:** SharedPreferences (Session Management)
- **IDE:** Android Studio
