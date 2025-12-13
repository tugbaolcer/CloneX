# 🚀 CLONEX  
## 📱 Android Application


CloneX is an Android application developed using the TMDB (The Movie Database) API.
The project is designed with modern Android development practices in mind. The user interface of this application is inspired by the Netflix mobile app.
The design is not a direct copy of Netflix; instead, it takes inspiration from its content presentation, visual hierarchy, and user experience approach to create an original interface.
All UI components and implementations are custom-built for this project.

🛠 Tech Stack

- Kotlin

- MVVM (Model–View–ViewModel)

- Retrofit

- Dagger / Hilt

- DataBinding

- DataStore

- Deeplink

- UI Tests

- Unit Tests

🔐 Authentication & Login Flow

CloneX uses TMDB’s Bearer Token–based authentication mechanism.

🔹 User Flow

1. User is redirected to the TMDB website for Register / Login.

2. After successful authentication, TMDB provides an Access Token (Bearer Token).

3. This token is used in all API requests from the app.

⚠️ TMDB does not provide a native email/password login endpoint.
Therefore, authentication must be completed via the web.

⚙️ API Key Configuration (Required)

To run the project, you must define your TMDB Bearer Token locally.

1️⃣ Add to local.properties
API_KEY=Bearer YOUR_TMDB_ACCESS_TOKEN

2️⃣ Expose via BuildConfig
buildConfigField "String", "API_KEY", "\"${API_KEY}\""

3️⃣ Use in Retrofit Headers (app/src/main/java/com/tugbaolcer/clonex/di/NetworkModule.kt)
val requestBuilder = request.newBuilder()
    .url(url)
    .addHeader("Authorization", BuildConfig.API_KEY)


After this setup, the app will be able to communicate with TMDB services successfully.

🔒 Security Note

Currently, the Access Token is stored in local.properties.
For production-ready usage, the following approaches are planned:

- Native secure storage (NDK / Encrypted DataStore)

- or token management via a secure backend service

This setup is intended for development and demo purposes.

🧪 Testing

- Unit Tests for ViewModels and business logic

- UI Tests for validating user interactions

Tests are written with maintainability and scalability in mind.

📌 Final Note

This project does not strictly enforce Clean Architecture,
but focuses on a clean, testable, and maintainable Android codebase.
