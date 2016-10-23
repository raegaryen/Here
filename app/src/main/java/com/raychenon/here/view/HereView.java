package com.raychenon.here.view;

import java.util.ArrayList;

import com.raychenon.here.model.PlacePOI;

/**
 * @author  Raymond Chenon
 */

public interface HereView {

    void displayData(ArrayList<PlacePOI> data);

    void showErrorMessage(String error);
}
