package com.company.utils.drawers;

import com.company.points.ScreenPoint;

import java.awt.*;

public interface LineDrawer {
    void drawLine(ScreenPoint p1, ScreenPoint p2, Color color);
}
