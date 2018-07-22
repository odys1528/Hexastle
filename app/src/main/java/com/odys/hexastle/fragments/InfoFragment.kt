package com.odys.hexastle.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.odys.hexastle.R
import kotlinx.android.synthetic.main.fragment_info.*

class InfoFragment : Fragment() {

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        infoTextView.movementMethod = ScrollingMovementMethod()

        for(i in resources.getStringArray(R.array.about_description)) {
            infoTextView.text = "${infoTextView.text}\n\n$i"
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_info, container, false)

    companion object {
        fun newInstance(): InfoFragment = InfoFragment()
    }
}
