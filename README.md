# PokemonApp

A sample Android app showcasing Jetpack Compose, Kotlin Coroutines & Flow, Hilt DI, Retrofit/Moshi, and Material 3. 

## Requirement

1. Fetch and Browse a list of Pokémon 

2. Tap on a Item to see details (name, image, height).

## Technical Decisions & Reasoning
1. Architecture: Followed Clean Architecture with distinct layers – Presentation (Jetpack Compose), Domain (UseCases), Data (Repository, RemoteDataSource).

2. State Management: Used StateFlow + UiState sealed class for clean and observable UI state handling (Loading, Success, Error).

3. Dependency Injection: Integrated Hilt for constructor-based DI, promoting testability and modularity.

4. Null Safety: Applied null-safe design by handling nullable API responses in the repository and mappers using Kotlin’s idioms (orEmpty(), safe calls).

5. Error Handling: Used Throwable.toUiMessage() to convert exceptions to user-readable strings (e.g., No Internet, HTTP Error).

6. Empty/Edge State UI - Show List Empty Error State and handle UI edge cases if any detail is missing.


### Testing:

- Unit tests written for ViewModels, UseCases, Repository, and Mappers using JUnit5 + MockK.

- One Compose UI test added for PokemonListScreen interaction using ComposeTestRule.

### Separation of Concerns: 

- DTO to Domain mapping is encapsulated in dedicated mapper classes, keeping data and domain layers decoupled.

## DEMO VIDEO and Screenshots:

https://github.com/user-attachments/assets/2d101494-3f79-4c84-b392-5c620214c7a1

<img width="315" alt="Screenshot 2025-05-05 at 4 11 55 PM" src="https://github.com/user-attachments/assets/8af80c33-5dce-412b-a6c2-ddca7df1968a" />

<img width="312" alt="Screenshot 2025-05-05 at 4 12 07 PM" src="https://github.com/user-attachments/assets/4460753d-7c4e-4b08-8fad-77320c67dc89" />

<img width="315" alt="Screenshot 2025-05-05 at 4 11 31 PM" src="https://github.com/user-attachments/assets/8955c2d8-6498-4716-a35a-8a3f34578b12" />

<img width="314" alt="Screenshot 2025-05-05 at 4 11 46 PM" src="https://github.com/user-attachments/assets/aa186343-f8d3-419a-9dc5-1f2178656f07" />


## What Is Missing & Why

1. Retry Logic	- Not implemented, Wanted to keep focus on core MVVM, can be easily added using onRetry callback tied to ViewModel reload

2. Pagination - Not implemented, Out of scope for this MVP-style implementation

3. Configuration Change Handling - ViewModels survive config change, but no special state restoration logic

4. Pull-to-Refresh / SwipeRefresh - Not added, Focus was on structure and architecture rather than UI affordances

5. Auto Reload on Network Callback  - Not implemented, Could be done using ConnectivityManager or Jetpack's NetworkCallback


## Additional Notes

- UI: Used Jetpack Compose with Material 3 design principles.

- Theming: Light/Dark mode previews are added for all composables.

- Test Tags: UI test uses stable testTags for targeting UI nodes.

- Flexibility: Flow-based use cases enable easy testing and recomposition.


## Some of my Projects & Contributions for your reference:

 - My Portfolio - https://bento.me/arunportfolio

- A Compose Project with Firebase integration - https://github.com/sairam1592/My-Movie-Shelf-Android-App

- Also recently for our Mindvalley Android app, i really enjoyed implementing a Multi tab Search feature with pagination built entirely using compose in just 3 days - Article - https://medium.com/@arundroidkl92/implementing-a-multi-tab-search-feature-in-jetpack-compose-01b8c189ed37
