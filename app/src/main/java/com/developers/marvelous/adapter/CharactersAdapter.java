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

import com.developers.marvelous.DetailActivity;
import com.developers.marvelous.Model.Character;
import com.developers.marvelous.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Amanjeet Singh on 08-Jun-17.
 */

public class CharactersAdapter extends RecyclerView.Adapter<CharactersAdapter.MyViewHolder> {

    Context context;
    List<Character> characters;

    public CharactersAdapter(Context context, List<Character> characters){
        this.context=context;
        this.characters=characters;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.list_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Character character=characters.get(position);
        holder.charecter_name.setText(character.getName());
        Picasso.with(context).load(character.getThumbnail()).into(holder.charecter_img);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, DetailActivity.class);
                intent.putExtra("character",character);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return characters.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView charecter_img;
        TextView charecter_name;
        CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);
            charecter_img= (ImageView) itemView.findViewById(R.id.img);
            charecter_name= (TextView) itemView.findViewById(R.id.name);
            cardView= (CardView) itemView.findViewById(R.id.cards);
        }

    }
}
