package com.raychenon.here.presenter;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.here.android.mpa.common.GeoCoordinate;

import com.raychenon.here.view.HereView;

/**
 * @author  Raymond Chenon
 */

public class HerePresenterTest {

    HereView hereView;
    HerePresenter herePresenter;

    @Before
    public void setUp() {

        herePresenter = new HerePresenter();
        hereView = mock(HereView.class);
        herePresenter.attachView(hereView);
    }

    @After
    public void tearDown() {
        herePresenter.detachView();
    }

    @Test
    public void searchPlaces() {

        String query = "Here";
        GeoCoordinate coordinate = new GeoCoordinate(52.5200, 13.4050);
        herePresenter.requestPlaces(query, coordinate);
        verify(hereView).showProgress();
        // verify(hereView).displayDataInList();
    }

}
