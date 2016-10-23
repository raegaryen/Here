package com.raychenon.here.presenter;

import java.util.List;

import com.here.android.mpa.common.GeoCoordinate;
import com.here.android.mpa.search.DiscoveryResultPage;
import com.here.android.mpa.search.ErrorCode;
import com.here.android.mpa.search.PlaceLink;

import com.raychenon.here.http.CallBackListener;
import com.raychenon.here.http.SearchEngine;
import com.raychenon.here.model.TransformerUtil;
import com.raychenon.here.view.HereView;

/**
 * @author  Raymond Chenon
 */

public class HerePresenter implements Presenter<HereView> {

    private HereView mvpView;

    private DiscoveryResultPage discoveryResultPage;
    private List<PlaceLink> dataList;

    private CallBackListener callBackListener = new CallBackListener<DiscoveryResultPage>() {
        @Override
        public void onSuccess(final DiscoveryResultPage data) {
            discoveryResultPage = data;
            dataList = data.getPlaceLinks();
            mvpView.displayDataInList(TransformerUtil.transform(data.getPlaceLinks()));
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

    public void requestPlaces(final String query, final GeoCoordinate lastUserCoordinate) {
        SearchEngine.request(lastUserCoordinate, query, callBackListener);
    }

    public void displayLocationOnMap(final String locationId, final int indexInList) {
        mvpView.displayPlaceInMap(dataList.get(indexInList).getPosition());
    }

}
