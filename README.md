[![Android CI](https://github.com/vicky7230/TASKER-multi-module/actions/workflows/android-ci.yml/badge.svg)](https://github.com/vicky7230/TASKER-multi-module/actions/workflows/android-ci.yml)

# Tasker

> 🚧 **This project is currently in active development. Features and structure may change frequently.**

Tasker is a modular, modern Android application designed to help you manage tasks and notes efficiently. Built with Clean Architecture and MVVM design pattern, Tasker leverages the latest Android technologies for scalability, maintainability, and a delightful user experience.

---

## 📱 App Screenshots

<div align="center">
  <img src="graphics/1.png" width="200" alt="Notes List" />
  <img src="graphics/2.png" width="200" alt="Add/Edit Note" />
  <img src="graphics/3.png" width="200" alt="Tags Management" />
  <img src="graphics/4.png" width="200" alt="Note Details" />
</div>

---

## ✨ Features

- 📝 **Notes Management** - Create, edit, and organize notes with rich text support
- 🏷️ **Tags System** - Categorize notes with custom tags for better organization
- 🔍 **Search & Filter** - Quickly find notes using advanced search capabilities
- 📋 **Task Organization** - Manage tasks efficiently with due dates and priority levels
- 💾 **Offline First** - Full functionality without internet connection
- 🌙 **Modern UI** - Clean, intuitive interface built with Jetpack Compose
- ⚡ **Performance** - Fast, responsive, and optimized for smooth user experience
- 🔒 **Data Privacy** - All data stored locally on your device

---

## 🏗️ Project Structure

```
Tasker/
├── app/                        # Main application module
│   ├── src/main/
│   │   ├── java/               # Application class and dependency injection
│   │   └── AndroidManifest.xml
│   └── build.gradle.kts        # App-level build configuration
├── feature/                    # Feature modules (UI + Domain + Data)
│   ├── notes/                  # Notes feature module
│   │   ├── data/              # Data layer (repositories, data sources)
│   │   ├── domain/            # Domain layer (use cases, models)
│   │   └── ui/                # UI layer (composables, view models)
│   ├── add_edit_note/         # Add/Edit Note feature
│   │   ├── data/              # Data layer implementation
│   │   ├── domain/            # Business logic and use cases
│   │   └── ui/                # UI components and state management
│   └── tags/                  # Tags management feature
│       ├── data/              # Tags data layer
│       ├── domain/            # Tags domain logic
│       └── ui/                # Tags UI components
├── core/                      # Core modules (shared across features)
│   ├── common/                # Common utilities, extensions, and constants
│   ├── domain/                # Core domain models and interfaces
│   ├── database/              # Room database, DAOs, and entities
│   ├── network/               # Networking layer (Retrofit, OkHttp)
│   └── feature_api/           # Feature API contracts for navigation
├── config/                    # Build configuration and code quality
│   └── detekt/                # Static analysis configuration
└── gradle/                    # Gradle wrapper and version catalogs
    └── libs.versions.toml     # Centralized dependency management
```

This multi-module structure follows Android's recommended modularization best practices with clear
separation between features and core functionality.

---

## 🛠️ Tech Stack

### Core Technologies

- **[Kotlin](https://kotlinlang.org/)** `2.0.21` - Modern programming language for Android
- **[Jetpack Compose](https://developer.android.com/jetpack/compose)** - Declarative UI toolkit
- **[Android Gradle Plugin](https://developer.android.com/build)** `8.10.0` - Build system

### Architecture & Patterns
- **Clean Architecture** - Domain-driven design with clear separation of concerns
- **MVVM Pattern** - Model-View-ViewModel for UI state management
- **Multi-Module Architecture** - Scalable project structure
- **Dependency Injection** - Dagger 2 for dependency management

### Jetpack Libraries

- **[Compose BOM](https://developer.android.com/jetpack/compose/bom)** `2024.09.00` - Compose
  libraries versioning
- **[Navigation Compose](https://developer.android.com/jetpack/compose/navigation)** `2.9.0` -
  Navigation component
- **[Room Database](https://developer.android.com/jetpack/androidx/releases/room)** `2.7.2` - SQLite
  object mapping
- **[Lifecycle](https://developer.android.com/jetpack/androidx/releases/lifecycle)** `2.9.1` -
  Lifecycle-aware components

### Networking & Serialization

- **[Retrofit](https://square.github.io/retrofit/)** `2.11.0` - Type-safe HTTP client
- **[OkHttp](https://square.github.io/okhttp/)** `4.12.0` - HTTP client with logging interceptor
- **[Kotlinx Serialization](https://github.com/Kotlin/kotlinx.serialization)** `1.8.1` - JSON
  serialization

### Asynchronous Programming

- **[Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html)** `1.10.2` -
  Structured concurrency
- **[Flow](https://kotlinlang.org/docs/flow.html)** - Reactive streams for data observation

### Code Quality & Testing

- **[Detekt](https://detekt.dev/)** `1.23.8` - Static code analysis
- **[Ktlint](https://pinterest.github.io/ktlint/)** `1.4.1` - Kotlin linter and formatter
- **[JUnit](https://junit.org/junit4/)** `4.13.2` - Unit testing framework
- **[MockK](https://mockk.io/)** `1.14.2` - Mocking library for Kotlin
- **[Turbine](https://github.com/cashapp/turbine)** `1.2.0` - Testing library for Flow

### Development Tools

- **[KSP](https://kotlinlang.org/docs/ksp-overview.html)** `2.0.21-1.0.27` - Symbol processing API
- **[Dagger](https://dagger.dev/)** `2.56.2` - Compile-time dependency injection

---

## 🚀 Getting Started

### Prerequisites

- **Android Studio** Hedgehog (2023.1.1) or newer
- **JDK 17** or higher
- **Android SDK 35** (compile SDK)
- **Minimum Android 7.0** (API level 24)

### Installation

1. **Clone the repository:**
   ```bash
   git clone https://github.com/vicky7230/TASKER-multi-module.git
   cd TASKER-multi-module
   ```

2. **Open in Android Studio:**
   - Open Android Studio
   - Select "Open an existing project"
   - Navigate to the cloned repository folder

3. **Configure local.properties:**
   ```properties
   sdk.dir=YOUR_ANDROID_SDK_PATH
   # For release builds (optional):
   # RELEASE_STORE_FILE=path/to/keystore.jks
   # RELEASE_STORE_PASSWORD=your_store_password
   # RELEASE_KEY_ALIAS=your_key_alias
   # RELEASE_KEY_PASSWORD=your_key_password
   ```

4. **Sync and Build:**
   ```bash
   ./gradlew build
   ```

5. **Run the App:**
   - Connect an Android device or start an emulator
   - Click the "Run" button in Android Studio

### Development Commands

```bash
# Build the project
./gradlew build

# Run unit tests
./gradlew testDebugUnitTest

# Run Android tests (requires device/emulator)
./gradlew connectedDebugAndroidTest

# Code formatting
./gradlew ktlintFormat

# Static analysis
./gradlew detekt

# Clean build
./gradlew clean build
```

---

## 🏛️ Architecture

Tasker follows a modular Clean Architecture approach with MVVM pattern, ensuring scalability,
testability, and maintainability.

### Clean Architecture Layers

```
┌─────────────────────────────────────────────────────────────┐
│                        UI Layer                             │
│  ┌─────────────────┐  ┌─────────────────┐  ┌─────────────┐ │
│  │   Composables   │  │   ViewModels    │  │   States    │ │
│  └─────────────────┘  └─────────────────┘  └─────────────┘ │
└─────────────────────────────────────────────────────────────┘
                                │
                                ▼
┌─────────────────────────────────────────────────────────────┐
│                      Domain Layer                           │
│  ┌─────────────────┐  ┌─────────────────┐  ┌─────────────┐ │
│  │   Use Cases     │  │     Models      │  │ Repositories│ │
│  │   (Business     │  │   (Entities)    │  │ (Interfaces)│ │
│  │    Logic)       │  │                 │  │             │ │
│  └─────────────────┘  └─────────────────┘  └─────────────┘ │
└─────────────────────────────────────────────────────────────┘
                                │
                                ▼
┌─────────────────────────────────────────────────────────────┐
│                       Data Layer                            │
│  ┌─────────────────┐  ┌─────────────────┐  ┌─────────────┐ │
│  │  Repositories   │  │  Data Sources   │  │   Mappers   │ │
│  │ (Implementation)│  │ (Room, Network) │  └─────────────┘ │
│  └─────────────────┘  └─────────────────┘                  │
└─────────────────────────────────────────────────────────────┘
```

### Key Architectural Components

- **🎨 UI Layer** - Jetpack Compose UI, ViewModels, and UI state management
- **🏢 Domain Layer** - Business logic, use cases, and domain models
- **💾 Data Layer** - Repositories, data sources, and data mapping
- **🔗 Feature API** - Contracts for inter-module communication
- **⚙️ Core Modules** - Shared utilities, database, and networking

### Data Flow

```
User Interaction → Composable → ViewModel → Use Case → Repository → Data Source
                                    ↓
              UI State Update ←── Domain Model ←── Data Model ←── Database/API
```

### Dependency Injection

The app uses **Dagger 2** for dependency injection with the following structure:

- **Application Component** - Provides app-level dependencies
- **Feature Components** - Scoped dependencies for each feature
- **Module Pattern** - Organized provision of dependencies

---

## 📊 Project Statistics

- **Modules:** 13 (1 app + 12 library modules)
- **Minimum SDK:** Android 7.0 (API 24)
- **Target SDK:** Android 15 (API 35)
- **Build System:** Gradle with Kotlin DSL
- **Code Quality:** Detekt + Ktlint integration
- **Test Coverage:** Unit tests + Integration tests

---

## 🤝 Contributing

Contributions are welcome! Please read our contributing guidelines before getting started.

### How to Contribute

1. **Fork the repository**
2. **Create a feature branch:**
   ```bash
   git checkout -b feature/amazing-feature
   ```
3. **Make your changes and ensure code quality:**
   ```bash
   ./gradlew ktlintFormat detekt testDebugUnitTest
   ```
4. **Commit your changes:**
   ```bash
   git commit -m 'Add amazing feature'
   ```
5. **Push to the branch:**
   ```bash
   git push origin feature/amazing-feature
   ```
6. **Open a Pull Request**

### Development Guidelines

- Follow the existing code style and architecture patterns
- Write unit tests for new features
- Update documentation as needed
- Ensure all checks pass before submitting PR

---

## 📄 License

This project is licensed under the [Apache 2.0 License](LICENSE).

```
Copyright 2024 Vicky

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

---

## 🙏 Acknowledgements

- [Android Developers](https://developer.android.com/) - Official documentation and guidelines
- [Jetpack Compose](https://developer.android.com/jetpack/compose) - Modern declarative UI toolkit
- [Clean Architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html) -
  Robert C. Martin's architectural principles
- [Dagger 2](https://dagger.dev/) - Compile-time dependency injection framework
- [Room Persistence Library](https://developer.android.com/jetpack/androidx/releases/room) - SQLite
  abstraction layer

---

<div align="center">
  <p>Made with ❤️ by <a href="https://github.com/vicky7230">Vicky</a></p>
  <p>⭐ Star this repository if you find it helpful!</p>
</div>
