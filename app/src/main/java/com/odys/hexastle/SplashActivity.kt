package com.odys.hexastle

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.fragment_main.*
import android.content.Intent


class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        splashAnimation()
        startService(Intent(this, MusicService::class.java))

        val thread = object : Thread() {
            override fun run() {
                try {
                    Thread.sleep(3000)
                    val intent = Intent(applicationContext, NavigationActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                    startActivity(intent)
                    finish()
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }

        thread.start()
    }

    private fun splashAnimation() {
        val titleAnimatorSet = AnimatorInflater.loadAnimator(this, R.animator.vanish)
                as AnimatorSet
        titleAnimatorSet.setTarget(titleTextView)

        val imageAnimatorSet = AnimatorInflater.loadAnimator(this, R.animator.vanish)
                as AnimatorSet
        imageAnimatorSet.setTarget(splashImageView)

        val bothAnimatorSet = AnimatorSet()
        bothAnimatorSet.playTogether(titleAnimatorSet, imageAnimatorSet)
        bothAnimatorSet.duration = 1000L

        bothAnimatorSet.start()
    }

    override fun onBackPressed() {
    }
}
