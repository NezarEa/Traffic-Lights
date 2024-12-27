# Traffic Light Simulation App

## Overview
This is an Android application simulating a real-world traffic light system. The app replicates the sequence of lights (red, yellow, green) with dynamic timing and intuitive visual feedback, including rounded corners and a countdown timer.

## Features
- **Traffic Light Simulation**:
  - Red light: 30 seconds.
  - Yellow light: 5 seconds.
  - Green light: 25 seconds.
- **Automatic Transitions**: The lights transition automatically through the correct sequence.
- **Midnight Mode**: At midnight, the light automatically switches to yellow for a specific period.
- **Timer Display**: A countdown timer shows the remaining duration for each light.
- **Rounded Corners**: Aesthetic design for the lights using rounded corners.
- **Orientation Adaptability**: Retains the current state and adjusts the UI seamlessly in both portrait and landscape orientations.

## Technology Stack
- **Language**: Kotlin
- **UI Framework**: Android Views (ConstraintLayout, LinearLayout)
- **Architecture**: MVVM (Model-View-ViewModel)
- **Concurrency**: ScheduledExecutorService for scheduled tasks
- **Dynamic Colors**: Drawable resources for state-specific designs

## How It Works
1. **Light Sequence**:
   - The lights cycle in the following order: Red → Green → Yellow → Red.
   - Each light stays active for its predefined duration.
2. **Midnight Trigger**:
   - A scheduled task detects midnight and switches the light to yellow for a short period.
3. **User Interface**:
   - The traffic lights are visually represented as `View` elements with dynamic background colors and rounded corners.
   - A `TextView` displays the countdown timer for each light.

## Installation
1. Clone the repository:
   ```bash
   git clone https://github.com/your-repository/traffic-light-simulation.git
   ```
2. Open the project in Android Studio.
3. Sync Gradle to download dependencies.
4. Run the app on an emulator or physical device.

## Usage
- Launch the app.
- Observe the lights transition through the sequence automatically.
- The countdown timer displays the time remaining for the active light.
- At midnight, the light will switch to yellow.

## Future Enhancements
- Add a manual mode for user-controlled light changes.
- Include animations for smoother transitions.
- Support multiple languages for a global audience.
- Add a statistics screen to display light sequence data.

## Contributing
1. Fork the repository.
2. Create a feature branch:
   ```bash
   git checkout -b feature-name
   ```
3. Commit your changes:
   ```bash
   git commit -m "Add feature"
   ```
4. Push to the branch:
   ```bash
   git push origin feature-name
   ```
5. Open a pull request.
- Navigate to your fork on GitHub.
- Click the Compare & pull request button.
- Add a title and description for your pull request.
- Click Create pull request.

---

Enjoy using the Traffic Light Simulation App! Feel free to contribute or provide feedback.
