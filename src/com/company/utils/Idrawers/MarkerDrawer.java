package com.company.utils.Idrawers;

import com.company.points.RealPoint;
import com.company.utils.ScreenConverter;

public interface MarkerDrawer {
    void draw(LineDrawer lineDrawer, ScreenConverter screenConverter);
    boolean hitMarker(RealPoint checkPoint, ScreenConverter screenConverter);
}
