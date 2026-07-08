#  <img width="70" height="70" alt="image" src="https://github.com/user-attachments/assets/428532d4-ec88-459e-b1f1-b89ef56c65c1" />SafeRoute — Smart Women Safety Application


SafeRoute is a native Android application built to help women respond quickly and safely to unsafe situations — **and it works even without an internet connection.** With a single tap, it can send an SOS alert with live location to trusted contacts over SMS, generate a fake incoming call to create a distraction, and keep working when data or Wi-Fi isn't available.

> "SafeRoute is more than an app — it's a digital safety companion."

## Problem

Women's safety is a major concern in many regions due to rising cases of harassment, kidnapping, and street crime. In unsafe situations, it's often hard to make a call or type a message for help — and most existing safety apps require a stable internet connection, which isn't always available in an emergency.

## Features

- 🚨 **Works Without Internet** — SafeRoute's core SOS flow runs entirely over SMS, so alerts still go out in low-signal areas, during data outages, or when Wi-Fi/mobile data is off. No app should ever leave you stranded when you need it most.
- **One-Tap SOS Alert** — Instantly sends live location and a custom message to trusted/emergency contacts.
- **Real-Time Location Tracking** — Shares live location with trusted contacts via a background location service (when internet is available).
- **Fake Call Generator** — Simulates an incoming call with a configurable fake caller to help the user exit uncomfortable situations.
- **Trusted & Emergency Contacts Management** — Add, edit, and manage the people who get notified.
- **Onboarding / App Guide** — Walks new users through how the app works.

## Screenshots
<img width="200" height="400" alt="11" src="https://github.com/user-attachments/assets/43dbfdf5-3672-46dc-b5c1-19bc0dfb5ed0" />
<img width="200" height="400" alt="22" src="https://github.com/user-attachments/assets/4fc5becd-34a2-4e40-b25f-3cabb8f3a5f8" />
<img width="200" height="400" alt="msg" src="https://github.com/user-attachments/assets/5e4d5a2b-5de8-4eba-8014-5d34b904b5b9" />
<img width="200" height="400" alt="3" src="https://github.com/user-attachments/assets/316a7f6d-92bb-4bbc-bf11-5281d66dfa03" />
<img width="200" height="400" alt="4" src="https://github.com/user-attachments/assets/aa2992c6-e485-48c2-bbc8-ce238940fd9b" />



## Tech Stack

- **Language:** Kotlin
- **Platform:** Native Android (min SDK 24, target/compile SDK 36)
- **UI:** Android View system with View Binding, Material Components, ConstraintLayout
- **Location:** Google Play Services Location API
- **Backend:** Firebase (Realtime Database, Analytics) via Firebase BoM
- **Build System:** Gradle (Kotlin DSL)

## Project Structure

```
saferoute/
├── app/
│   ├── src/main/
│   │   ├── java/com/example/saferoute/
│   │   │   ├── MainActivity.kt
│   │   │   ├── SplashScreen.kt
│   │   │   ├── TrustedContactsActivity.kt
│   │   │   ├── EmergencyContactsActivity.kt
│   │   │   ├── EmergencyContact.kt
│   │   │   ├── YourInformationActivity.kt
│   │   │   ├── AppGuideActivity.kt
│   │   │   ├── FakeCallActivity.kt
│   │   │   ├── FakeCallerActivity.kt
│   │   │   ├── FakeCallerInfo.kt
│   │   │   ├── FakeCallerSettingsActivity.kt
│   │   │   ├── SOSService.kt
│   │   │   ├── SirenService.kt
│   │   │   ├── ShakeDetectorService.kt
│   │   │   ├── LiveLocationService.kt
│   │   │   ├── LocationHelper.kt
│   │   │   └── SMSHelper.kt
│   │   ├── res/                 # layouts, drawables, icons, fonts, strings
│   │   └── AndroidManifest.xml
│   └── build.gradle.kts
├── gradle/
├── build.gradle.kts
├── settings.gradle.kts
└── gradlew / gradlew.bat
```

## Permissions Used

| Permission | Why it's needed |
|---|---|
| `SEND_SMS` | Sending SOS alerts via SMS fallback |
| `ACCESS_FINE_LOCATION` / `ACCESS_COARSE_LOCATION` | Getting the user's live location |
| `FOREGROUND_SERVICE`, `FOREGROUND_SERVICE_LOCATION`, `FOREGROUND_SERVICE_DATA_SYNC` | Running location tracking and shake-detection as foreground services |
| `POST_NOTIFICATIONS` | Notifying the user while a safety service is active |
| `INTERNET` | Firebase sync and live location sharing online |

## Getting Started

### Prerequisites
- Android Studio (latest stable)
- JDK 11
- An Android device/emulator running API 24+

### Setup
1. Clone the repository:
   ```bash
   git clone https://github.com/<aafia1>/saferoute.git
   ```
2. Open the project in Android Studio.
3. Add your own `google-services.json` (from your Firebase project) into the `app/` directory — this file is **not** included in the repo for security reasons. See [Firebase setup](https://firebase.google.com/docs/android/setup) if you need to create a project.
4. Let Gradle sync, then run the app on a device or emulator.

## Roadmap / Future Work

- **Shake to Alert** — Trigger an SOS alert automatically when the phone is shaken, even if it can't be unlocked in time. (`ShakeDetectorService.kt` exists as a starting point but this feature is not fully wired up yet.)
- In-app history log of past SOS alerts
- iOS version

## Contributing

Contributions, bug reports, and suggestions are welcome — see [CONTRIBUTING.md](CONTRIBUTING.md) for how to get started.


## Social Impact

SafeRoute supports UN SDG 5 (Gender Equality) and SDG 11 (Sustainable Cities and Communities) by giving women a fast, reliable way to reach help in unsafe situations.

## License

This project is licensed under the MIT License — see the [LICENSE](LICENSE) file for details.
