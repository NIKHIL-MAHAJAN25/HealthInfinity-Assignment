# Real Time Signal Visualizer
A native app (Kotlin/Compose for Android) that simulates a live data feed (e.g., a heart rate or sensor signal) and renders it on a custom-drawn graph.

## Question 1 - Archiecture and Thread Safety
### For Screen Rotaion safety
As stated in the requirements that data must be independent of UI rendering and data points should not disappear on screen rotation, hence i chose MVVM architecture where it has
- ViewModel- Contains the logic
- Repository - Contains network fetching logic (Not required in this assignment)
- UI Screen - Handles all the compose UI
By default View Model survives screen rotation in android as its independent of ui rendering.
### For thread safety
For thread safety i used Dispatchers.Default background thread which is there to handle all the heavy compuatational tasks