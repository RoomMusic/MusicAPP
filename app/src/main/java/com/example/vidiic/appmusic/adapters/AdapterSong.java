package com.example.vidiic.appmusic.adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vidiic.appmusic.R;
import com.example.vidiic.appmusic.classes.Song;

import java.util.List;

/**
 * Created by Vidiic on 17/03/2018.
 */

public class AdapterSong extends RecyclerView.Adapter<AdapterSong.ViewHolder> implements View.OnClickListener {

    private List<Song> songs;
    Context context;
    View.OnClickListener listener;

    public AdapterSong(List<Song> songs, Context context) {
        this.songs = songs;
        this.context = context;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView imagen;
        public TextView nombreSong;
        public TextView artistaSong;

        public ViewHolder(View view){
            super(view);
            imagen = view.findViewById(R.id.imageSong);
            nombreSong = view.findViewById(R.id.nameSong);
            artistaSong = view.findViewById(R.id.nameArtist);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView =
            LayoutInflater.from(parent.getContext())
                .inflate(R.layout.viewholder_songlist,parent,false);

        itemView.setOnClickListener(this);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Song song = songs.get(position);
        if (song.getImageSong() != null){
            holder.imagen.setImageBitmap(song.getImageSong());
        }else {
            holder.imagen.setImageResource(R.drawable.ic_action_music);
        }
        holder.nombreSong.setText(song.getName());
        holder.artistaSong.setText(song.getArtist());
        Log.i("Main","ViewHolder4");
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener=listener;
    }


    @Override
    public void onClick(View view) {
        if (listener!=null){
            listener.onClick(view);
        }
    }
    @Override
    public int getItemCount() {
        return songs.size();
    }
}
