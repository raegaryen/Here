package com.raychenon.here.adapter;

import java.util.List;

import com.raychenon.here.R;
import com.raychenon.here.model.PlacePOI;

import android.app.Activity;

import android.support.annotation.NonNull;

import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

/**
 * @author  Raymond Chenon
 */

public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.PlaceViewHolder> {

    private LayoutInflater inflater;
    private List<PlacePOI> items;

    public PlaceAdapter(final Activity activity, @NonNull final List<PlacePOI> items) {
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
        holder.vicinityTextView.setText(item.vicinity);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class PlaceViewHolder extends RecyclerView.ViewHolder {

        public TextView titleTextView;
        public TextView vicinityTextView;

        public PlaceViewHolder(final View itemView) {
            super(itemView);
            titleTextView = (TextView) itemView.findViewById(R.id.placeViewholderTitle);
            vicinityTextView = (TextView) itemView.findViewById(R.id.placeViewholderVicinity);
        }
    }
}
