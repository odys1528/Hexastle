package com.odys.hexastle.utils

import android.content.SharedPreferences

abstract class AppConstants {
    companion object {
        val GAME_SETTINGS_TAG = "HEXASTLE_DATA"
        lateinit var settings : SharedPreferences
        lateinit var editor : SharedPreferences.Editor

        val MUSIC_SETTINGS_TAG = "MUSIC"
        var MUSIC_TURNED_ON = true

        var GAME_STATE = GameState.NEW.name
        var GAME_STATE_TAG = "GAME_STATE"
        val LOAD_MODE = "load"
        val NEW_MODE = "new"
        var MODE = NEW_MODE

        enum class GameState {
            NEW, //when new game is created
            SAVED //when there is already saved game
        }
    }
}