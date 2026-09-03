# SparkChat - Android 

SparkChat is a robust, local-first chat application built with modern Android technologies, strictly following Clean Architecture and the MVVM pattern. It demonstrates a high-quality implementation of a messaging interface with persistent storage and comprehensive testing.

## 🚀 Architectural Decisions

### 1. Fully Stateless Screen Pattern
The UI is refactored into a **Stateless Screen** pattern. 
- **Navigation Hoisting**: The navigation layer (`ChatNavigation.kt`) acts as the state hoister. It handles ViewModel injection, manages mutable UI states (like text input and scroll positions), and wires events.
- **Pure Composables**: `ChatListScreen` and `ChatDetailScreen` are pure UI components with zero dependencies on ViewModels or Hilt. This makes them perfectly suited for **Compose Previews** and isolated UI testing.

### 2. Clean Architecture (Monolith)
The project is structured into clear layers to ensure separation of concerns:
- **Domain**: Pure Kotlin logic containing Entities and Use Cases.
- **Data**: Infrastructure layer managing Room DB, DAOs, and repository implementations.
- **Presentation**: UI layer using Jetpack Compose and ViewModels.

### 3. Observable Data Flow
The app uses Room as the **Single Source of Truth**. Data flows from the database through the repository to the UI via Kotlin Flows, ensuring the interface always reflects the latest state of the persistent storage.

### 4. Efficient Message Loading (Paging 3)
To ensure the app scales smoothly as chat history grows, **Paging 3** is integrated into the message list. This provides seamless scrolling and efficient memory management even with thousands of local messages.

---

## 💡 Implementation Assumptions

- **Local Persistence Only**: Based on the project requirements, I assumed no external network connectivity was needed. All data is managed locally via Room.
- **Simulated Two-Way Messaging**: To satisfy the requirement of triggering messages from the "other" user, I implemented a **"Me / Replying" toggle** in the Top Bar of the chat detail screen. This allows for easy testing of bubble alignment and notification-like behavior.
- **Fixed User Set**: I assumed a fixed initial user set for the "Sarah" design scenario, though the architecture is fully prepared for dynamic user creation.
- **Avatar Assets**: Profile images are assumed to be static assets bundled with the app (loaded via Coil), as no cloud-based image storage was requested.
- **API Compatibility**: While using `LocalDateTime` (API 26+), I assumed the use of **Core Library Desugaring** to maintain compatibility with the `minSdk 24` requirement.

---

## 🛠 Tech Stack

- **Jetpack Compose**: Declarative UI.
- **Hilt**: Dependency Injection.
- **Room**: Local SQL database.
- **Paging 3**: Efficient data loading.
- **Kotlin Coroutines & Flows**: Asynchronous programming.
- **Coil 3**: Image loading.
- **Kotlin Serialization**: Type-safe navigation.
- **AndroidX Core Splashscreen**: Polished entry experience.

---

## 🧪 Testing Strategy

The app includes a high-value testing suite across all layers:
- **Unit Tests (`src/test`)**: Verifying ViewModel state transitions and event logic using **MockK**, **Turbine**, and **Robolectric**.
- **Instrumentation Tests (`src/androidTest`)**:
    - **DAO Tests**: Verifying complex SQL queries (e.g., latest message retrieval) in an in-memory Room database.
    - **UI Tests**: Validating stateless screen rendering and user interactions in isolation.

### Running Tests
- **Unit Tests**: `./gradlew app:testDebugUnitTest`
- **Instrumentation Tests**: `./gradlew app:connectedDebugAndroidTest` (requires a connected device/emulator)
