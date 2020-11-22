package com.company.utils;

import com.company.linedrawers.BresenhamLineDrawer;
import com.company.points.RealPoint;
import com.company.points.ScreenPoint;

import java.awt.*;

public interface Figure {
    void draw(ScreenConverter screenConverter, PixelDrawer pixelDrawer);

    void moveMarkers(RealPoint start, RealPoint end);
    void drawMarkers(LineDrawer g, ScreenConverter screenConverter);

    boolean hitCursor(RealPoint screenPoint);

    void fill();

    void setColor(Color color);
    void setPoint(RealPoint point);
    RealPoint getPoint();
}
