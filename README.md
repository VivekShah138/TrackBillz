
# BaseAndroidTemplate

This repository serves as a **base Android project template** with pre-configured libraries and structure including:

- Jetpack Compose
- MVVM Custom File Template
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
Open your terminal (Command Prompt, Git Bash, etc.) and go to the directory where you want your new project folder to be created.

```bash
git clone https://github.com/VivekShah138/BaseAndroidTemplate.git MyNewApp
```
```bash
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

## 🔄 Optional: Rename the App Package

If you want to change the default package name (`com.example.baseandroidtemplate`) to your own (e.g., `com.vivek.myfinanceapp`), follow these steps carefully:

### 🔹 Step 1: View Full Java/Kotlin Folder Structure

1. In Android Studio, go to the **top-left dropdown** and switch from **"Android"** to **"Project"** view.
2. Navigate to:
   ```
   app > src > main > java > com > example > baseandroidtemplate
   ```

### 🔹 Step 2: Refactor the Package Name

1. Right-click on each folder in the package path (e.g., `example`, then `baseandroidtemplate`).
2. Select:
   ```
   Refactor > Rename
   ```
3. Choose **"Rename Package"** when prompted.
4. Change it to your desired package name:
   ```
   com.vivek.myfinanceapp
   ```
5. Click **Refactor** and let Android Studio update all references.

### 🔹 Step 3: Update `AndroidManifest.xml`

1. Open:
   ```
   app/src/main/AndroidManifest.xml
   ```
2. Update the `package` attribute at the top:
   ```xml
   <manifest package="com.vivek.myfinanceapp">
   ```

### 🔹 Step 4: Update `build.gradle.kts` or `build.gradle`

1. Open:
   ```
   app/build.gradle.kts
   ```
2. Update the `applicationId` field to match your new package name:
   ```kotlin
   applicationId = "com.vivek.myfinanceapp"
   ```

### 🔹 Step 5: Update `settings.gradle.kts` or `settings.gradle`

1. Open the root-level `settings.gradle.kts` (or `settings.gradle` if using Groovy).
2. Update the `rootProject.name` to reflect your new project name:
   ```kotlin
   rootProject.name = "MyFinanceApp"
   ```

### 🔹 Step 6: Sync & Rebuild Project

1. Click **File > Sync Project with Gradle Files**
2. Then run:
   - **Build > Clean Project**
   - **Build > Rebuild Project**

---

> ✅ Done! Your app now uses the new package name across all relevant files.

---

## 📦 Preconfigured Dependencies

| Feature           | Library Used         |
|------------------|----------------------|
| Dependency Injection | Dagger Hilt       |
| Database         | Room                 |
| Networking       | Retrofit + Gson      |
| Async & State    | Kotlin Coroutines    |
| UI Framework     | Jetpack Compose      |
| Testing          | JUnit, Mockito, etc. |

---

## 📄 Custom Android Studio file templates to generate boilerplate code for a new screen (ViewModel, State class, Event class)

### 🔹 Custom File Templates
Included in `custom_templates/`:
- A custom template for generating:
  - `ViewModel.kt`
  - `State.kt`
  - `Event.kt`

This eliminates the need to manually write boilerplate code every time you create a new screen.

---

## 🔧 How to Install Custom Templates in Android Studio

### Step 1: Locate the Templates Directory

#### 🪟 Windows:
```
C:\Users\<YourUsername>\AppData\Roaming\Google\AndroidStudio<version>\templates
```

#### 🍎 macOS:
```
~/Library/Application Support/Google/AndroidStudio<version>/templates
```

#### 🐧 Linux:
```
~/.config/Google/AndroidStudio<version>/templates
```

### Step 2: Copy Custom Templates

1. Download this project.
2. Copy everything from the `custom_templates/` folder.
3. Paste it into the templates directory from Step 1.
4. Restart Android Studio.

### ✅ Done!

You can now go to:
```
File > New > Other > YourCustomTemplate
```
...and generate your MVVM boilerplate in seconds!


---

## 🤝 Contributing to the Template

If you want to improve or update this base template, create a branch and make a pull request on the [original repo](https://github.com/VivekShah138/BaseAndroidTemplate).

---

## 📄 License

This project is open-source. Use it freely in your personal or professional projects.
