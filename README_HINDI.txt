THE HOOT Android App - Android Studio project starter

Android 15 compatible target SDK 35. This is a WebView wrapper app that connects to the existing Flask POS server running in Termux.

IMPORTANT:
- This ZIP is Android Studio source project, NOT a compiled APK.
- It does not yet add the requested server-side Admin/employee authentication. That must be implemented and tested in Flask before production use.
- Termux server must be running and reachable on the same Wi-Fi.
- On the server phone use http://127.0.0.1:5000
- On waiter phones use http://PHONE_WIFI_IP:5000 (example only; find actual IP in Android Wi-Fi settings).
- Keep the Flask server bound to 0.0.0.0 and allow port 5000 on local network.

Build APK:
1. Install Android Studio on a computer.
2. Open this folder as a project.
3. Let Gradle sync complete (internet required to download Android Gradle Plugin).
4. Build > Build Bundle(s) / APK(s) > Build APK(s).
5. APK output: app/build/outputs/apk/debug/app-debug.apk

Do not expose this app/server to public internet without proper authentication, HTTPS and security hardening.
