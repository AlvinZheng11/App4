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


class AlbumsFragment : Fragment() {
    private var albums = mutableListOf<Albums>()
    class Albums {
        private var title : String = ""
        private var produced : String = ""
        private var date : String = ""
        private var image : String = ""

        constructor(title : String, produced : String, date : String, image : String) {
            this.title = title
            this.produced = produced
            this.date = date
            this.image = image
        }

        public fun setTitle(title : String) {
            this.title = title
        }

        public fun getTitle() : String {
            return title
        }

        public fun setProduced(produced: String) {
            this.produced = produced
        }

        public fun getProduced() : String {
            return produced
        }

        public fun setDate(date : String) {
            this.date = date
        }

        public fun getDate() : String {
            return date
        }

        public fun setImage(image : String) {
            this.image = image
        }

        public fun getImage() : String {
            return image
        }

    }
    fun setAlbum(albums: MutableList<AlbumsFragment.Albums>) {
        this.albums = albums
    }

    fun getAlbum() : MutableList<AlbumsFragment.Albums>{
        return albums
    }
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<RecyclerAdapter.ViewHolder>? = null

    companion object {
        private var instance: AlbumsFragment? = null
        public fun getInstance(): AlbumsFragment {
            return instance!!
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        instance = this
        // read albums.txt so we can display albums in AlbumsFragment
        var is1 = MainActivity.getInstance().assets.open("albums.txt")
        var reader1 = BufferedReader(InputStreamReader(is1))
        var lines1 = reader1.readLines()
        var arrayLines1 = lines1.toTypedArray()
        var allData1 = arrayOf<Array<String>>()
        for (element in arrayLines1) {
            var array1 = element.split("^")
            allData1 += array1.toTypedArray()
        }
        var albums = mutableListOf<AlbumsFragment.Albums>()

        //for loop to check through all items in data
        for (i in allData1.indices) {
            //create new album item
            var album : AlbumsFragment.Albums = AlbumsFragment.Albums("", "", "", "")
            //check through index in item
            for (j in 0..<allData1[i].size) {
                // if index == 0 set title
                if (j == 0)
                    album.setTitle(allData1[i][j])
                // if index == 1 set producer
                if (j == 1)
                    album.setProduced(allData1[i][j])
                // if index == 0 set date
                if (j == 2)
                    album.setDate(allData1[i][j])
                // if index == 0 set image
                if (j == 3)
                    album.setImage(allData1[i][j])
            }
            //add albums item to array
            albums.add(album)
        }
        //set albums
        setAlbum(albums)
        //close reader
        reader1.close()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_albums, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        layoutManager = LinearLayoutManager(MainActivity.getInstance())

        var recyclerView = MainActivity.getInstance().findViewById<RecyclerView>(R.id.albumsView)

        recyclerView.layoutManager = layoutManager
        adapter = RecyclerAdapter()
        recyclerView.adapter = adapter
    }

}

