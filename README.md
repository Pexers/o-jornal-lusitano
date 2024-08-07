# O Jornal Lusitano 📰
[![Get it on Google Play](https://play.google.com/intl/en_us/badges/images/badge_new.png)](https://play.google.com/store/apps/details?id=com.pexers.ojornallusitano)  

The app **O Jornal Lusitano** provides quick in-app access to popular Portuguese newspapers. This repository contains the app's source code written in Kotlin for Android. 

Despite being focused on Portuguese newspapers, it can be easily modified to support different international newspapers. 🌎 Changing the data file [_docs/index.json_](docs/index.json) will automatically update the newspapers displayed in the app. Changes get synchronized once every 24 hours using the repository's GitHub Pages API.

For more information regarding the _privacy policy_ and how to contact us, please visit our [_website_](https://sites.google.com/view/o-jornal-lusitano/home).

<p align="center">
  <img src="https://user-images.githubusercontent.com/47757441/204115466-74fd0b6c-c821-4846-8e00-e53d86a2991c.png" width="185">
</p>

## App screenshots 📱
|Access newspaper|Access newspaper (_dark mode_)|In-app webpage|Navigation drawer|
|:-------------------------:|:-------------------------:|:-------------------------:|:-------------------------: |
|<img src="https://user-images.githubusercontent.com/47757441/203153660-dfe3edd0-6dad-4d12-9697-19fe11339eed.jpg" width="170">|<img src="https://user-images.githubusercontent.com/47757441/203153847-b5e1b650-0ec1-4fa1-97f1-f1ab74be4d4f.jpg" width="170">|<img src="https://user-images.githubusercontent.com/47757441/203153706-a0bcb242-0435-400d-9246-6733b17b09bb.jpg" width="170">|<img src="https://user-images.githubusercontent.com/47757441/203153724-a95ad5cd-fea5-47c3-876b-beb59f09eb1d.jpg" width="170">

## Building the app from the command line 
1. Download Android's *[cmdline-tools](https://developer.android.com/studio#command-tools)*.
2. Install the required licenses.
    ```sh
    $ sdkmanager --licenses
    ```
3. Place the licenses within `cmdline-tools/tools/bin`.
4. Set the environment variable `ANDROID_HOME=cmdline-tools/tools/bin`.
5. Make sure to set the required `signingConfigs` properties within the `build.gradle` file.
6. Build the Android App Bundle `.aab` file.
    ```sh
    $ gradlew bundleRelease --warning-mode all
    ```
