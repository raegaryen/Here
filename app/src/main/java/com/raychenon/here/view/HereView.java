package com.raychenon.here.view;

import com.here.android.mpa.search.DiscoveryResultPage;

/**
 * @author  Raymond Chenon
 */

public interface HereView {

    void displayData(DiscoveryResultPage data);

    void showErrorMessage(String error);
}
