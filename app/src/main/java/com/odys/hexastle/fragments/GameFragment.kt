package com.odys.hexastle.fragments

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import com.odys.hexastle.R
import com.odys.hexastle.activities.TileCreatorActivity
import com.odys.hexastle.activities.WorldCreatorActivity
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
            val intent = Intent(context, TileCreatorActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            navbarAnimation(intent)
        }

        worldCreatorButton.setOnClickListener {
            val intent = Intent(context, WorldCreatorActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            navbarAnimation(intent)
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

    private fun navbarAnimation(intent: Intent) {
        val navbarLayout = activity.findViewById<LinearLayout>(R.id.navbarLayout)
        val valueAnimator = ValueAnimator.ofFloat(1f, 0f)
        valueAnimator.addUpdateListener {
            val value = it.animatedValue as Float
            navbarLayout.alpha = value
        }
        valueAnimator.interpolator = LinearInterpolator()
        valueAnimator.duration = 1000L
        valueAnimator.start()

        valueAnimator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(p0: Animator?) {
            }

            override fun onAnimationEnd(p0: Animator?) {
                startActivity(intent)
            }

            override fun onAnimationCancel(p0: Animator?) {
            }

            override fun onAnimationStart(p0: Animator?) {
            }

        })
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
