# TVSniffer: Fine-Grained Android TV Detection (No Permissions Required!)

Let's rewind a bit... I recently started developing my first Android app, a radio player for my station ([your station URL or app name]), and everything was going smoothly until I hit a wall:  Android TV detection.  Existing methods were unreliable, inconsistent, and frankly, a pain to work with.  I needed a way to accurately detect TVs without compromising performance or user privacy.  That's how TVSniffer was born.

TVSniffer is designed to be run once at app startup and then forgotten.  It provides you with a risk level, which you can then use to make decisions about how to tailor your app's behavior.  No need for continuous checks or ongoing resource consumption.

## The Problem: Android TV Detection - A Developer's Nightmare

Existing methods rely on things like screen resolution or UI components, which can be easily spoofed or simply inaccurate.  

* Screen Resolution:  What about tablets with high-resolution screens or phones like the Sony Xperia with 4K displays?  They'll get incorrectly identified as TVs.
* UI Sniffing:  But what if a user replaces the default Leanback launcher with a different one?  Or removes it altogether, like many power users do?  That's a false negative.

I needed a more reliable solution, something that worked at the system level without requiring any permissions.  That's when I started thinking about what really makes a device a TV.

## The Solution:  Thinking Like NORAD (and the Commodore 64)

Instead of directly detecting Leanback Keyboard (which would require permissions), I looked for its "exhaust fumes" – the residual files it leaves behind.  Just like NORAD uses exhaust patterns to differentiate between aircraft and missiles, TVSniffer uses these files to identify TVs.  No need to read the files, just check if they exist.

And to avoid needing permissions to check for hardware, I took inspiration from my Commodore 64 days.  Instead of "POKE"-ing values, I simply "PEEK" to see if certain sensors exist.  In this case, the accelerometer.  No TV has one, but almost every phone or tablet does.

Combine these two checks – accelerometer presence and Leanback Keyboard "exhaust" – and you have a highly accurate, permission-free way to detect Android TVs.

## Risk-Level Control: Your Call

But simply detecting a TV wasn't enough.  I wanted to give developers more control.  So I borrowed a concept from spam scoring.  TVSniffer analyzes various device characteristics and assigns a "risk level" – how likely it is that the device is a TV.  You set the threshold.  Need high accuracy?  Set a high threshold.  Want to cast a wider net?  Lower the threshold.  It's your call.

This gives you the flexibility to tailor the detection to your specific needs.  A bank app might need a very high threshold (10.0!), while a casual game might be fine with a lower one (6.0).  You're in control.

## No Permissions Needed! (Yes, Really!) - Leave No Trace

One of the core principles behind TVSniffer is "Leave No Trace" – a throwback to my 80s script kiddie days.  In this context, it means minimizing the impact on the user's device and respecting their privacy.  That's why TVSniffer requires absolutely no permissions.  It doesn't write to storage or ask for any unnecessary access.  It simply does its job and gets out of the way.

This not only simplifies integration for developers but also provides peace of mind to users.  They can be confident that TVSniffer isn't accessing any sensitive data or making any unwanted changes to their device.

## The Beauty of Minimalism

TVSniffer's "Leave No Trace" philosophy extends beyond just permissions.  It's about keeping things lightweight and efficient.  No unnecessary dependencies, no bloated code.  Just a focused solution that does one thing well – and does it quickly.

Run Once, Forget About It:  TVSniffer is designed to be run once at app startup.  It calculates the risk level and provides you with a constant value.  You can then use this value throughout your app to make decisions about how to handle different device types.  No need for repeated checks or ongoing calculations, which can drain battery life and impact performance.

This minimalist approach has several benefits:

* Improved Performance:  TVSniffer won't slow down your app or consume excessive resources.
* Reduced Footprint:  It won't take up unnecessary space on the user's device.
* Enhanced Security:  By minimizing its access and impact, TVSniffer reduces the potential attack surface.
* Efficient Battery Usage:  Running only once minimizes battery drain.

This aligns with modern development best practices, where efficiency, security, and user privacy are paramount.

## Key Features and Benefits

* Fine-Grained Risk Control:  You're in charge of the detection sensitivity.
* Improved Accuracy:  Minimize those annoying false positives and negatives.
* Easy Integration:  Just add the dependency and you're ready.
* Lightweight:  Won't bloat your app.
* No Permissions Required!:  Privacy-friendly and hassle-free.
* Open Source (MIT License):  Free to use and modify.

## Installation

Add this to your module's build.gradle file:
```
dependencies {
    implementation 'com.your_group_id:tvsniffer:your_version' // Replace with your actual coordinates
}
```
## Usage Example
```
// Run this once at app startup
val tvSniffer = TVSniffer(context)
val riskLevel = tvSniffer.getRiskLevel()

// Example usage in a radio app
when (riskLevel) {
    TVSniffer.RiskLevel.LOW -> {
        // Display phone UI with smaller buttons and a compact layout
    }
    TVSniffer.RiskLevel.MEDIUM, TVSniffer.RiskLevel.HIGH -> {
        // Display TV UI with larger buttons and a more spacious layout
    }
}

// Example of setting custom risk thresholds (Advanced)
tvSniffer.setCustomThresholds(lowRiskThreshold = 0.2, mediumRiskThreshold = 0.5)

// Example of checking if device is considered a TV, based on the current threshold
if (tvSniffer.isTv()) {
    // Device is considered a TV
}
```
## API Documentation

| Method/Class        | Description                                       |
|---------------------|---------------------------------------------------|
| TVSniffer(context) | Constructor for the TVSniffer class.              |
| getRiskLevel()    | Returns the risk level of the device.            |
| setCustomThresholds(low, medium) | Sets custom thresholds for risk levels. |
| isTv()            | Returns true if the device is considered a TV based on current threshold. |
| RiskLevel        | Enum class defining the different risk levels.  |


## Comparison to Existing Methods

| Feature           | TVSniffer              | Other Methods           |
|-------------------|-----------------------|------------------------|
| Risk-Level Control | Yes                   | No                     |
| Accuracy          | Improved              | Often Inconsistent     |
| Flexibility       | High                  | Limited                 |
| Permissions Required | No                    | Often Yes              |

## Sharing the Solution

I built TVSniffer to solve a real problem I was facing.  And I'm sure I'm not the only one.  That's why I'm sharing it with the community – completely permission-free and open source.  I hope it helps other developers avoid the headaches I went through.

## Contributing

Contributions are welcome!  If you'd like to contribute to TVSniffer, please keep the "Leave No Trace" philosophy in mind.  Focus on minimizing the library's footprint, respecting user privacy, and maintaining its lightweight design. 
