package com.raychenon.here.presenter;

import com.here.android.mpa.common.GeoCoordinate;
import com.here.android.mpa.search.DiscoveryResultPage;
import com.here.android.mpa.search.ErrorCode;

import com.raychenon.here.http.CallBackListener;
import com.raychenon.here.http.SearchEngine;
import com.raychenon.here.view.HereView;

/**
 * @author  Raymond Chenon
 */

public class HerePresenter implements Presenter<HereView> {

    private HereView mvpView;

    private CallBackListener callBackListener = new CallBackListener() {
        @Override
        public void onSuccess(final DiscoveryResultPage data) {
            mvpView.displayData();
        }

        @Override
        public void onError(final ErrorCode errorCode) {
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

    public void requestPlaces(final String query, final GeoCoordinate lastCoordinate) {
        SearchEngine.request(lastCoordinate, query, callBackListener);
    }

}
