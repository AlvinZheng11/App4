package com.example.app4

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import org.json.JSONObject
import java.net.URL

class WebFragment : Fragment() {
    companion object {
        private var instance : WebFragment? = null
        public fun getInstance() : WebFragment
        {
            return instance!!
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        instance = this
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_web, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // get arguments from SongFragment
        var arguments = this.arguments
        var song = arguments?.getString("song")
        var songCopy = song

        // set up data for JSON parsing
        if (song != null) {
            song = song.replace(" ", "+")
            //Set the artist
            var artist = "The Beatles"
            artist = artist.replace(" ","+")
            var origArtist = "The Beatles"
            //Encode search for YouTube
            val keywords = "$artist+$song"
            val max = 50

            //api key
            val string = "https://www.googleapis.com/youtube/v3/search?part=snippet&q=$keywords&order=viewCount&maxResults=$max&type=video&videoCategory=Music&key=AIzaSyC7vj06wB0IYc02TDL0EJtIoVfi5jP0k5w"

            var helper = songCopy?.let { Helper(string, origArtist, it) }
            var thread = Thread(helper)
            thread.start()
        }

    }
}

// Helper class for the thread
class Helper(private var url: String, private var artist: String, private var song: String) : Runnable
{

    override fun run() {
        val data = URL(url).readText()  //Read all the data at this URL

        var json = JSONObject(data)
        var items = json.getJSONArray("items")

        var titles = ArrayList<String>()
        var videos = ArrayList<String>()

        for (i in 0 until items.length()) {
            var videoObject = items.getJSONObject(i)
            var idDict = videoObject.getJSONObject("id")
            var videoId = idDict.getString("videoId")
            var snippetDict = videoObject.getJSONObject("snippet")
            var title =  snippetDict.getString("title")
            titles.add(title)
            videos.add(videoId)
        }

        //iterate through each title and find which video has both artist and song
        var selectedVideo : String = ""
        var selectedTitle : String = ""
        for (i in 0..< videos.size) {
            if (titles[i].contains(song) && titles[i].contains(artist) ) {
                selectedVideo = videos[i]
                selectedTitle = videos[i]
                break
            }
        }

        var helper1 = UIThreadHelper(selectedVideo)
        MainActivity.getInstance().runOnUiThread(helper1)
    }
}

// UI thread helper class, will update the webView with YouTube video obtained from JSON parsing
class UIThreadHelper(url: String) : Thread() {
    private var video : String = url

    override fun run() {
        //Update the webView
        val web = MainActivity.getInstance().findViewById<WebView>(R.id.webView)
        val settings = web.settings
        settings.javaScriptEnabled = true
        settings.domStorageEnabled = true
        settings.minimumFontSize = 10
        settings.loadWithOverviewMode = true
        settings.useWideViewPort = true
        settings.builtInZoomControls = true
        settings.displayZoomControls = false
        web.isVerticalScrollBarEnabled = false
        settings.domStorageEnabled = true
        web.webViewClient = WebViewClient()
        val str = "https://www.youtube.com/watch?v=$video"
        web.loadUrl(str)
    }
}