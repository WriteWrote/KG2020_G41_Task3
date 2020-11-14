package com.company.linedrawers;

import com.company.utils.LineDrawer;
import com.company.points.ScreenPoint;

import java.awt.*;

public class GraphicsLineDrawer implements LineDrawer {
    private Graphics g;

    public GraphicsLineDrawer(Graphics g) {
        this.g = g;
    }

    @Override
    public void drawLine(ScreenPoint p1, ScreenPoint p2, Color color) {
        int x1 = p1.getX(); int x2 = p2.getX();
        int y1 = p1.getY(); int y2 = p2.getY();
        g.setColor(color);
        g.drawLine(x1, y1, x2, y2);
    }
}
