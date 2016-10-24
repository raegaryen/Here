package com.raychenon.here.model;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;

import com.here.android.mpa.search.PlaceLink;

import junit.framework.Assert;

/**
 * @author  Raymond Chenon
 */

public class TransformerUtilTest {

    private final double RATING = 5.0;
    private final double DISTANCE = 500.0;
    private final String TITLE = "Title";
    private final String VICINITY = "vin";
    private final String ICON_URL = "icon";

    @Test
    public void transformerPreservesValues() {
        String id = "dfhdfh";
        PlaceLink placeLink = initPlaceLink(id);
        PlacePOI placePOI = TransformerUtil.transform(placeLink);

        Assert.assertEquals(placeLink.getAverageRating(), placePOI.averageRating);
        Assert.assertEquals(placeLink.getTitle(), placePOI.title);
        Assert.assertEquals(placeLink.getVicinity(), placePOI.vicinity);
        Assert.assertEquals(placeLink.getIconUrl(), placePOI.iconUrl);
        Assert.assertEquals(placeLink.getDistance(), placePOI.distance);
        Assert.assertEquals(placeLink.getId(), placePOI.id);
    }

    private PlaceLink initPlaceLink(final String id) {
        PlaceLink placeLink = mock(PlaceLink.class);
        when(placeLink.getAverageRating()).thenReturn(RATING);
        when(placeLink.getDistance()).thenReturn(DISTANCE);
        when(placeLink.getTitle()).thenReturn(TITLE);
        when(placeLink.getVicinity()).thenReturn(VICINITY);
        when(placeLink.getIconUrl()).thenReturn(ICON_URL);
        when(placeLink.getId()).thenReturn(id);

        return placeLink;
    }

}
