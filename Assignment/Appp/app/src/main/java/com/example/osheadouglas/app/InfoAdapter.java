package com.example.osheadouglas.app;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by osheadouglas on 23/11/2016.
 */



public class InfoAdapter extends RecyclerView.Adapter<InfoAdapter.MyViewHolder> {



    private Context context;
    private List<MusicInformation> data;



    public InfoAdapter(Context c,List<MusicInformation> i) {
        this.context = c;
        this.data = i;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) { // RecyclerView.ViewHolder represent text and metadata

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card,parent,false); // Finds the XML card

        return new MyViewHolder(itemView);
    }



    // Now we nee dto launch an activity by selecting a card
    // and Launch

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
       holder.description.setText(data.get(position).getMusicDescription());  //  would be holder.description.setText(data.get(position).get description); if inside a class
        Picasso.with(context).load(data.get(position).getImageUrl()).into(holder.imageView); // Using the picasso api it binds the image


     //  holder.imageView.setImageBitmap(data.get(position).getImage());
    }


    @Override
    public int getItemCount() {
        return data.size(); // Returns the list size
    }


    class MyViewHolder extends RecyclerView.ViewHolder{ // Because RecyclerView.ViewHolder is abstarct and contains no implementation we need to do our own

        public View view;
        public TextView description;
        public ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            description = (TextView) itemView.findViewById(R.id.description);
            imageView = (ImageView) itemView.findViewById(R.id.cardImage);
            view = itemView;


        }


    }
}
