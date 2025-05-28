
# BaseAndroidTemplate

This repository serves as a **base Android project template** with pre-configured libraries and structure including:

- Jetpack Compose
- Dagger Hilt
- Room Database
- Retrofit + Coroutines
- Unit testing dependencies

> Use this template to quickly start new Android projects without repeating the setup each time.

---

## 🚀 How to Use This Template to Start a New Project

Follow these steps to create a **new Android project based on this template** and link it to its own GitHub repository.

---

### ✅ Step 1: Clone the Template Locally

```bash
git clone https://github.com/VivekShah138/BaseAndroidTemplate.git MyNewApp
cd MyNewApp
```

> Replace `MyNewApp` with your new project name.

---

### ✅ Step 2: Remove Link to This Original Template

```bash
git remote remove origin
```

This ensures that you don’t push code back to the original template repo.

---

### ✅ Step 3: Create a New GitHub Repo

Go to [GitHub](https://github.com/new) and create a **new empty repository** (e.g. `MyNewApp`).

Do **not** initialize with a README or license.

---

### ✅ Step 4: Connect to Your New Repo

```bash
git remote add origin https://github.com/your-username/MyNewApp.git
```

Make sure to replace `your-username` and `MyNewApp` with your actual GitHub username and repo name.

---

### ✅ Step 5: Push the Code

```bash
git add .
git commit -m "Initial commit from base template"
git branch -M main
git push -u origin main
```

---

## 🛠️ Optional: Rename the App Package

If you want to change the app package (e.g. from `com.example.baseandroidtemplate` to `com.yourapp.name`):

1. Refactor the package in Android Studio (`Right click > Refactor > Rename`)
2. Update the `AndroidManifest.xml`, `build.gradle`, and `google-services.json` if needed.

---

## 📦 Preconfigured Dependencies

| Feature           | Library Used         |
|------------------|----------------------|
| Dependency Injection | Dagger Hilt       |
| Database         | Room                 |
| Networking       | Retrofit + Gson      |
| Async & State    | Kotlin Coroutines    |
| Architecture     | MVVM + Clean Architecture |
| UI Framework     | Jetpack Compose      |
| Testing          | JUnit, Mockito, etc. |

---

## 🤝 Contributing to the Template

If you want to improve or update this base template, create a branch and make a pull request on the [original repo](https://github.com/VivekShah138/BaseAndroidTemplate).

---

## 📄 License

This project is open-source. Use it freely in your personal or professional projects.
