package com.company.utils.markers;

import com.company.MainPanel;
import com.company.points.RealPoint;
import com.company.points.ScreenPoint;
import com.company.utils.Marker;
import com.company.utils.ScreenConverter;

import javax.print.DocFlavor;
import java.awt.*;
import java.time.Year;

public class ScaleMarker implements Marker {
    private final int width = 21, height = 21;
    private final Color color = Color.BLACK;

    private RealPoint point;
    private Graphics2D g;

    public ScaleMarker(Graphics g, RealPoint point) {
        this.g = (Graphics2D) g;
        this.point = point;
    }

    public double getX() {
        return point.getX();
    }

    public double getY() {
        return point.getY();
    }

    public void setX(double x) {
        this.point.setX(x);
        //this.point = new ScreenPoint(x, point.getY());
    }

    public void setY(double y) {
        this.point.setY(y);
        //this.point = new ScreenPoint(point.getX(), y);
    }

    @Override
    public void draw() {
        double x = point.getX();
        double y = point.getY();
        g.setColor(color);
        g.setStroke(new BasicStroke(3));
        ScreenConverter scrConverter = new ScreenConverter(-2, 2, 4, 4, 800, 600);
        ScreenPoint buff = scrConverter.r2s(new RealPoint(x + width / 2 + 1, y));
        g.drawLine(buff.getX(), buff.getY(), buff.getX(), buff.getY() + height);
        buff = scrConverter.r2s(new RealPoint(x, y + height / 2 + 1));
        g.drawLine(buff.getX(), buff.getY(), buff.getX() + width, buff.getY());
    }
}
