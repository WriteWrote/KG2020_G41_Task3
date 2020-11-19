package com.company.utils.markers;

import com.company.MainPanel;
import com.company.points.RealPoint;
import com.company.points.ScreenPoint;
import com.company.utils.Marker;
import com.company.utils.ScreenConverter;

import javax.print.DocFlavor;
import java.awt.*;

public class ScaleMarker implements Marker {
    private final int width = 21, height = 21;
    private final Color color = Color.BLACK;

    private ScreenPoint point;
    private Graphics2D g;

    public ScaleMarker(Graphics g, ScreenPoint point) {
        this.g = (Graphics2D) g;
        this.point = point;
    }

    public int getX() {
        return point.getX();
    }

    public int getY() {
        return point.getY();
    }

    public void setX(int x) {
        this.point = new ScreenPoint(x, point.getY());
    }

    public void setY(int y) {
        this.point = new ScreenPoint(point.getX(), y);
    }

    @Override
    public void draw() {
        int x = point.getX();
        int y = point.getY();
        g.setColor(color);
        g.setStroke(new BasicStroke(3));
        g.drawLine(x + width / 2 + 1, y,x + width / 2 + 1,  y + height);
        g.drawLine(x, y + height / 2 + 1, x + width, y + height / 2 + 1);
    }
}
