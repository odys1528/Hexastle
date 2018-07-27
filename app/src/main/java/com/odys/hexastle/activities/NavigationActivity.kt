package com.odys.hexastle.activities

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.odys.hexastle.R
import com.odys.hexastle.fragments.GameFragment
import com.odys.hexastle.fragments.InfoFragment
import com.odys.hexastle.fragments.MainFragment
import com.odys.hexastle.services.MusicService
import com.odys.hexastle.utils.AppConstants
import com.odys.hexastle.utils.AppConstants.Companion.GAME_STATE
import com.odys.hexastle.utils.AppConstants.Companion.GAME_STATE_TAG
import com.odys.hexastle.utils.AppConstants.Companion.MUSIC_TURNED_ON
import com.odys.hexastle.utils.AppConstants.Companion.SOUND_SETTINGS_TAG
import com.odys.hexastle.utils.AppConstants.Companion.SOUND_TURNED_ON
import com.odys.hexastle.utils.AppConstants.Companion.clickSound
import com.odys.hexastle.utils.AppConstants.Companion.editor
import com.odys.hexastle.utils.AppConstants.Companion.playClickSound
import com.odys.hexastle.utils.AppConstants.Companion.settings
import kotlinx.android.synthetic.main.activity_start.*

class NavigationActivity : AppCompatActivity() {

    private var activeFragment = ""

    @SuppressLint("CommitPrefEdits")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        MusicService.start()

        this.onBackPressed()
        navigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        splashAnimation()

        settings = getSharedPreferences(AppConstants.GAME_SETTINGS_TAG, Context.MODE_PRIVATE)
        AppConstants.editor = settings.edit()

        MUSIC_TURNED_ON = settings.getBoolean(AppConstants.MUSIC_SETTINGS_TAG, true)
        if(!MUSIC_TURNED_ON) MusicService.mute()

        SOUND_TURNED_ON = settings.getBoolean(SOUND_SETTINGS_TAG, true)

        clickSound = MediaPlayer.create(this, R.raw.click)

        GAME_STATE = settings.getString(GAME_STATE_TAG, AppConstants.Companion.GameState.NEW.name)
    }

    override fun onBackPressed() {
        playClickSound()
        when (activeFragment) {
            "MainFragment" -> exit()
            else -> backToMain()
        }
    }

    private fun backToMain() {
        openFragment(MainFragment.newInstance())
        activeFragment = "MainFragment"
        navigationView.menu.getItem(0).isEnabled= true
        navigationView.menu.getItem(0).isChecked = true
    }

    private fun leaveGame() {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setPositiveButton(getString(R.string.leave_game)) { _, _ -> saveGame() }
        alertDialog.setNegativeButton(getString(R.string.stay_in), null)
        alertDialog.setMessage(getString(R.string.leave_question))
        alertDialog.setTitle(getString(R.string.game))
        alertDialog.show()
    }

    private fun saveGame() {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setPositiveButton(getString(R.string.save_game)) { _, _ ->
            editor.putString(GAME_STATE_TAG, AppConstants.Companion.GameState.SAVED.name)
            editor.apply()
            GAME_STATE = AppConstants.Companion.GameState.SAVED.name
            backToMain()
            Toast.makeText(this, "Game is saved", Toast.LENGTH_SHORT).show()
        }
        alertDialog.setNegativeButton(getString(R.string.dont_save)) { _, _ ->
            Toast.makeText(this, "Changes discarded", Toast.LENGTH_SHORT).show()
            backToMain()
        }
        alertDialog.setMessage(getString(R.string.save_question))
        alertDialog.setTitle(getString(R.string.game))
        alertDialog.show()
    }

    private fun exit() {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setPositiveButton(getString(R.string.close_app)) { _, _ -> finish() }
        alertDialog.setNegativeButton(getString(R.string.stay_in_app), null)
        alertDialog.setMessage(getString(R.string.exit_question))
        alertDialog.setTitle(getString(R.string.app_name))
        alertDialog.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        stopService(Intent(this, MusicService::class.java))
    }

    private fun splashAnimation() {
        navbarLayout.visibility = View.VISIBLE
        val barAnimatorSet = AnimatorInflater.loadAnimator(this, R.animator.appear)
                as AnimatorSet
        barAnimatorSet.setTarget(navbarLayout)
        barAnimatorSet.duration = 1000L
        barAnimatorSet.start()
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener {
        item -> when (item.itemId) {
        R.id.navigation_start -> {
            playClickSound()
            val gameFragment = GameFragment.newInstance()
            openFragment(gameFragment)
            activeFragment = "GameFragment"
            return@OnNavigationItemSelectedListener true
        }

        R.id.navigation_info -> {
            playClickSound()
            val infoFragment = InfoFragment.newInstance()
            openFragment(infoFragment)
            activeFragment = "InfoFragment"
            return@OnNavigationItemSelectedListener true
        }
    }
        false
    }

    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainer, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

}
