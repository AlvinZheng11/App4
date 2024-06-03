package com.example.app4

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>()
{
    private var albums = AlbumsFragment.getInstance().getAlbum()

    override fun getItemCount(): Int
    {
        return albums.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_layout, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        holder.itemTitle.text = albums[position].getTitle()
        holder.itemDetail.text = albums[position].getDate()
        holder.itemImage.setImageResource(MainActivity.getInstance().resources.getIdentifier(albums[position].getImage(),"drawable",
            MainActivity.getInstance().packageName))
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        var itemImage: ImageView
        var itemTitle: TextView
        var itemDetail: TextView

        init
        {
            itemImage = itemView.findViewById(R.id.imageView)
            itemTitle = itemView.findViewById(R.id.name)
            itemDetail = itemView.findViewById(R.id.title)
            var handler = Handler()
            itemView.setOnClickListener(handler)

        }
        inner class Handler() : View.OnClickListener
        {
            override fun onClick(v: View?)
            {
                val itemPosition = layoutPosition
                //Get the navigation controller
                var navController = Navigation.findNavController(AlbumsFragment.getInstance().requireView()!!)
                val bundle = Bundle()
                bundle.putInt("position", itemPosition)
                bundle.putString("album", albums[itemPosition].getImage())
                navController.navigate(R.id.albums_to_song,bundle)
            }
        }

    }
}
