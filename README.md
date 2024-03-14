# Rapid-Rescue

# RapidRescue: A Real-Time Collision Detection and Emergency Response System

Rapid Rescue is an advanced mobile application meticulously crafted for real-time monitoring and swift emergency response during driving. The application places user safety at the forefront by seamlessly incorporating personal details, health information, and emergency contacts into a secure storage system.



https://github.com/SwarajKotapati/RapidRescue/assets/70504997/ca28315c-7ccb-46c0-886d-be1b33a4fc12



Prerequisites Software:
Android Studio
- Latest Android Version (KitKat Minimum).
Java Development Kit (JDK):
- Minimum SDK of 34.

Android Studio requires JDK (minimum version requires is 1.8) to be installed. You can download it from Oracle's website or use an alternative like OpenJDK.

Other Requirements:
- Active internet connection.
- GPS Location Access permission to the application

## Instructions to Run:

1. Make sure you enable your internet and location permission upon first launch.
2. Install Telegram and search Rapid Rescue bot, then send out a “Hi” message for
the bot to register you as a user.
3. On the home page, select the User Profile button and enter your personal data.
4. Then click on Start Driving to start the accelerometer and GPS services.
5. On the dashboard you’ll see real-time data such as heart rate and respiratory rate
and speed data.
6. On fall detection or sudden speed change, the app navigates to confirmation
giving the user the option to send the alert immediately or to cancel the alert.
7. If canceled on the confirmation page, the app redirects to the dashboard page.
8. Whereas, when clicked on trigger alert, the app sends out an alert to the
emergency contact entered in the user profile section.
9. The app also proactively scans the health data such as respiratory rate and heart
rate.
10. The app also detects an anomaly in the health data received and navigates to
the same confirmation page.

Steps to run the application from Android Studio:

Launch Android Studio.

Import Project:
If you are opening an existing project, click on "Open an Existing Project" and navigate to your project folder.

Build Gradle:
Android projects use Gradle for building. Wait for Android Studio to sync and download the necessary dependencies. This may take some time.

Connect a Device or Use an Emulator:
Connect a physical Android device to your computer using a USB cable, or use an emulator provided by Android Studio. You can create a new emulator by going to Tools -> AVD Manager and creating a new Virtual Device.

Run the App:
Click on the green play button in the toolbar, or go to Run -> Run 'app'. This will compile your code and install the app on the connected device or emulator.

View the App:
Once the build is successful, you should see your app running on the device or emulator.

No additional external files & executables are required apart from the dependencies already mentioned in build gradle files.

## Key Features:

### Real-Time Collision Detection:
- Instant identification of potential collisions through real-time analysis of vital signs, speed, and accelerometer data.

### Collision Prediction using Health Data:
- Utilizes real-time health data to detect anomalies in heart and respiratory rates, triggering timely alerts for potential collisions.

### Automated Trigger Response System:
- Automatically initiates an emergency alert if the user does not respond within 7 seconds.

### Rapid Notification using REST API:
- The Telegram bot promptly forwards alert messages to designated users, ensuring a swift and effective emergency response.

### User-Initiated Cancellation:
- Empowers users with the ability to cancel alerts, seamlessly redirecting them back to the sensor page.

## Files Included:

### Activity Files:

- *MainActivity.kt:*
  - Home Screen with options to start driving, enter profile data, or send an immediate alert.

- *ConfirmationActivity.kt:*
  - Provides users with options to trigger an alert or cancel it.

- *SensorActivity.kt:*
  - A real-time dashboard displaying heart rate, respiratory rate, and real-time speed.

### Services:

- *AccelerometerClass:*
  - Actively scans sensor data and alerts in case of a fall.

- *SpeedometerClass:*
  - Actively scans speed data from the GPS sensor and alerts in case of a sharp decrease in speed.

- *SpeedCallBack.kt:*
  - An event callback to propagate data from the speedometer service to the sensor activity.

### Database:

- *AppDatabase.kt:*
  - Initializes the database.

- *TableData.kt:*
  - Initializes the table.

- *TableEntryDao.kt:*
  - Defines Dao operations.

### Model Classes:

- *UserMonitoringData.kt:*
  - An object class containing heart rate and respiratory rate data extracted from various wearable watches.

### Util Classes:

- *DatabaseUtil.kt:*
  - Contains methods to operate on the Room Database.

- *Util.kt:*
  - Contains logic to extract the latest data from the database, obtain the current GPS location, and handle POST requests to the TelegramBot.

### Fuzzy Inference Classes:

- *UserHealthFuzzyInterfaceSystem:*
  - A FIS designed to detect the probability of the driver's unfitness.

- *CollisionDetectionFuzzyInterfaceSystem:*
  - A FIS designed to detect the probability of a collision.

Rapid Rescue transcends being a mere application; it is a proactive safety companion for drivers, utilizing real-time data to elevate road safety and emergency responsiveness.

## For Detailed Information :
Please refer to the "REPORT.pdf" file in the repository.
For any issues or inquiries, contact Rushabh Jaiswal (rjaisw15@asu.edu) or VenkataSwaraj Kotapati (vkotapati@asu.edu). 
