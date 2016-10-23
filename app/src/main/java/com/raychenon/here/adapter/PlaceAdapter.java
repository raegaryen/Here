package com.raychenon.here.adapter;

import java.text.MessageFormat;

import java.util.List;

import com.bumptech.glide.Glide;

import com.raychenon.here.R;
import com.raychenon.here.model.PlacePOI;

import android.app.Activity;

import android.support.annotation.NonNull;

import android.support.v7.widget.RecyclerView;

import android.text.Html;
import android.text.Spanned;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author  Raymond Chenon
 */

public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.PlaceViewHolder> {

    private LayoutInflater inflater;
    private List<PlacePOI> items;
    private String distanceMsg;

    public PlaceAdapter(final Activity activity, @NonNull final List<PlacePOI> items) {
        distanceMsg = activity.getString(R.string.distance_from);
        inflater = activity.getLayoutInflater();
        this.items = items;
    }

    @Override
    public PlaceViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        return new PlaceViewHolder(inflater.inflate(R.layout.place_viewholder, parent, false));
    }

    @Override
    public void onBindViewHolder(final PlaceViewHolder holder, final int position) {
        PlacePOI item = items.get(position);
        holder.titleTextView.setText(item.title);

        Spanned address;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            address = Html.fromHtml(item.vicinity, Html.FROM_HTML_MODE_LEGACY);
        } else {
            address = Html.fromHtml(item.vicinity);
        }

        holder.vicinityTextView.setText(address);

        holder.distanceTextView.setText(MessageFormat.format(distanceMsg, item.distance));
        Glide.with(holder.imageView.getContext()).load(item.iconUrl).fitCenter().crossFade().into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class PlaceViewHolder extends RecyclerView.ViewHolder {

        public TextView titleTextView;
        public TextView vicinityTextView;
        public TextView distanceTextView;
        public ImageView imageView;

        public PlaceViewHolder(final View itemView) {
            super(itemView);
            titleTextView = (TextView) itemView.findViewById(R.id.placeViewholderTitle);
            vicinityTextView = (TextView) itemView.findViewById(R.id.placeViewholderVicinity);
            distanceTextView = (TextView) itemView.findViewById(R.id.placeViewholderDistance);
            imageView = (ImageView) itemView.findViewById(R.id.placeViewholderImage);
        }
    }
}
