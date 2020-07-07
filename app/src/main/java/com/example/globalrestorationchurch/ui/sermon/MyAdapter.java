package com.example.globalrestorationchurch.ui.sermon;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.globalrestorationchurch.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private ArrayList<SermonDetails> mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public CardView root;
        public ImageView thumbnailIv;
        public TextView titleTv;
        public TextView descriptionTv;
        public TextView creationTV;

        public MyViewHolder(CardView root, ImageView thumbnailIv, TextView titleTv, TextView descriptionTv, TextView creationTV) {
            super(root);
            this.root = root;
            this.thumbnailIv = thumbnailIv;
            this.titleTv = titleTv;
            this.descriptionTv = descriptionTv;
            this.creationTV = creationTV;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(ArrayList<SermonDetails> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
//        TextView v = (TextView) LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.title_text, parent, false);
        CardView v = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sermon_view, parent, false);

        ImageView thumbnailIv = v.findViewById(R.id.thumbnail_image);
        TextView titleTv = v.findViewById(R.id.title_text);
        TextView descriptionTv = v.findViewById(R.id.description_text);
        TextView creationTV = v.findViewById(R.id.creation_text);

        MyViewHolder vh = new MyViewHolder(v, thumbnailIv, titleTv, descriptionTv, creationTV);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

//        holder.thumbnailIv.setText(mDataset.get(position).title);
        Picasso.get().load(mDataset.get(position).thumbnailUrl).centerInside().fit().into(holder.thumbnailIv);
        holder.titleTv.setText(mDataset.get(position).title);
        holder.descriptionTv.setText(mDataset.get(position).description);
        holder.creationTV.setText(mDataset.get(position).published);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}

