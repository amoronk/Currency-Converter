# Currency Converter

A currency converter application built with Jetpack Compose.

## Screen recording
https://github.com/user-attachments/assets/64c67260-ecff-44aa-b763-c1394a5e12b1

## Features

- Currency conversion 
- Historical rate charts for 30 and 90 day periods
- Swap currencies with one tap
- Offline support with local database caching
- Error handling and loading states

## Libraries & Frameworks

- **Kotlin** - 100% Kotlin codebase
- **Jetpack Compose** - Modern UI toolkit for building native UI
- **MVVM Architecture** - Clear separation of concerns
- **Hilt** - Dependency injection
- **Retrofit** - HTTP client for network calls
- **Room** - Local database for caching
- **Coroutines** - Asynchronous programming
- **Flow** - Reactive programming
- **MPAndroidChart** - For displaying historical rate charts
- **Material 3** - Modern design system

## Project Structure

The project follows a clean architecture approach with:

- **data** - Contains data sources, repositories, and DTOs
- **domain** - Contains business models and use cases
- **presentation** - Contains UI components, ViewModels, and state holders

## Setup

### API Key

The app uses [Fixer.io](https://fixer.io/) for currency data. You'll need to:

1. Sign up for a free API key at [Fixer.io](https://fixer.io/)
2. Create a file named `apikey.properties` in the root project folder
3. Add your API key to the file in this format:
```
FIXER_API_KEY=your_api_key_here
```

### Build and Run

1. Clone the repository
2. Open the project in Android Studio
3. Sync the Gradle files
4. Run the app on an emulator or physical device

## Testing

The project includes unit tests:

- Unit tests for the domainlayer using JUnit and Mockk

## Architecture

The application follows the MVVM architecture with Clean Architecture principles:

1. **UI Layer (Presentation)**
   - Composable UI components
   - ViewModels that hold UI state and handle UI logic
   - State holders for representing UI state

2. **Domain Layer**
   - Use cases for business logic
   - Domain models representing core data
   - Repository interfaces

3. **Data Layer**
   - Repository implementations
   - Data sources (local and remote)
   - DTOs and entity mappers

## Future Improvements
- More extensive testing coverage
