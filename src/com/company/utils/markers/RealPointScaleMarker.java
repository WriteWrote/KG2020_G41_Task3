package com.company.utils.markers;

import com.company.points.RealPoint;
import com.company.points.ScreenPoint;
import com.company.utils.Marker;
import com.company.utils.ScreenConverter;

import java.awt.*;

public class RealPointScaleMarker implements Marker {
    private final int width = 21, height = 21;
    private final Color color = Color.BLACK;

    private RealPoint point;
    private Graphics2D g;

    public RealPointScaleMarker(Graphics g, RealPoint point) {
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
        //this.point = new ScreenPoint(x, point.getY());
        this.point.setX(x);
    }

    public void setY(double y) {
        //this.point = new ScreenPoint(point.getX(), y);
        this.point.setY(y);
    }

    @Override
    public void draw() {
        double x = point.getX();
        double y = point.getY();
        g.setColor(color);
        g.setStroke(new BasicStroke(3));
        ScreenConverter scrConv = new ScreenConverter(-2, 2, 4, 4, 800, 600);
        RealPoint buff = new RealPoint(x + width / 2 + 1, y);
        ScreenPoint scrBuff = scrConv.r2s(buff);
        g.drawLine(scrBuff.getX(), scrBuff.getY(), scrBuff.getX(), scrBuff.getY() + height);
        buff.setX(x);
        buff.setY(y + height / 2 + 1);
        scrBuff = scrConv.r2s(buff);
        g.drawLine(scrBuff.getX(), scrBuff.getY(), scrBuff.getX() + width, scrBuff.getY());
    }
}
