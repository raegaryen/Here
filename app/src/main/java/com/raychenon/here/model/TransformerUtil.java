package com.raychenon.here.model;

import java.util.ArrayList;
import java.util.List;

import com.here.android.mpa.search.PlaceLink;

/**
 * @author  Raymond Chenon
 */

public class TransformerUtil {

    private TransformerUtil() { }

    /**
     * a List is not Serializable but an ArrayList is.
     *
     * <p>This list will be bundled in an Intent 's extra
     *
     * @param   placeLinkList
     *
     * @return  ArrayList of {@link PlacePOI}
     */
    public static ArrayList<PlacePOI> transform(final List<PlaceLink> placeLinkList) {
        ArrayList list = new ArrayList();
        for (PlaceLink placeLink : placeLinkList) {
            list.add(transform(placeLink));
        }

        return list;
    }

    public static PlacePOI transform(final PlaceLink placeLink) {
        return new PlacePOI(placeLink.getTitle(), placeLink.getVicinity(), placeLink.getIconUrl(), placeLink.getId(),
                placeLink.getDistance(), placeLink.getAverageRating());
    }
}
