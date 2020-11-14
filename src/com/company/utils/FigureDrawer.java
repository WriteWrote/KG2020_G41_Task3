package com.company.utils;

import com.company.points.ScreenPoint;

public interface FigureDrawer {
    void draw();

    void moveMarkers(ScreenPoint start, ScreenPoint end);

    boolean hitCursor(ScreenPoint screenPoint);

    void fill();
}
