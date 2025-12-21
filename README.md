# Real Time Signal Visualizer
A native app (Kotlin/Compose for Android) that simulates a live data feed (e.g., a heart rate or sensor signal) and renders it on a custom-drawn graph.

## Answer 1 - "Architecture: Briefly explain your choice of architecture (MVVM, MVI, etc.) and how you handled thread safety"
As stated in the requirements that data must be independent of UI rendering and data points should not disappear on screen rotation, hence i chose MVVM architecture where 
- ViewModel- Contains the logic
- Repository - Contains network fetching logic 
- UI Screen - Handles all the compose UI

By default View Model survives screen rotation in android as its independent of ui rendering.
### For thready safety