package com.raychenon.here.presenter;

/**
 * @author  Raymond Chenon
 */

public interface Presenter<V> {

    void attachView(V view);

    void detachView();

}
