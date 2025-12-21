# Real Time Signal Visualizer
A native app (Kotlin/Compose for Android) that simulates a live data feed (e.g., a heart rate or sensor signal) and renders it on a custom-drawn graph.

## Question 1 - Archiecture and Thread Safety
### Architecture
As stated in the requirements that data must be independent of UI rendering and data points should not disappear on screen rotation, hence i chose MVVM architecture where it has
- ViewModel- Contains the logic
- Repository - Contains network fetching logic (Not required in this assignment)
- UI Screen - Handles all the compose UI
I Chose MVVM architecture because
1. **Configuration Change Survival**: ViewModels automatically survive screen rotation. When the device rotates, the Composable is destroyed and recreated, but the ViewModel and its data persist. This ensures the 300 accumulated data points are not lost.

2. **Separation of Concerns**: Business logic (data generation, smoothing) is isolated from UI code, making the app easier to test and maintain.

3. **Reactive UI Updates**: Using StateFlow with Compose's `collectAsState()` provides automatic UI updates whenever new data is emitted, without manual state management.

### Thread Safety
For thread safety i used Dispatchers.Default background thread which is there to handle all the heavy compuatational tasks