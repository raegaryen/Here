package com.raychenon.here.model;

import java.io.Serializable;

/**
 * POI = point of interest. Because {@link com.here.android.mpa.search.PlaceLink} is not Serializable or Parcelable.
 * This class is used to pass between Activities
 *
 * <p>NOTE : this class should be Parcelable for performance. But I'm short on time to generate extra code
 *
 * @author  Raymond Chenon
 */

public class PlacePOI implements Serializable {

    public PlacePOI(final String title, final String vicinity) {
        this.title = title;
        this.vicinity = vicinity;
    }

    public final String title;
    public final String vicinity;

}
