package com.odys.hexastle.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.odys.hexastle.R
import com.odys.hexastle.utils.AppConstants
import com.odys.hexastle.utils.AppConstants.Companion.GAME_STATE_TAG
import com.odys.hexastle.utils.AppConstants.Companion.LOAD_MODE
import com.odys.hexastle.utils.AppConstants.Companion.NEW_MODE
import com.odys.hexastle.utils.AppConstants.Companion.editor

class GameFragment : Fragment() {

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkGameState()
    }

    private fun checkGameState() {
        if (mode == NEW_MODE) {
            Toast.makeText(context, "New game is created", Toast.LENGTH_SHORT).show()
            editor.putString(GAME_STATE_TAG, AppConstants.Companion.GameState.NEW.name)
            editor.apply()
        } else if (mode == LOAD_MODE) {
            Toast.makeText(context, "Loaded last game", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_game, container, false)

    companion object {
        private lateinit var mode : String

        fun newInstance(mode : String): GameFragment {
            this.mode = mode
            return GameFragment()
        }
    }
}
