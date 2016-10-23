package com.raychenon.here.view;

import java.util.ArrayList;
import java.util.List;

import com.here.android.mpa.common.GeoCoordinate;
import com.here.android.mpa.routing.RouteResult;

import com.raychenon.here.model.PlacePOI;

/**
 * @author  Raymond Chenon
 */

public interface HereView {

    void displayDataInList(ArrayList<PlacePOI> data);

    void displayPlaceInMap(GeoCoordinate coordinate);

    void showRoute(List<RouteResult> routeResultList);

    void showErrorMessage(String error);
}
