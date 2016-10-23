package com.raychenon.here.http;

import java.util.List;

import com.here.android.mpa.common.GeoCoordinate;
import com.here.android.mpa.routing.RouteManager;
import com.here.android.mpa.routing.RouteOptions;
import com.here.android.mpa.routing.RoutePlan;
import com.here.android.mpa.routing.RouteResult;

/**
 * @author  Raymond Chenon
 */

public class RouteEngine {

    private RouteEngine() { }

    public static void request(final GeoCoordinate start, final GeoCoordinate destination,
            final CallBackListener<List<RouteResult>, RouteManager.Error> callBackListener) {
        RoutePlan routePlan = new RoutePlan();
        routePlan.addWaypoint(start);
        routePlan.addWaypoint(destination);

        RouteOptions routeOptions = new RouteOptions();
        routeOptions.setTransportMode(RouteOptions.TransportMode.CAR);
        routeOptions.setRouteType(RouteOptions.Type.FASTEST);

        routePlan.setRouteOptions(routeOptions);

        RouteManager rm = new RouteManager();
        rm.calculateRoute(routePlan, new RouteListener(callBackListener));
    }

    private static class RouteListener implements RouteManager.Listener {
        CallBackListener callBackListener;

        public RouteListener(final CallBackListener callBackListener) {
            this.callBackListener = callBackListener;
        }

        // Method defined in Listener
        public void onProgress(final int percentage) {
            // Display a message indicating calculation progress
        }

        // Method defined in Listener
        public void onCalculateRouteFinished(final RouteManager.Error error, final List<RouteResult> routeResult) {

            // If the route was calculated successfully
            if (error == RouteManager.Error.NONE) {

                // Render the route on the map
                callBackListener.onSuccess(routeResult);
            } else {

                // Display a message indicating route calculation failure
                callBackListener.onError(error);
            }
        }
    }
}
