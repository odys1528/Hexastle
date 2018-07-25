package com.odys.hexastle.utils

import android.content.SharedPreferences
import android.media.MediaPlayer

abstract class AppConstants {
    companion object {
        const val GAME_SETTINGS_TAG = "HEXASTLE_DATA"
        lateinit var settings : SharedPreferences
        lateinit var editor : SharedPreferences.Editor

        const val MUSIC_SETTINGS_TAG = "MUSIC"
        var MUSIC_TURNED_ON = true

        const val SOUND_SETTINGS_TAG = "SOUND"//TODO
        var SOUND_TURNED_ON = true//TODO

        var GAME_STATE = GameState.NEW.name
        var GAME_STATE_TAG = "GAME_STATE"
        const val LOAD_MODE = "load"
        const val NEW_MODE = "new"
        var MODE = NEW_MODE

        enum class GameState {
            NEW, //when new game is created
            SAVED //when there is already saved game
        }

        var clickSound : MediaPlayer = MediaPlayer() //TODO

        fun playClickSound() { //TODO
            if(SOUND_TURNED_ON) clickSound.start()
        }
    }
}