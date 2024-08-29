# Github Repositories

## Overview
This project is an Android application that fetches and displays GitHub repositories. It is built using kotlin, and follows a modular architecture.

## Project Structure
The project is divided into several modules:
- `:app`: The main application module. It contains the main activity. It orchestrates the other modules and navigates between them.
 
- `:feature:repos`: It is responsible for displaying repositories and their details. It contains the ui components and viewmodels.
- `:data:repos`:It contains local and remote data sources, repositories, and use cases for fetching repositories.

- `:logging`: Provides logging functionalities.
- `:connectivity`: Manages network connectivity.

Modularization is based on https://developer.android.com/topic/modularization/patterns.

## SyncReposUseCase
Located in `repos/src/main/java/com/cyalc/repositories/SyncReposUseCase.kt`, this class is responsible for synchronizing repositories from the GitHub API and storing them in the local database.

RepoDbModel is the model used to store repositories in the local database. It is a Room entity.
RepoApiModel is the model used to represent repositories from the GitHub API.
Repo is the domain mode
RepoUiModel is the model used to represent repositories in the UI.

## Testing
Unit tests for the `SyncReposUseCase` is in the `test` directory of the `:data:repos` module.
Unit tests for the `RepoListViewModel` and `RepoDetailsViewModel` classes are located  `test` directory of the `:feature:repos` module.

#### Dependencies
- AndroidX libraries for core functionalities and UI components.
- Room for local database management.
- Retrofit for network operations.
- Koin for dependency injection.
- MockK for testing.

### Prerequisites
- Android Studio
- Kotlin 1.9.20 or higher
- Java 19

### CI/CD
- Github Actions is used for CI/CD. The workflow is defined in `.github/workflows/pull_request.yml`. It runs the unit tests.

### TO-DO
- [ ] Add UI tests
- [ ] Inject Dispatchers
- [ ] Get strings from resources
- [ ] Add turbine for easier testing
- [ ] Add more error handling
- [ ] Test configuration changes and process death
- [ ] Add ktlint (also to ci)
- [ ] Add scopes to koin modules
- [ ] Simplify and reuse gradle files
- [ ] Add baseline profiles

