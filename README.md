# Real Time Signal Visualizer
A native app (Kotlin/Compose for Android) that simulates a live data feed (e.g., a heart rate or sensor signal) and renders it on a custom-drawn graph.

## Question 1 - Archiecture and Thread Safety
### Architecture
As stated in the requirements that data must be independent of UI rendering and data points should not disappear on screen rotation, hence i chose MVVM architecture where it has
- ViewModel- Contains the logic
- Repository - Contains network fetching logic (Not required in this assignment)
- View - Handles all the compose UI  

Why I Chose MVVM architecture:  

1. **Configuration Change Survival**: ViewModels automatically survive screen rotation. When the device rotates, the Composable is destroyed and recreated, but the ViewModel and its data persist. This ensures the 300 accumulated data points are not lost.

2. **Separation of Concerns**: Business logic (data generation, smoothing) is isolated from UI code, making the app easier to test and maintain.

3. **Reactive UI Updates**: Using StateFlow with Compose's `collectAsState()` provides automatic UI updates whenever new data is emitted, without manual state management.

### Thread Safety
The assignment emphasized that the UI must remain fluid (60 FPS) and not "jank."

To achieve this, I used Kotlin Coroutines with Dispatchers.Default.

- Background Processing: I wrapped the data generation loop in viewModelScope.launch(Dispatchers.Default). This ensures that the random number generation and the moving average calculations happen on a background thread, not the main UI thread.

- State Updates: I used StateFlow.update { } to modify the list. This function is thread-safe, so I don't run into issues where the UI tries to read the list at the exact same moment the background thread is writing to it.

## Question 2 - Memory Management
As we know the numbers keep on generating indefinitely , the list of data points could grow infinitely and the app could crash. To prevent this i used a fixed size buffer
- I set a limit of 300 points (which equals 30 seconds of data at 100 ms intervals)
- If the list exceeds 300, the oldest point at index 0 is removed
- This ensures memory usage stays constant