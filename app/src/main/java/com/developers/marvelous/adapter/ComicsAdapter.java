package com.developers.marvelous.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.developers.marvelous.DetailComicActivity;
import com.developers.marvelous.Model.Comic;
import com.developers.marvelous.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Amanjeet Singh on 08-Jun-17.
 */

public class ComicsAdapter extends RecyclerView.Adapter<ComicsAdapter.ComicViewHolder> {

    List<Comic> comics;
    Context context;
    Comic comic_entity;

    public ComicsAdapter(Context context,List<Comic> comics){
        this.context=context;
        this.comics=comics;
    }

    @Override
    public ComicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.comic_row,parent,false);
        return new ComicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ComicViewHolder holder, final int position) {
        comic_entity=comics.get(position);
        holder.comic_title.setText(comic_entity.getTitle());
        Picasso.with(context).load(comic_entity.getThumbnail()).into(holder.comic_image);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, DetailComicActivity.class);
                intent.putExtra("comics",comics.get(position));
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return comics.size();
    }

    public class ComicViewHolder extends RecyclerView.ViewHolder{

        ImageView comic_image;
        TextView comic_title;
        CardView cardView;

        public ComicViewHolder(View itemView) {
            super(itemView);
            comic_image= (ImageView) itemView.findViewById(R.id.comic_img);
            comic_title= (TextView) itemView.findViewById(R.id.comic_title);
            cardView= (CardView) itemView.findViewById(R.id.comic_cards);
        }
    }
}
