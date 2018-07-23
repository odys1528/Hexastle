package com.odys.hexastle.fragments

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.odys.hexastle.R
import com.odys.hexastle.utils.AppConstants
import com.odys.hexastle.utils.AppConstants.Companion.GAME_STATE
import com.odys.hexastle.utils.AppConstants.Companion.GAME_STATE_TAG
import com.odys.hexastle.utils.AppConstants.Companion.LOAD_MODE
import com.odys.hexastle.utils.AppConstants.Companion.NEW_MODE
import com.odys.hexastle.utils.AppConstants.Companion.editor
import kotlinx.android.synthetic.main.fragment_game.*

class GameFragment : Fragment() {

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkGameState()

        tileCreatorButton.setOnClickListener {
            Toast.makeText(context, getString(R.string.tile_creator_title), Toast.LENGTH_SHORT).show()
        }

        worldCreatorButton.setOnClickListener {
            Toast.makeText(context, getString(R.string.world_creator_title), Toast.LENGTH_SHORT).show()
        }

        disableButton(tileCreatorButton)
        disableButton(worldCreatorButton)
        checkGameState()

        loadGameButton.setOnClickListener {
            if(loadGameButton.isEnabled) {
                Toast.makeText(context, getString(R.string.last_game_load), Toast.LENGTH_SHORT).show()
                GAME_STATE = AppConstants.Companion.GameState.SAVED.name
                editor.putString(GAME_STATE_TAG, GAME_STATE)
                editor.apply()
                AppConstants.MODE = LOAD_MODE
                enableButton(tileCreatorButton)
                enableButton(worldCreatorButton)
            } else {
                Toast.makeText(context, getString(R.string.no_saved_game), Toast.LENGTH_SHORT).show()
            }
        }

        newGameButton.setOnClickListener {
            if (GAME_STATE == AppConstants.Companion.GameState.NEW.name){
                Toast.makeText(context, getString(R.string.new_game_created), Toast.LENGTH_SHORT).show()
                disableButton(loadGameButton)
                enableButton(tileCreatorButton)
                enableButton(worldCreatorButton)
            } else {
                newOrSaved()
            }
        }
    }

    private fun checkGameState() {
        if(GAME_STATE == AppConstants.Companion.GameState.SAVED.name) enableButton(loadGameButton)
        else disableButton(loadGameButton)
    }

    private fun enableButton(button: Button) {
        button.isEnabled = true
        button.setTextColor(Color.WHITE)
    }

    private fun disableButton(button: Button) {
        button.isEnabled = false
        button.setTextColor(resources.getColor(R.color.colorPrimary))
    }

    private fun newOrSaved() {
        val alertDialog = AlertDialog.Builder(context)
        alertDialog.setPositiveButton(getString(R.string.new_ok), { _, _ ->
            Toast.makeText(context, getString(R.string.last_game_lost), Toast.LENGTH_SHORT).show()
            editor.putString(GAME_STATE_TAG, AppConstants.Companion.GameState.NEW.name)
            editor.apply()
            GAME_STATE = AppConstants.Companion.GameState.NEW.name
            AppConstants.MODE = NEW_MODE
            enableButton(tileCreatorButton)
            enableButton(worldCreatorButton)
        })
        alertDialog.setNegativeButton(getString(R.string.cancel), null)
        alertDialog.setMessage(getString(R.string.override_question))
        alertDialog.setTitle(getString(R.string.game))
        alertDialog.show()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_game, container, false)

    companion object {
        fun newInstance(): GameFragment = GameFragment()
    }
}
