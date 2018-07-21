package com.odys.hexastle

import android.animation.Animator
import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_start.*

class StartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        this.onBackPressed()
        navigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        splashAnimation()
    }

    override fun onBackPressed() {
        openFragment(MainFragment.newInstance())
        val size = navigationView.menu.size() - 1
        for (i in 0..size){
            navigationView.menu.getItem(i).isChecked = false
        }
    }

    private fun splashAnimation() {
        navbarLayout.visibility = View.VISIBLE
        val barAnimatorSet = AnimatorInflater.loadAnimator(this, R.animator.vanish)
                as AnimatorSet
        barAnimatorSet.setTarget(navbarLayout)
        barAnimatorSet.duration = 2000L
        barAnimatorSet.start()
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener {
        item -> when (item.itemId) {
            R.id.navigation_new -> {
                Toast.makeText(applicationContext, "navigation new", Toast.LENGTH_SHORT).show()
                val gameFragment = GameFragment.newInstance()
                openFragment(gameFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_load -> {
                Toast.makeText(applicationContext, "navigation load", Toast.LENGTH_SHORT).show()
                val gameFragment = GameFragment.newInstance()
                openFragment(gameFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_info -> {
                Toast.makeText(applicationContext, "navigation info", Toast.LENGTH_SHORT).show()
                val infoFragment = InfoFragment.newInstance()
                openFragment(infoFragment)
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
