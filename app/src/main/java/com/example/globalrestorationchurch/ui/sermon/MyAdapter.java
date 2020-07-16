package com.example.globalrestorationchurch.ui.sermon;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.globalrestorationchurch.R;
import com.squareup.picasso.Picasso;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private final SermonDetails[] mDataset;
    private final Fragment fragment;

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(SermonDetails[] myDataset, Fragment fragment) {
        mDataset = myDataset;
        this.fragment = fragment;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sermon_view, parent, false);

        ImageView thumbnailIv = cardView.findViewById(R.id.thumbnail_image);
        TextView titleTv = cardView.findViewById(R.id.title_text);
        TextView descriptionTv = cardView.findViewById(R.id.description_text);
        TextView creationTV = cardView.findViewById(R.id.creation_text);

        return new MyViewHolder(cardView, thumbnailIv, titleTv, descriptionTv, creationTV);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Picasso.get().load(mDataset[position].thumbnailUrl)
                .centerInside()
                .noFade()
                .config(Bitmap.Config.RGB_565)
                .fit()
                .into(holder.thumbnailIv);
        holder.titleTv.setText(mDataset[position].title);
        holder.descriptionTv.setText(mDataset[position].description);
        holder.creationTV.setText(mDataset[position].published);

        holder.itemView.setOnClickListener(view -> fragment.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(mDataset[position] + SermonDetails.YOUTUBELINK))));
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length;
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public final ImageView thumbnailIv;
        public final TextView titleTv;
        public final TextView descriptionTv;
        public final TextView creationTV;

        public MyViewHolder(CardView root, ImageView thumbnailIv, TextView titleTv, TextView descriptionTv, TextView creationTV) {
            super(root);
            this.thumbnailIv = thumbnailIv;
            this.titleTv = titleTv;
            this.descriptionTv = descriptionTv;
            this.creationTV = creationTV;
        }
    }
}

