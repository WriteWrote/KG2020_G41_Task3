package com.company.figures;

import com.company.points.RealPoint;
import com.company.utils.ScreenConverter;
import com.company.utils.Idrawers.LineDrawer;
import com.company.utils.markers.ScaleMarker;

import java.util.ArrayList;
import java.util.List;

public class SimpleRectangle {
    private RealPoint point;
    private double width, height;
    private ScaleMarker marker_UL, marker_UR, marker_DL, marker_DR;
    private boolean isActivated = false;

    public SimpleRectangle(RealPoint point, double width, double height) {
        this.point = point;
        this.width = width;
        this.height = height;
        marker_UL = new ScaleMarker(point);
        marker_DL = new ScaleMarker(new RealPoint(point.getX(), point.getY() - height));
        marker_UR = new ScaleMarker(new RealPoint(point.getX() + width, point.getY()));
        marker_DR = new ScaleMarker(new RealPoint(point.getX() + width, point.getY() - height));
    }

    public boolean hitMarkers(RealPoint currPoint) {
        ScaleMarker[] markers = new ScaleMarker[]{marker_UL, marker_UR, marker_DL, marker_DR};
        for (ScaleMarker m :
                markers) {
            if (m.hitMarker(currPoint))
                return true;
        }
        return false;
    }

    public void drawMarkers(LineDrawer g) {
        marker_DR.draw(g);
        marker_UL.draw(g);
        marker_UR.draw(g);
        marker_DL.draw(g);
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

    public RealPoint getPoint() {
        return point;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public boolean isActivated() {
        return isActivated;
    }

    public void setPoint(RealPoint point) {
        this.point = point;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void activate(boolean activated) {
        isActivated = activated;
    }

    public void setParams(double x, double y, double width, double height) {
        this.point.setX(x);
        this.point.setY(y);
        this.width = width;
        this.height = height;
    }
}
