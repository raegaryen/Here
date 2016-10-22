package com.raychenon.here.http;

import com.here.android.mpa.common.GeoCoordinate;
import com.here.android.mpa.search.DiscoveryResultPage;
import com.here.android.mpa.search.ErrorCode;
import com.here.android.mpa.search.ResultListener;
import com.here.android.mpa.search.SearchRequest;

/**
 * @author  Raymond Chenon
 */

public class SearchEngine {

    public static void request(final GeoCoordinate coordinate, final String query,
            final CallBackListener callBackListener) {
        SearchRequest request = new SearchRequest(query).setSearchCenter(coordinate); // should named as a builder
                                                                                      // .withSearchCenter().build();
        request.setCollectionSize(15);

        ErrorCode error = request.execute(new SearchRequestListener(callBackListener));
    }

    private static class SearchRequestListener implements ResultListener<DiscoveryResultPage> {

        private CallBackListener callBack;

        public SearchRequestListener(final CallBackListener callBackListener) {
            this.callBack = callBackListener;
        }

        @Override
        public void onCompleted(final DiscoveryResultPage data, final ErrorCode error) {
            if (error != ErrorCode.NONE) {

                // Handle error
                callBack.onError(error);

            } else {
                callBack.onSuccess(data);
            }
        }
    }
}
