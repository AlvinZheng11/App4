package com.example.app4

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.BufferedReader
import java.io.InputStreamReader

class SongFragment : Fragment() {
    class Songs {
        private var title : String = ""
        private var written : String = ""

        constructor(title: String, written: String) {
            this.title = title
            this.written = written
        }

        public fun setTitle(title : String) {
            this.title = title
        }

        public fun getTitle() : String {
            return title
        }

        public fun setWritten(written : String) {
            this.written = written
        }

        public fun getWritten() : String {
            return written
        }
    }
    private var songs = mutableListOf<Songs>()
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<RecyclerSongs.ViewHolder>? = null

    companion object {
        private var instance: SongFragment? = null
        public fun getInstance(): SongFragment {
            return instance!!
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        instance = this

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_song, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //gets info from recycler
        var arguments = this.arguments
        var pos = arguments?.getInt("position")
        var album = arguments?.getString("album") + ".txt"

        //reads info from album
        var is1 = MainActivity.getInstance().assets.open(album)
        var reader1 = BufferedReader(InputStreamReader(is1))
        var lines1 = reader1.readLines()
        var arrayLines1 = lines1.toTypedArray()
        var allData1 = arrayOf<Array<String>>()
        for (element in arrayLines1) {
            var array1 = element.split("^")
            allData1 += array1.toTypedArray()
        }
        var songs = mutableListOf<Songs>()
        //for loop to check through all items in data
        for (i in allData1.indices) {
            //create new song item
            var song : Songs = Songs("", "")
            //check through index in item
            for (j in 0 until allData1[i].size) {
                // if index == 0 set title
                if (j == 0)
                    song.setTitle(allData1[i][j])
                // if index == 1 set writer
                if (j == 1)
                    song.setWritten(allData1[i][j])
            }
            //add song to song array
            songs.add(song)
        }
        //set song
        setSongs(songs)
        reader1.close()


        var recyclerView2 = MainActivity.getInstance().findViewById<RecyclerView>(R.id.songView)

        layoutManager = LinearLayoutManager(MainActivity.getInstance())
        recyclerView2.layoutManager = layoutManager
        adapter = RecyclerSongs(pos!!)
        recyclerView2.adapter = adapter
    }

    fun setSongs(songs: MutableList<Songs>) {
        this.songs = songs
    }

    fun getSongs() : MutableList<Songs>{
        return songs
    }
}