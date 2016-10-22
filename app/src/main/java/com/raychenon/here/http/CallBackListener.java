package com.raychenon.here.http;

import com.here.android.mpa.search.ErrorCode;

/**
 * @author  Raymond Chenon
 */

public interface CallBackListener<D> {

    /**
     * generic can call any data type.
     *
     * @param  data
     */
    void onSuccess(D data);

    void onError(ErrorCode errorCode);
}
