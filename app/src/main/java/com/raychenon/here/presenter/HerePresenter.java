package com.raychenon.here.presenter;

import java.util.List;

import com.here.android.mpa.common.GeoCoordinate;
import com.here.android.mpa.routing.RouteManager;
import com.here.android.mpa.routing.RouteResult;
import com.here.android.mpa.search.DiscoveryResultPage;
import com.here.android.mpa.search.ErrorCode;
import com.here.android.mpa.search.PlaceLink;

import com.raychenon.here.http.CallBackListener;
import com.raychenon.here.http.RouteEngine;
import com.raychenon.here.http.SearchEngine;
import com.raychenon.here.model.TransformerUtil;
import com.raychenon.here.view.HereView;

/**
 * @author  Raymond Chenon
 */

public class HerePresenter implements Presenter<HereView> {

    private HereView mvpView;

    private List<PlaceLink> dataList;

    private GeoCoordinate lastKnownUserCoordinate;

    private CallBackListener searchCallback = new CallBackListener<DiscoveryResultPage, ErrorCode>() {
        @Override
        public void onSuccess(final DiscoveryResultPage data) {
            mvpView.hideProgress();
            dataList = data.getPlaceLinks();
            mvpView.displayDataInList(TransformerUtil.transform(data.getPlaceLinks()));
        }

        @Override
        public void onError(final ErrorCode errorCode) {
            mvpView.hideProgress();
            mvpView.showErrorMessage(errorCode.name());
        }
    };

    private CallBackListener routeCallback = new CallBackListener<List<RouteResult>, RouteManager.Error>() {

        @Override
        public void onSuccess(final List<RouteResult> data) {
            mvpView.hideProgress();
            mvpView.showRoute(data.get(0));     // select the first route
        }

        @Override
        public void onError(final RouteManager.Error errorCode) {
            mvpView.hideProgress();
            mvpView.showErrorMessage(errorCode.name());
        }
    };

    @Override
    public void attachView(final HereView view) {
        this.mvpView = view;
    }

    @Override
    public void detachView() {
        this.mvpView = null;
    }

    public void requestPlaces(final String query, final GeoCoordinate lastUserCoordinate) {
        this.lastKnownUserCoordinate = lastUserCoordinate;
        mvpView.showProgress();
        SearchEngine.request(lastUserCoordinate, query, searchCallback);
    }

    /**
     * look for the selected location. The list order has not changed, so the index will be used to look up for the
     * location
     *
     * @param  locationId   could be used. But searching a specific element in an List is O(n)
     * @param  indexInList  method used. Look up for object is constant
     */
    public void displayLocationOnMap(final String locationId, final int indexInList) {

        mvpView.displayPlaceInMap(dataList.get(indexInList).getPosition());
        mvpView.showProgress();
        calculateRoute(dataList.get(indexInList).getPosition());
    }

    private void calculateRoute(final GeoCoordinate destination) {
        RouteEngine.request(lastKnownUserCoordinate, destination, routeCallback);
    }

}
