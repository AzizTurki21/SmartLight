# SmartLight 💡

An Android prototype for sending light on/off commands over MQTT. The app has two buttons and publishes `on` or `off` to an MQTT topic.

The Android project is in [android/dhawbiti](android/dhawbiti/). The original archive is kept at [smartlight.zip](smartlight.zip).

## Open the app

Open `android/dhawbiti` in Android Studio and let Gradle sync. The project includes the Gradle wrapper. It targets Android API 33, compiles against API 34, and has a minimum API level of 24.

## MQTT demo settings

The current values are in `MainActivity.java`:

- Broker: `tcp://broker.hivemq.com:1883`
- Topic: `dhaw/output`
- Client ID: `AndroidClient`

This is a public broker connection without TLS or authentication, and the topic is not private. Treat it as a learning demo only. Do not connect it to a real light or other device. For a real setup, use a broker and topic you control, with authentication and TLS, and configure a unique client ID. This repository contains the Android client; it does not include receiver firmware or a wiring diagram.

---

[©](https://www.google.com/url?sa=t\&rct=j\&q=\&esrc=s\&source=web\&cd=\&ved=2ahUKEwjzju28hu6XAxXJRv4FHRh3Ig0QFnoECBAQAQ\&url=CAESWAHrOzAVtYcEstJL-hlsYH2cx_UHgieapeL5nBQtUKWwQaRB8lQyJ1UlN9og_Cn8Qde_uZWtOCmSAz24iGijz5qs9WjGyp0szyxoXINP5SwQlImWqFiMGWE\&uoh=2\&usg=AOvVaw3RsaJBv9Ghf9gJTeeFH94z\&opi=89978449) reserved to MAT
