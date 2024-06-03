package com.example.app4

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment

class HomeFragment : Fragment() {

    companion object {
        private var instance: HomeFragment? = null
        public fun getInstance(): HomeFragment {
            return instance!!
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        instance = this

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var imageView = view.findViewById<ImageView>(R.id.imageView)
        var files = arrayOf(R.drawable.pleasepleaseme, R.drawable.with_the_beatles, R.drawable.harddaysnight, R.drawable.beatlesforsale, R.drawable.help, R.drawable.rubber_soul, R.drawable.revolver, R.drawable.sgt_pepper, R.drawable.white, R.drawable.yellowsubmarine, R.drawable.abbeyroad, R.drawable.letitbe, R.drawable.magicalmysterytour, R.drawable.pastmastersvolume1, R.drawable.pastmastersvolume2, R.drawable.intro1)

        val handler = Handler()
        var index = 0
        handler.postDelayed(object : Runnable {
            override fun run() {
                imageView.setImageResource(files[index % files.size])
                handler.postDelayed(this, 3000)
                index++
            }
        }, 3000)
    }
}
