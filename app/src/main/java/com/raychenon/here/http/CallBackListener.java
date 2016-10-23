package com.raychenon.here.http;

/**
 * @author  Raymond Chenon
 */

public interface CallBackListener<D, E> {

    /**
     * generic can call any data type.
     *
     * @param  data
     */
    void onSuccess(D data);

    void onError(E errorCode);
}
