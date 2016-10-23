package com.raychenon.here;

import static android.view.inputmethod.EditorInfo.IME_ACTION_SEARCH;

import java.io.IOException;

import java.lang.ref.WeakReference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.here.android.mpa.common.GeoCoordinate;
import com.here.android.mpa.common.GeoPosition;
import com.here.android.mpa.common.Image;
import com.here.android.mpa.common.OnEngineInitListener;
import com.here.android.mpa.common.PositioningManager;
import com.here.android.mpa.mapping.Map;
import com.here.android.mpa.mapping.MapFragment;
import com.here.android.mpa.mapping.MapMarker;
import com.here.android.mpa.mapping.MapObject;
import com.here.android.mpa.search.DiscoveryResultPage;
import com.here.android.mpa.search.PlaceLink;

import com.raychenon.here.model.PlacePOI;
import com.raychenon.here.presenter.HerePresenter;
import com.raychenon.here.ui.SnackbarWrapper;
import com.raychenon.here.view.HereView;

import android.Manifest;

import android.app.Activity;

import android.content.Context;
import android.content.Intent;

import android.content.pm.PackageManager;

import android.os.Bundle;

import android.support.annotation.NonNull;

import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import android.util.Log;

import android.view.KeyEvent;
import android.view.View;

import android.view.inputmethod.InputMethodManager;

import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author  Raymond Chenon
 */

public class HereActivity extends Activity implements HereView {

    public static final String ACT_RESULT_ID = "activity_result_id";
    public static final String ACT_RESULT_POSITION = "activity_result_position";
    public static final int PICK_PLACE_REQUEST_CODE = 1;

    private static final String LOG_TAG = HereActivity.class.getSimpleName();

    // permissions request code
    private static final int REQUEST_CODE_ASK_PERMISSIONS = 1;

    /**
     * Permissions that need to be explicitly requested from end user.
     */
    private static final String[] REQUIRED_SDK_PERMISSIONS = new String[] {
        Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    // map embedded in the map fragment
    private Map map = null;

    // map fragment embedded in this activity
    private MapFragment mapFragment = null;

    private PositioningManager mPositioningManager;
    private boolean paused = false;
    private MapMarker userMarker;

    private GeoCoordinate lastCoordinate;

    private HerePresenter presenter;

    private PositioningManager.OnPositionChangedListener positionListener =
        new PositioningManager.OnPositionChangedListener() {

            public void onPositionUpdated(final PositioningManager.LocationMethod method, final GeoPosition position,
                    final boolean isMapMatched) {

                // set the center only when the app is in the foreground
                // to reduce CPU consumption
                if (!paused) {
                    lastCoordinate = position.getCoordinate();
                    map.setCenter(position.getCoordinate(), Map.Animation.LINEAR);

                    drawUserPosition();
                }
            }

            public void onPositionFixChanged(final PositioningManager.LocationMethod method,
                    final PositioningManager.LocationStatus status) { }
        };

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkPermissions();

        presenter = new HerePresenter();
        presenter.attachView(this);
        initSearchBar();
    }

    // To remove the positioning listener
    public void onDestroy() {
        if (mPositioningManager != null) {

            // Cleanup
            mPositioningManager.removeListener(positionListener);
        }

        map = null;

        presenter.detachView();
        super.onDestroy();

    }

    // Resume positioning listener on wake up
    public void onResume() {
        super.onResume();
        paused = false;
        if (mPositioningManager != null) {
            mPositioningManager.start(PositioningManager.LocationMethod.GPS_NETWORK);
        }
    }

    // To pause positioning listener
    public void onPause() {
        if (mPositioningManager != null) {
            mPositioningManager.stop();
        }

        super.onPause();
        paused = true;
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {

        // Check which request we're responding to
        if (requestCode == PICK_PLACE_REQUEST_CODE && resultCode == RESULT_OK) {
            String id = data.getStringExtra(ACT_RESULT_ID);
            SnackbarWrapper.make(this, "Return " + id, SnackbarWrapper.Duration.LONG).show();
        }
    }

    private void initSearchBar() {
        final EditText searchBar = (EditText) findViewById(R.id.searchBar);
        searchBar.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(final TextView textView, final int actionId, final KeyEvent keyEvent) {
                    boolean handled = false;

                    if (actionId == IME_ACTION_SEARCH) {
                        presenter.requestPlaces(searchBar.getText().toString(), lastCoordinate);
                        hideSoftKeyboard();
                        handled = true;
                    }

                    return handled;
                }
            });
    }

    private void initPosition() {
        if (mPositioningManager != null) {

            // add the listener to a synchronized object
            PositioningManager.getInstance().addListener(
                new WeakReference<PositioningManager.OnPositionChangedListener>(positionListener));

            mPositioningManager = PositioningManager.getInstance();
        }
    }

    private void initialize() {
        setContentView(R.layout.activity_main);

        // Search for the map fragment to finish setup by calling init().
        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapfragment);
        mapFragment.setRetainInstance(true);
        mapFragment.init(new OnEngineInitListener() {
                @Override
                public void onEngineInitializationCompleted(final OnEngineInitListener.Error error) {
                    if (error == OnEngineInitListener.Error.NONE) {

                        // retrieve a reference of the map from the map fragment
                        map = mapFragment.getMap();

                        // Set the zoom level to the average between min and max
                        map.setZoomLevel(getZoomLevel());
                        map.setMapScheme(map.getMapSchemes().get(2));
                        lastCoordinate = map.getCenter();
                    } else {
                        Log.e(LOG_TAG, "Cannot initialize MapFragment (" + error + ")");
                    }

                    initPosition();

                    drawUserPosition();
                }
            });

        mapFragment.setAllowEnterTransitionOverlap(true);

    }

    private void hideSoftKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void drawUserPosition() {
        if (map.getPositionIndicator().isVisible()) {
            map.getPositionIndicator().setVisible(true);
            map.getPositionIndicator().setAccuracyIndicatorVisible(true);
        } else {
            if (userMarker == null) {
                userMarker = new MapMarker(lastCoordinate, constructImage(R.drawable.user_loc));
            } else {
                map.removeMapObject(userMarker);
            }

            userMarker.setCoordinate(lastCoordinate);
            map.addMapObject(userMarker);
        }
    }

    @Override
    public void onRequestPermissionsResult(final int requestCode, @NonNull final String[] permissions,
            @NonNull final int[] grantResults) {
        switch (requestCode) {

            case REQUEST_CODE_ASK_PERMISSIONS :
                for (int index = permissions.length - 1; index >= 0; --index) {
                    if (grantResults[index] != PackageManager.PERMISSION_GRANTED) {

                        // exit the app if one permission is not granted
                        Toast.makeText(this, "Required permission '" + permissions[index] + "' not granted, exiting",
                            Toast.LENGTH_LONG).show();
                        finish();
                        return;
                    }
                }

                // all permissions were granted
                initialize();
                break;
        }
    }

    /**
     * Checks the dynamically controlled permissions and requests missing permissions from end user.
     */
    protected void checkPermissions() {

        final List<String> missingPermissions = new ArrayList<String>();

        // check all required dynamic permissions
        for (final String permission : REQUIRED_SDK_PERMISSIONS) {
            final int result = ContextCompat.checkSelfPermission(this, permission);
            if (result != PackageManager.PERMISSION_GRANTED) {
                missingPermissions.add(permission);
            }
        }

        if (!missingPermissions.isEmpty()) {

            // request all missing permissions
            final String[] permissions = missingPermissions.toArray(new String[missingPermissions.size()]);
            ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE_ASK_PERMISSIONS);
        } else {
            final int[] grantResults = new int[REQUIRED_SDK_PERMISSIONS.length];
            Arrays.fill(grantResults, PackageManager.PERMISSION_GRANTED);
            onRequestPermissionsResult(REQUEST_CODE_ASK_PERMISSIONS, REQUIRED_SDK_PERMISSIONS, grantResults);
        }
    }

    private double getZoomLevel() {
        return map.getMaxZoomLevel() * 0.95;
    }

    @Override
    public void displayData(final ArrayList<PlacePOI> data) {
        SnackbarWrapper.make(this, "Success " + data.size(), SnackbarWrapper.Duration.SHORT).show();

        startActivityForResult(ListPlaceActivity.createIntent(this, data), PICK_PLACE_REQUEST_CODE);
    }

    @Override
    public void showErrorMessage(final String error) {
        SnackbarWrapper.make(this, error, SnackbarWrapper.Duration.SHORT).show();
    }

    private List<MapObject> mapObjectList = new ArrayList<>();

    // not necessary
    private void displayOnMap(final DiscoveryResultPage data) {
        map.removeMapObjects(mapObjectList);
        mapObjectList.clear();

        for (PlaceLink result : data.getPlaceLinks()) {
            addToList(result.getPosition());
        }

        map.addMapObjects(mapObjectList);
        // TODO set the bounding box
    }

    private void addToList(final GeoCoordinate geoCoordinate) {
        com.here.android.mpa.common.Image myImage = constructImage(R.drawable.cat_06);

        MapMarker myMapMarker = new MapMarker(geoCoordinate, myImage);
        mapObjectList.add(myMapMarker);

    }

    private Image constructImage(final int resId) {
        com.here.android.mpa.common.Image myImage = new com.here.android.mpa.common.Image();
        try {
            myImage.setImageResource(resId);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return myImage;
    }
}
