# 🧹 Cleaner_File – Android Kotlin App

**Cleaner_File** is a lightweight and efficient Android application built using **Kotlin** that helps users clean junk files, cache, residual files, and optimize their device's storage. It provides a simple, fast, and user-friendly interface for managing files and boosting device performance.

---

## 🚀 Key Features

- 🔍 **Scan Storage**: Quickly scan internal and external storage for unnecessary files.
- 🗑️ **Delete Junk Files**: Remove cache, residual, empty folders, and log files safely.
- 📁 **File Manager Integration**: Browse, open, or delete large files or duplicate files.
- 📊 **Storage Summary**: View total and used space with detailed categorization.
- 🚀 **Boost Performance**: Free up RAM and reduce lag by cleaning temporary files.

---

## 🧱 Project Structure

Cleaner_File/
├── app/
│ ├── src/
│ │ ├── main/
│ │ │ ├── java/com/example/cleaner_file/
│ │ │ │ ├── MainActivity.kt
│ │ │ │ ├── FileScanner.kt
│ │ │ │ ├── CleanerUtils.kt
│ │ │ │ └── adapter/ # (Optional) For file list UI
│ │ │ ├── res/
│ │ │ │ ├── layout/
│ │ │ │ ├── drawable/
│ │ │ │ └── values/
├── build.gradle
├── AndroidManifest.xml

yaml
Sao chép
Chỉnh sửa

---

## 🛠️ Technologies Used

- **Kotlin** – Primary development language
- **ViewModel + LiveData** – For managing UI-related data
- **RecyclerView** – To display lists of files
- **File API** – To access and manage file system
- **Material Design Components** – For modern UI
- *(Optional)* **Hilt / Dagger** – For dependency injection

---

## 🔐 Permissions Required

Make sure to request runtime permissions:

```xml
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
For Android 11+ (API 30+), also include:

xml
Sao chép
Chỉnh sửa
<uses-permission android:name="android.permission.MANAGE_EXTERNAL_STORAGE" />
Handle permissions properly using ActivityResultLauncher or AndroidX Permission APIs.

📦 Future Improvements
🔁 Duplicate image/file detection

⏰ Schedule auto-clean tasks

🌙 Dark mode support

🔋 Battery optimization monitor

📸 Screenshots
(Add screenshots here of your app interface if available)

🧑‍💻 Author
Tran Cuong
