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
```kotlin
 viewModelScope.launch(Dispatchers.Default){
    while (true)
        {
            val newnum=Random.nextInt(0,101)
            signal.update{ currentlist ->
            val newlist = currentlist.toMutableList()
            newlist.add(newnum)
            if (newlist.size > 300) {
                    newlist.removeAt(0)
            }
                newlist
            }
                delay(100)
            }
       }
```

## Question 2 - Memory Management
As we know the numbers keep on generating indefinitely , the list of data points could grow infinitely and the app could crash. To prevent this i used a fixed size buffer
- I set a limit of 300 points (which equals 30 seconds of data at 100 ms intervals)
- If the list exceeds 300, the oldest point at index 0 is removed
- This ensures memory usage stays constant
```kotlin
 if (newlist.size > 300) 
 {
    newlist.removeAt(0)
} 
```

## Question 3 - Architectural Changes to keep ui responsive at 1ms
My current approach updates the UI state every time a new data point arrives. At 1ms intervals (1000 updates/second), this would cause performance issues because standard mobile screens only refresh at 60Hz (60 times/second). Trying to redraw the screen 1000 times a second is wasteful and would freeze the UI.
To handle this, I would change the architecture to a Producer-Consumer pattern with Batching:

1. Decoupling: I would separate the "Data Collection" from the "UI Update." The background thread would continue collecting data at 1ms into a temporary buffer/queue.

2. Batch Updates: Instead of pushing every single point to the UI immediately, I would have a separate loop that wakes up every 16ms (synced with the 60Hz frame rate).

3. Processing: This loop would take all the new points accumulated in that 16ms window (approx. 16 points) and send them to the UI in one single update.



