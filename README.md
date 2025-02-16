# TVSniffer

[![License](https://img.shields.io/badge/License-MIT-blue.svg)](https://opensource.org/licenses/MIT)

TVSniffer is an Android library that helps you determine if a device is "TV-like" (such as an Android TV or Google TV) as opposed to a traditional mobile device (phone or tablet). It provides a way to determine, with a degree of confidence, whether the device is an Android TV, Google TV, or another TV-like device. This library is particularly useful for apps that need to adjust their behavior or UI based on the type of device they are running on.

## Problem

Determining if an Android device is a TV device can be challenging. There's no single, definitive "isTV" flag you can check. TVSniffer solves this by using a combination of checks and a scoring system to provide a confidence level. It allows you to be confident in your analysis.

## Installation

To use TVSniffer, add the following to your app's `build.gradle.kts` (or `build.gradle`) file:
```
dependencies {
    implementation("com.devynel:tvsniffer:1.0.0")
}
```

**Note:** We will set up JitPack later so that you can use this.

## Usage

Here's a simple example of how to use `TVSniffer`:
```
import com.devynel.tvsniffer.TVSniffer
import android.content.Context
// ... in your Activity or other class ...
fun isThisATv(context: Context) {
    val result = TVSniffer.isTvDevice(context)
    if (result.tvThresholdMet) {
        // It's very likely a TV
        println("This is a TV device.")
    } else if (result.tvMaybe) {
        // It could be a TV
        println("This might be a TV device.")
    } else {
        // It's probably not a TV
        println("This is likely not a TV device.")
    }
    println("TV Score is ${result.tvScore}")
}
```

### The `isTvDevice()` Method

The core of `TVSniffer` is the `isTvDevice()` method. Here is its signature:
```
fun isTvDevice(
    context: Context,
    tvThresholdNeeded: Double = DEFAULT_THRESHOLD,
    tvUiModeScore: Int = 2,
    noAccelerometerScore: Int = 4,
    leanbackKeyFileScore: Int = 5,
    leanbackLibScore: Int = 5,
    leanbackLauncherScore: Int = 1,
    tvSettingsScore: Int = 2,
    tvRecommendationScore: Int = 1,
    tvPartnerCustomizerScore: Int = 1
): TVSnifferResult
```

*   **`context`:** The application context.
*   **`tvThresholdNeeded`:** The threshold score that needs to be met to consider the device a TV. The default is `8.0`.
*   **`tvUiModeScore`:** The weight given to the UI mode check. The default is `2`.
*   **`noAccelerometerScore`:** The weight given to the check for an accelerometer. The default is `4`.
*   **`leanbackKeyFileScore`:** The weight given to the check for a Leanback keyboard file. The default is `5`.
*   **`leanbackLibScore`:** The weight given to the check for the Leanback library. The default is `5`.
*   **`leanbackLauncherScore`:** The weight given to the check for the Leanback launcher. The default is `1`.
*   **`tvSettingsScore`:** The weight given to the check for TV settings. The default is `2`.
*   **`tvRecommendationScore`:** The weight given to the check for a TV recommendation service. The default is `1`.
*   **`tvPartnerCustomizerScore`:** The weight given to the check for a TV partner customizer. The default is `1`.

  ### Customizing the Weights

You can adjust the weights of the checks to fine-tune the detection for your specific needs. For example, if you want to give more importance to the accelerometer check, you can pass a higher value to `noAccelerometerScore`.

```
noAccelerometerScore: Int = 6, //Higher Value Assigned
```

You can also assign No Weight to a test, effectively removing it from the calulation. For example, to remove a score for a Leanback Launcher:
```
leanbackLauncherScore: Int = 0,
```

### Understanding `TVSnifferResult`

The `isTvDevice()` method returns a `TVSnifferResult` object, which has three properties:

*   **`tvThresholdMet`:** `true` if the confidence score is above the threshold.
*   **`tvScore`:** The total confidence score.
*   **`tvMaybe`:** `true` if the score is close to the threshold (within a small proximity of 0.5 under Threshold).

  ### How It Works

TVSniffer uses a combination of checks to determine if a device is a TV. These checks include:

*   **UI Mode:** Checks if the device is in TV mode. **Why?** TV devices are almost always in TV mode.
*   **Accelerometer:** Checks if the device has an accelerometer. **Why?** TV devices rarely have accelerometers.
*   **Leanback Keyboard File:** Checks for the existence of a specific keyboard layout file used by Leanback. **Why?** Leanback is a TV-centric UI.
*   **Leanback Library:** Checks for the presence of the `androidx.leanback` library. **Why?** This library is used for TV UIs.
*   **TV Settings:** Checks for the presence of settings typically found on Android TV devices. **Why?** These settings are unique to TVs.
*   **Leanback Launcher:** Checks if a Leanback launcher is set as the default launcher. **Why?** Leanback launchers are common on TVs.
*   **TV Recommendation Service:** Checks for the presence of a TV recommendation service. **Why?** This is a TV-specific feature.
*   **TV Partner Customizer:** Checks for the presence of the TV Partner Customizer package. **Why?** This is another TV-specific feature.

Each check contributes to a confidence score. You can configure the weights of these checks to fine-tune the detection for your specific needs.

## License

TVSniffer is released under the [MIT License](LICENSE).

## Contact

If you have any questions or want to contribute to this project, please contact me via email at david@devynel.co.uk.

## Data Structure

The `Data Structure` will be a simple array of `TVSnifferResult` objects. We will come back to this later.
