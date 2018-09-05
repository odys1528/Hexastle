package com.odys.hexastle.fragments

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.content.res.ResourcesCompat
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
import com.odys.hexastle.utils.AppConstants.Companion.NEW_MODE
import com.odys.hexastle.utils.AppConstants.Companion.editor
import com.odys.hexastle.utils.AppConstants.Companion.playClickSound
import com.odys.hexastle.utils.TileDataLoader
import kotlinx.android.synthetic.main.fragment_game.*

class GameFragment : Fragment() {

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        startButton.setOnClickListener {
            playClickSound()
            if(tileCreatorLayout.alpha == 1f) {
                val intent = Intent(context, TileCreatorActivity::class.java)
                navbarAnimation(intent)
            } else if(worldCreatorButton.alpha == 1f) {
                val intent = Intent(context, WorldCreatorActivity::class.java)
                navbarAnimation(intent)
            }
        }

        disableButton(startButton)
        disableButton(resetGameButton)

        tileCreatorLayout.setOnClickListener {
            playClickSound()
            tileCreatorLayout.alpha = 1f
            tileBorderLayout.background = ResourcesCompat.getDrawable(resources, R.drawable.border, null)
            worldBorderLayout.setBackgroundResource(0)
            worldCreatorLayout.alpha = 0.5f
            enableButton(startButton)
            enableButton(resetGameButton)
        }

        worldCreatorLayout.setOnClickListener {
            playClickSound()
            worldCreatorLayout.alpha = 1f
            worldBorderLayout.background = ResourcesCompat.getDrawable(resources, R.drawable.border, null)
            tileBorderLayout.setBackgroundResource(0)
            tileCreatorLayout.alpha = 0.5f
            enableButton(startButton)
            enableButton(resetGameButton)
        }

        mainLayout.setOnClickListener {
            playClickSound()
            worldCreatorLayout.alpha = 1f
            tileCreatorLayout.alpha = 1f
            disableButton(startButton)
            disableButton(resetGameButton)
            tileBorderLayout.setBackgroundResource(0)
            worldBorderLayout.setBackgroundResource(0)
        }
    }

    private fun navbarAnimation(intent: Intent) {
        val navbarLayout = activity.findViewById<LinearLayout>(R.id.navbarLayout)
        val variantsLayout = activity.findViewById<LinearLayout>(R.id.variantsLayout)
        val resetButton = activity.findViewById<Button>(R.id.resetGameButton)

        val valueAnimator = ValueAnimator.ofFloat(1f, 0f)
        valueAnimator.addUpdateListener {
            val value = it.animatedValue as Float
            navbarLayout.alpha = value
            variantsLayout.alpha = value
            startButton.alpha = value
            resetButton.alpha = value
        }
        valueAnimator.interpolator = LinearInterpolator()
        valueAnimator.repeatMode = ValueAnimator.REVERSE
        valueAnimator.repeatCount = 1
        valueAnimator.duration = 1000L
        valueAnimator.start()

        valueAnimator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(p0: Animator?) {
                val dataLoader = TileDataLoader(context, intent)
                dataLoader.init()

                navbarLayout.visibility = View.INVISIBLE
                variantsLayout.visibility = View.INVISIBLE
                startButton.visibility = View.INVISIBLE
                resetButton.visibility = View.INVISIBLE
            }

            override fun onAnimationEnd(p0: Animator?) {
                navbarLayout.visibility = View.VISIBLE
                variantsLayout.visibility = View.VISIBLE
                startButton.visibility = View.VISIBLE
                resetButton.visibility = View.VISIBLE
            }

            override fun onAnimationCancel(p0: Animator?) {
            }

            override fun onAnimationStart(p0: Animator?) {
            }

        })
    }

    private fun enableButton(button: Button) {
        button.isEnabled = true
        button.setTextColor(Color.WHITE)
    }

    private fun disableButton(button: Button) {
        button.isEnabled = false
        button.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary))
    }

    private fun newOrSaved() {
        val alertDialog = AlertDialog.Builder(context)
        alertDialog.setPositiveButton(getString(R.string.new_ok)) { _, _ ->
            Toast.makeText(context, getString(R.string.last_game_lost), Toast.LENGTH_SHORT).show()
            editor.putString(GAME_STATE_TAG, AppConstants.Companion.GameState.NEW.name)
            editor.apply()
            GAME_STATE = AppConstants.Companion.GameState.NEW.name
            AppConstants.MODE = NEW_MODE
        }
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
