package com.odys.hexastle.utils

import android.content.SharedPreferences

abstract class AppConstants {
    companion object {
        val GAME_SETTINGS_TAG = "HEXASTLE_DATA"
        val MUSIC_SETTINGS_TAG = "MUSIC"
        lateinit var settings : SharedPreferences
        lateinit var editor : SharedPreferences.Editor
        var MUSIC_TURNED_ON = true
    }
}