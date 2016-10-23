package com.raychenon.here.view;

import java.util.ArrayList;

import com.here.android.mpa.common.GeoCoordinate;

import com.raychenon.here.model.PlacePOI;

/**
 * @author  Raymond Chenon
 */

public interface HereView {

    void displayDataInList(ArrayList<PlacePOI> data);

    void displayPlaceInMap(GeoCoordinate coordinate);

    void showErrorMessage(String error);
}
