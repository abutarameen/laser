# Laser Music Player

This repository contains an Android application demonstrating a simple music player using SoundCloud as a source. The app allows searching for tracks, maintaining a playlist, and playing audio in the background using a foreground service.

## Features

- Search songs from the SoundCloud API.
- Maintain a playlist using a RecyclerView.
- Background playback powered by ExoPlayer.
- Basic settings screen that opens the SoundCloud login page.

To build the project use Gradle:

```bash
cd android-app
gradle assembleDebug
```

Make sure to provide a valid SoundCloud `client_id` in `MainActivity.kt`.
