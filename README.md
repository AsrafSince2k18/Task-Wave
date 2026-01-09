# WorkManager Background Network App

This Android application demonstrates how to perform **background network requests** using **WorkManager** and notify users when tasks complete or require attention.

The app ensures reliable background execution, even when the app is closed or the device restarts, following modern Android background execution guidelines.

---

## ✨ Features
- Background network requests using WorkManager
- Reliable task execution (guaranteed background work)
- Notification support for task status
- Android 13+ notification permission handling
- Permission rationale and redirection to app settings
- Clean and simple architecture

---

## 🛠 Tech Stack
- Kotlin
- Hilt
- Retrofit
- Room database
- Jetpack WorkManager
- Jetpack Compose
- Notifications
- Android Jetpack libraries

---

## 🔐 Permission Handling
On Android 13 (API 33) and above, the app requests **POST_NOTIFICATIONS** permission.

- If permission is denied, a rationale dialog is shown
- If permission is permanently denied, users are redirected to system settings
- The app continues to function without forcing permission

---

## 🔄 How It Works
1. User triggers a background task
2. WorkManager schedules the network request
3. Task executes in the background
4. Notification is shown when work completes

---

## 📱 Screenshots
_Add screenshots here_

