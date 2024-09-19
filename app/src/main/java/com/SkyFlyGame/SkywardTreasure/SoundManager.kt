package com.SkyFlyGame.SkywardTreasure

import android.content.Context
import android.media.MediaPlayer

object SoundManager {
    private lateinit var musicPlayer: MediaPlayer
    private lateinit var soundPlayer: MediaPlayer

    fun init(context: Context) = runCatching {
        musicPlayer = MediaPlayer.create(context, R.raw.music)
        soundPlayer = MediaPlayer.create(context, R.raw.sound)
    }

    fun pauseMusic() = runCatching {
        musicPlayer.pause()
        soundPlayer.pause()
    }

    fun resumeMusic() = runCatching {
        if (!musicPlayer.isPlaying) {
            musicPlayer.start()
            musicPlayer.isLooping = true
        }
    }

    fun onDestroy() = runCatching {
        musicPlayer.release()
        soundPlayer.release()
    }


    fun playSound() = runCatching {
        soundPlayer.setVolume(Prefs.soundVolume, Prefs.soundVolume)
        soundPlayer.start()
        soundPlayer.isLooping = false
    }

    fun setSoundVolume() {
        soundPlayer.setVolume(Prefs.soundVolume, Prefs.soundVolume)
    }

    fun setMusicVolume() {
        musicPlayer.setVolume(Prefs.musicVolume, Prefs.musicVolume)
    }
}