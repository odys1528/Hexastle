package com.odys.hexastle

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_start.*

class NavigationActivity : AppCompatActivity() {

    private var activeFragment = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        this.onBackPressed()
        navigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        splashAnimation()
    }

    override fun onBackPressed() {
        if (activeFragment == "MainFragment") {
            exit()
        } else {
            openFragment(MainFragment.newInstance())
            activeFragment = "MainFragment"
            navigationView.menu.getItem(0).isChecked = true
        }
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
                activeFragment = "GameFragment"
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_load -> {
                Toast.makeText(applicationContext, "navigation load", Toast.LENGTH_SHORT).show()
                val gameFragment = GameFragment.newInstance()
                openFragment(gameFragment)
                activeFragment = "GameFragment"
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_info -> {
                Toast.makeText(applicationContext, "navigation info", Toast.LENGTH_SHORT).show()
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
