# SafeRoute — Smart Women Safety Application

SafeRoute is a native Android application built to help women respond quickly and safely to unsafe situations. With a single tap (or a shake), it can send an SOS alert with live location to trusted contacts, generate a fake incoming call to create a distraction, and fall back to SMS when there's no internet connection.

> "SafeRoute is more than an app — it's a digital safety companion."

## Problem

Women's safety is a major concern in many regions due to rising cases of harassment, kidnapping, and street crime. In unsafe situations, it's often hard to make a call or type a message for help — and most existing safety apps require manual steps or a stable internet connection, which isn't always reliable in an emergency.

## Features

- **One-Tap SOS Alert** — Instantly sends live location and a custom message to trusted/emergency contacts.
- **Real-Time Location Tracking** — Shares live location with trusted contacts via a background location service.
- **Fake Call Generator** — Simulates an incoming call with a configurable fake caller to help the user exit uncomfortable situations.
- **Offline Mode (SMS Fallback)** — Sends alerts over SMS when there's no internet connection.
- **Trusted & Emergency Contacts Management** — Add, edit, and manage the people who get notified.
- **Onboarding / App Guide** — Walks new users through how the app works.

## Screenshots

| Splash / Onboarding | Home | SOS / Contacts |
|---|---|---|
| ![Splash screen](docs/screenshots/splash.png) | ![Home screen](docs/screenshots/home.png) | ![Contacts screen](docs/screenshots/contacts.png) |

| Fake Call | App Guide |
|---|---|
| ![Fake call screen](docs/screenshots/fake_call.png) | ![App guide screen](docs/screenshots/app_guide.png) |

> Drop your screenshots into a `docs/screenshots/` folder in the repo using these filenames (or update the paths above to match your own). PNG or JPG both work.

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
   git clone https://github.com/<your-username>/saferoute.git
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

## Team

Built as a Mobile Application Development (MAD) project by:
- **Aafia Azhar**
- **Anoosha Ikram**

## Social Impact

SafeRoute supports UN SDG 5 (Gender Equality) and SDG 11 (Sustainable Cities and Communities) by giving women a fast, reliable way to reach help in unsafe situations.

## License

This project is licensed under the MIT License — see the [LICENSE](LICENSE) file for details.
