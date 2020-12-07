package com.company.figures;

import com.company.linedrawers.BresenhamLineDrawer;
import com.company.points.RealPoint;
import com.company.points.ScreenPoint;
import com.company.utils.drawers.LineDrawer;
import com.company.utils.drawers.PixelDrawer;
import com.company.utils.ScreenConverter;
import com.company.utils.markers.ScaleMarker;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Circle {
    private RealPoint point;
    private double radius;
    private ScaleMarker marker_UL, marker_UR, marker_DL, marker_DR;
    private boolean isActivated = false;

    private ScaleMarker[] markers;

    public Circle(RealPoint point, int radius) {
        this.point = point;
        this.radius = radius;

        marker_UL = new ScaleMarker(point);
        marker_DL = new ScaleMarker(new RealPoint(point.getX(), point.getY() - radius * 2));
        marker_UR = new ScaleMarker(new RealPoint(point.getX() + 2 * radius, point.getY()));
        marker_DR = new ScaleMarker(new RealPoint(point.getX() + 2 * radius, point.getY() - 2 * radius));
        markers = new ScaleMarker[]{marker_UL, marker_UR, marker_DL, marker_DR};
    }

    public void activate(boolean b) {
        this.isActivated = b;
    }

    public void setPoint(RealPoint point) {
        this.point = point;
    }

    public RealPoint getPoint() {
        return this.point;
    }

    public void setParams(int x, int y, int radius) {
        this.point.setX(x);
        this.point.setY(y);
        this.radius = radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public double getRadius() {
        return radius;
    }

    public boolean isActivated() {
        return isActivated;
    }

    public boolean hitMarkers(RealPoint currPoint, ScreenConverter screenConverter) {
        for (ScaleMarker m :
                markers) {
            if (m.hitMarker(currPoint, screenConverter))
                return true;
        }
        return false;
    }

    public void drawMarkers(LineDrawer g, ScreenConverter screenConverter) {
        marker_DR.draw(g, screenConverter);
        marker_UL.draw(g, screenConverter);
        marker_UR.draw(g, screenConverter);
        marker_DL.draw(g, screenConverter);
    }

    public boolean hitCursor(RealPoint currP) {
        if (currP.getX() < marker_UR.getX() && currP.getX() > marker_UL.getX() &&
                currP.getY() > marker_DR.getY() && currP.getY() < marker_UR.getY()) {
            return true;
        }
        return false;
    }

    public void moveMarkers(RealPoint start, RealPoint end) {
        double dx = end.getX() - start.getX();
        double dy = end.getY() - start.getY();
        List<ScaleMarker> list = new ArrayList<>();
        list.add(marker_DL);
        list.add(marker_DR);
        list.add(marker_UL);
        list.add(marker_UR);
        for (ScaleMarker m :
                list) {
            m.setX(m.getX() + dx);
            m.setY(m.getY() + dy);
        }
    }
}
