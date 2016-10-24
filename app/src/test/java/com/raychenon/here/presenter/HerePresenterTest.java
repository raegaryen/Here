package com.raychenon.here.presenter;

import static org.mockito.Mockito.mock;

import org.junit.After;
import org.junit.Before;

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

    // TODO SearchEngine must have Dependency Injection in order to mock

// @Test
// public void searchPlaces() {
//
// String query = "Here";
// GeoCoordinate coordinate = new GeoCoordinate(52.5200, 13.4050);
// herePresenter.requestPlaces(query, coordinate);
// verify(hereView).showProgress();
// // verify(hereView).displayDataInList();
// }

}
