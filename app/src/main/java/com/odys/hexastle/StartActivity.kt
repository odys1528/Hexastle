package com.odys.hexastle

import android.animation.Animator
import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_start.*

class StartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        splashAnimation()
    }

    fun splashAnimation() {
        val titleAnimatorSet = AnimatorInflater.loadAnimator(this, R.animator.vanish)
                as AnimatorSet
        titleAnimatorSet.setTarget(titleTextView)

        val imageAnimatorSet = AnimatorInflater.loadAnimator(this, R.animator.vanish)
                as AnimatorSet
        imageAnimatorSet.setTarget(splashImageView)

        val barAnimatorSet = AnimatorInflater.loadAnimator(this, R.animator.vanish)
                as AnimatorSet
        barAnimatorSet.setTarget(navbarLayout)
        barAnimatorSet.duration = 1500L

        val bothAnimatorSet = AnimatorSet()
        bothAnimatorSet.playTogether(titleAnimatorSet, imageAnimatorSet)
        bothAnimatorSet.duration = 1000L

        bothAnimatorSet.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(p0: Animator?) {
            }

            override fun onAnimationEnd(p0: Animator?) {
                Thread.sleep(2000)
                navbarLayout.visibility = View.VISIBLE
                barAnimatorSet.start()
            }

            override fun onAnimationCancel(p0: Animator?) {
            }

            override fun onAnimationStart(p0: Animator?) {
            }

        })

        bothAnimatorSet.start()
    }
}
