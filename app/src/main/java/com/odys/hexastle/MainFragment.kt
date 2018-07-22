package com.odys.hexastle

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.media.MediaPlayer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.res.ResourcesCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_main.*


class MainFragment : Fragment() {

    companion object {
        fun newInstance(): MainFragment = MainFragment()
        var MUSIC = true
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        soundImageAnimation()

        soundImageView.setOnClickListener {
            if(MUSIC)  {
                val color = ResourcesCompat.getColor(resources, R.color.colorPrimaryDark, null)
                soundImageView.setColorFilter(color)
                MusicService.mute()
            }
            else  {
                soundImageView.clearColorFilter()
                MusicService.amplify()
            }
            MUSIC = !MUSIC
        }
    }

    private fun soundImageAnimation() {
        val soundImageAnimatorSet = AnimatorInflater.loadAnimator(context, R.animator.vanish)
                as AnimatorSet
        soundImageAnimatorSet.setTarget(soundImageView)
        soundImageAnimatorSet.duration = 1000L
        soundImageAnimatorSet.start()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_main, container, false)

}
