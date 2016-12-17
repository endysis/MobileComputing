package com.example.osheadouglas.app;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.res.XmlResourceParser;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.xmlpull.v1.XmlPullParser;

import java.io.ByteArrayOutputStream;
import java.util.List;

import static android.view.View.INVISIBLE;

/**
 * Created by osheadouglas on 23/11/2016.
 */



public class InfoAdapter extends RecyclerView.Adapter<InfoAdapter.MyViewHolder> {

    private Context context;
    private List<MusicInformation> data;
    private boolean moodBand;
    private String location;

    public InfoAdapter(Context c,List<MusicInformation> i, boolean mB,String loc) {
        this.context = c;
        this.data = i;
        this.moodBand = mB;
        this.location = loc;
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
        holder.urlText.setText(data.get(position).getImageUrl()); // This is the url of the image to send to the next view
        Picasso.with(context).load(data.get(position).getImageUrl()).into(holder.imageView); // Using the picasso api it binds the image
        //holder.urlText.setVisibility(INVISIBLE);

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
        public Button urlText;
        public Button cButton;
        private String inspireText;
        private String imageUrl;


        public MyViewHolder(View itemView) {
            super(itemView);
            description = (TextView) itemView.findViewById(R.id.description);
            imageView = (ImageView) itemView.findViewById(R.id.cardImage);
            urlText = (Button) itemView.findViewById(R.id.urlButton);

            cButton = (Button) itemView.findViewById(R.id.cardButton);
            view = itemView;

            cButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    inspireText = description.getText().toString();
                    location = "Recorded in " + location; // Add the location part
                    imageUrl = urlText.getText().toString();
                    Intent i = new Intent(v.getContext(),InspireDecide.class);
                    i.putExtra("inspText",inspireText);
                    i.putExtra("image",imageUrl);
                    i.putExtra("location",location);
                    i.putExtra("iM",moodBand);
                    context.startActivity(i);
                    //Log.i("TAG","d + "+ description.getText());
                }
            });
        }
    }
}




















