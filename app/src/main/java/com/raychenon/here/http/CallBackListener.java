package com.raychenon.here.http;

import com.here.android.mpa.search.DiscoveryResultPage;
import com.here.android.mpa.search.ErrorCode;

/**
 * @author  Raymond Chenon
 */

public interface CallBackListener {

    void onSuccess(DiscoveryResultPage data);

    void onError(ErrorCode errorCode);
}
