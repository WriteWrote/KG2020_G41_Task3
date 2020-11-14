package com.company.utils.markers;

import com.company.points.ScreenPoint;
import com.company.utils.Marker;

import java.awt.*;

public class ScaleMarker implements Marker {
    private final int width = 21, height = 21;
    private final Color color = Color.BLACK;

    private ScreenPoint p_UL, p_UR, p_DL, p_DR;
    private Graphics2D g;

    public ScaleMarker(Graphics g, ScreenPoint p_UL, ScreenPoint p_UR, ScreenPoint p_DL, ScreenPoint p_DR) {
        this.g = (Graphics2D) g;
        this.p_UL = p_UL;
        this.p_UR = p_UR;
        this.p_DL = p_DL;
        this.p_DR = p_DR;
    }

    @Override
    public void draw() {
        drawOneMarker(p_UL.getX(), p_UR.getY());
        drawOneMarker(p_DL.getX(), p_DL.getY());
        drawOneMarker(p_DR.getX(), p_DR.getY());
        drawOneMarker(p_UR.getX(), p_UR.getY());
    }

    private void drawOneMarker(int x, int y) {
        g.setColor(color);
        g.setStroke(new BasicStroke(3));
        g.drawLine(x + width / 2 + 1, y, x + width / 2 + 1, y + height);
        g.drawLine(x, y + height / 2 + 1, x + width, y + height / 2 + 1);
    }
}
