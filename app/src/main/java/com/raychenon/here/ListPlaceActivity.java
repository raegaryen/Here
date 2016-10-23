package com.raychenon.here;

import java.util.ArrayList;

import com.raychenon.here.adapter.DividerItemDecoration;
import com.raychenon.here.adapter.PlaceAdapter;
import com.raychenon.here.model.PlacePOI;

import android.app.Activity;

import android.content.Context;
import android.content.Intent;

import android.os.Bundle;

import android.support.annotation.NonNull;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * @author  Raymond Chenon
 */

public class ListPlaceActivity extends Activity {

    private static final String INTENT_EXTRA_PLACES = "intent_extra_places";

    private RecyclerView recyclerView;

    private ArrayList<PlacePOI> listData;

    public static Intent createIntent(final Context context, final ArrayList<PlacePOI> placeLinks) {
        Intent intent = new Intent(context, ListPlaceActivity.class);
        intent.putExtra(ListPlaceActivity.INTENT_EXTRA_PLACES, placeLinks);

        return intent;
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.places_activity);

        initFromIntent(getIntent());

        recyclerView = (RecyclerView) findViewById(R.id.placesRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,
                (int) getResources().getDimension(R.dimen.divider_margin))); // for the line divider

        PlaceAdapter adapter = new PlaceAdapter(this, listData);
        recyclerView.setAdapter(adapter);
    }

    private void initFromIntent(@NonNull final Intent intent) {
        if (intent.hasExtra(INTENT_EXTRA_PLACES)) {
            listData = (ArrayList<PlacePOI>) intent.getSerializableExtra(INTENT_EXTRA_PLACES);
        }

    }

}
