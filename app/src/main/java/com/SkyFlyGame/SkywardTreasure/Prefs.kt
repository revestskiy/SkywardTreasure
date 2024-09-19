package com.SkyFlyGame.SkywardTreasure

object Prefs {
    private lateinit var sharedPrefs: android.content.SharedPreferences

    fun init(context: android.content.Context) {
        sharedPrefs = context.getSharedPreferences("cloudgame", android.content.Context.MODE_PRIVATE)
    }

    fun isLevelAvailable(level: Int): Boolean {
        if (level == 1) return true
        return sharedPrefs.getBoolean("level${level - 1}", false)
    }

    fun levelPassed(level: Int) {
        sharedPrefs.edit().putBoolean("level$level", true).apply()
    }

    var musicVolume: Float
        get() = sharedPrefs.getFloat("musicVolume", 0.5f)
        set(value) = sharedPrefs.edit().putFloat("musicVolume", value).apply()
    var soundVolume: Float
        get() = sharedPrefs.getFloat("soundVolume", 0.5f)
        set(value) = sharedPrefs.edit().putFloat("soundVolume", value).apply()
}