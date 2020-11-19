package com.company.utils;

import com.company.points.RealPoint;
import com.company.points.ScreenPoint;

public interface Figure {
    void draw();

    void moveMarkers(RealPoint start, RealPoint end);

    boolean hitCursor(ScreenPoint screenPoint);

    void fill();
}
