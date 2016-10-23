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

    public PlacePOI(final String title, final String vicinity, final String iconUrl, final String id,
            final double distance, final double averageRating) {
        this.title = title;
        this.vicinity = vicinity;
        this.iconUrl = iconUrl;
        this.id = id;
        this.distance = distance;
        this.averageRating = averageRating;
    }

    public final String title;
    public final String vicinity;
    public final String iconUrl;
    public final String id;
    public final double distance;
    public final double averageRating;

}
