# Welcome App (Android Tablet)

Startbildschirm mit zentriertem "Welcome", unten links **Hilfe** (zeigt "es gibt keine"),
unten rechts **Beenden** (schließt die App).

## Bauen und starten

1. Ordner `android-app` in Android Studio öffnen (File > Open).
2. Gradle-Sync abwarten (Android Studio lädt Gradle 8.9 und das SDK 35 bei Bedarf).
3. Tablet per USB (USB-Debugging an) oder Tablet-Emulator wählen und auf **Run** klicken.

Alternativ per Kommandozeile (wenn Gradle installiert ist): `gradle wrapper` einmalig,
danach `./gradlew assembleDebug` – das APK liegt dann unter `app/build/outputs/apk/debug/`.
