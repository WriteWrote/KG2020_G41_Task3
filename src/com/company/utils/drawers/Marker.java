package com.company.utils.drawers;

import com.company.points.RealPoint;
import com.company.utils.ScreenConverter;

public interface Marker {
    void draw(LineDrawer lineDrawer, ScreenConverter screenConverter);
    boolean hitMarker(RealPoint checkPoint, ScreenConverter screenConverter);
}
