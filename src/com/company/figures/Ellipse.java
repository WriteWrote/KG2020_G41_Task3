package com.company.figures;

import com.company.points.RealPoint;
import com.company.utils.ScreenConverter;
import com.company.utils.Idrawers.LineDrawer;
import com.company.utils.markers.ScaleMarker;

import java.util.ArrayList;
import java.util.List;

public class Ellipse {
    private RealPoint point;
    private double a, b;
    private ScaleMarker marker_UL, marker_UR, marker_DL, marker_DR;
    private boolean isActivated = false;

    public Ellipse(RealPoint point, double a, double b) {
        this.point = point;
        this.a = a;
        this.b = b;
// work this out
        marker_UL = new ScaleMarker(new RealPoint(point.getX() - a, point.getY() + b));
        marker_DL = new ScaleMarker(new RealPoint(point.getX() - a, point.getY() - b));
        marker_UR = new ScaleMarker(new RealPoint(point.getX() + a, point.getY() + b));
        marker_DR = new ScaleMarker(new RealPoint(point.getX() + a, point.getY() - b));
        /*marker_UL = new ScaleMarker(point);
        marker_DL = new ScaleMarker(new RealPoint(point.getX(), point.getY() - 2*b));
        marker_UR = new ScaleMarker(new RealPoint(point.getX() + 2*a, point.getY()));
        marker_DR = new ScaleMarker(new RealPoint(point.getX() + 2*a, point.getY() - 2*b));
         */
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

    public void setParams(double x, double y, double a, double b) {
        this.point.setX(x);
        this.point.setY(y);
        this.a = a;
        this.b = b;
    }

    public double getA() {
        return a;
    }

    public double getB() {
        return b;
    }

    public void setA(double a) {
        this.a = a;
    }

    public void setB(double b) {
        this.b = b;
    }

    public boolean isActivated() {
        return isActivated;
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
}
