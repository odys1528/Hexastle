package com.odys.hexastle.fragments

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.res.ResourcesCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.odys.hexastle.R
import com.odys.hexastle.services.MusicService
import com.odys.hexastle.utils.AppConstants
import com.odys.hexastle.utils.AppConstants.Companion.MUSIC_TURNED_ON
import com.odys.hexastle.utils.AppConstants.Companion.SOUND_SETTINGS_TAG
import com.odys.hexastle.utils.AppConstants.Companion.SOUND_TURNED_ON
import com.odys.hexastle.utils.AppConstants.Companion.editor
import com.odys.hexastle.utils.AppConstants.Companion.playClickSound
import kotlinx.android.synthetic.main.fragment_main.*


class MainFragment : Fragment() {

    companion object {
        fun newInstance(): MainFragment = MainFragment()
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        soundImageAnimation()

        if(!MUSIC_TURNED_ON) {  // init color
            val color = ResourcesCompat.getColor(resources, R.color.colorPrimaryDark, null)
            musicImageView.setColorFilter(color)
        }

        if(!SOUND_TURNED_ON) {
            soundImageView.setImageResource(R.drawable.ic_sound_off)
        }

        musicImageView.setOnClickListener {
            playClickSound()
            if(MUSIC_TURNED_ON)  {
                val color = ResourcesCompat.getColor(resources, R.color.colorPrimaryDark, null)
                musicImageView.setColorFilter(color)
                MusicService.mute()
            } else {
                musicImageView.clearColorFilter()
                MusicService.amplify()
            }
            MUSIC_TURNED_ON = !MUSIC_TURNED_ON
            editor.putBoolean(AppConstants.MUSIC_SETTINGS_TAG, MUSIC_TURNED_ON)
            editor.apply()
        }

        soundImageView.setOnClickListener {
            if(SOUND_TURNED_ON) {
                soundImageView.setImageResource(R.drawable.ic_sound_off)
            } else {
                soundImageView.setImageResource(R.drawable.ic_sound_on)
            }
            SOUND_TURNED_ON = !SOUND_TURNED_ON
            playClickSound()
            editor.putBoolean(SOUND_SETTINGS_TAG, SOUND_TURNED_ON)
            editor.apply()
        }
    }

    private fun soundImageAnimation() {
        val soundImageAnimatorSet = AnimatorInflater.loadAnimator(context, R.animator.appear)
                as AnimatorSet
        soundImageAnimatorSet.setTarget(musicImageView)
        soundImageAnimatorSet.duration = 1000L
        soundImageAnimatorSet.start()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_main, container, false)

}
