package com.company.figures;

import com.company.points.RealPoint;
import com.company.utils.ScreenConverter;
import com.company.utils.Idrawers.LineDrawer;
import com.company.utils.markers.AngleMarker;
import com.company.utils.markers.ScaleMarker;

import java.util.ArrayList;
import java.util.List;

public class Segment {
    private RealPoint point;
    private double startAngle;
    private double deltaAngle;
    private double radius;
    private ScaleMarker marker_UL, marker_UR, marker_DL, marker_DR;
    private AngleMarker startA_marker, endA_marker;
    private boolean isActivated = false;

    public Segment(RealPoint point, double startAngle, double deltaAngle, double radius) {
        this.point = point;
        this.startAngle = startAngle;
        this.deltaAngle = deltaAngle;
        this.radius = radius;

        marker_UL = new ScaleMarker(new RealPoint(point.getX() - radius, point.getY() - radius));
        marker_DL = new ScaleMarker(new RealPoint(point.getX() - radius, point.getY() + radius));
        marker_UR = new ScaleMarker(new RealPoint(point.getX() + radius, point.getY() - radius));
        marker_DR = new ScaleMarker(new RealPoint(point.getX() + radius, point.getY() + radius));

        startA_marker = new AngleMarker(new RealPoint(point.getX() + radius * Math.cos(startAngle),
                point.getY() - radius * Math.sin(startAngle)));
        endA_marker = new AngleMarker(new RealPoint(point.getX() + radius * Math.cos(startAngle + deltaAngle),
                point.getY() - radius * Math.sin(startAngle + deltaAngle)));
    }

    public boolean hitScaleMarkers(RealPoint currPoint) {
        ScaleMarker[] markers = new ScaleMarker[]{marker_UL, marker_UR, marker_DL, marker_DR};
        for (ScaleMarker m :
                markers) {
            if (m.hitMarker(currPoint))
                return true;
        }
        return false;
    }

    public boolean hitAngleMarkers(RealPoint currPoint) {
        if (endA_marker.hitMarker(currPoint))
            return true;
        if (startA_marker.hitMarker(currPoint))
            return true;
        return false;
    }

    public void drawMarkers(LineDrawer g) {
        marker_DR.draw(g);
        marker_UL.draw(g);
        marker_UR.draw(g);
        marker_DL.draw(g);

        startA_marker.draw(g);
        endA_marker.draw(g);
    }

    public boolean hitCursor(RealPoint currP) {
        if (currP.getX() < marker_UR.getX() && currP.getX() > marker_DL.getX()
                && currP.getY() > marker_UR.getY() && currP.getY() < marker_DL.getY()) {
            return true;
        }
        return false;
    }

    public void moveMarkers(RealPoint start, RealPoint end) {
        double dx = end.getX() - start.getX();
        double dy = end.getY() - start.getY();
        marker_UL = new ScaleMarker(new RealPoint(point.getX() - radius + dx, point.getY() - radius + dy));
        marker_DL = new ScaleMarker(new RealPoint(point.getX() - radius + dx, point.getY() + radius + dy));
        marker_UR = new ScaleMarker(new RealPoint(point.getX() + radius + dx, point.getY() - radius + dy));
        marker_DR = new ScaleMarker(new RealPoint(point.getX() + radius + dx, point.getY() + radius + dy));

        startA_marker = new AngleMarker(new RealPoint(point.getX() + radius * Math.cos(startAngle),
                point.getY() - radius * Math.sin(startAngle)));
        endA_marker = new AngleMarker(new RealPoint(point.getX() + radius * Math.cos(startAngle + deltaAngle),
                point.getY() - radius * Math.sin(startAngle + deltaAngle)));
    }

    public RealPoint getPoint() {
        return point;
    }

    public double getRadius() {
        return radius;
    }

    public double getStartAngle() {
        return startAngle;
    }

    public double getDeltaAngle() {
        return deltaAngle;
    }

    public boolean isActivated() {
        return isActivated;
    }

    public void setPoint(RealPoint point) {
        this.point = point;
    }

    public void setStartAngle(double startAngle) {
        this.startAngle = startAngle;
    }

    public void setDeltaAngle(double deltaAngle) {
        this.deltaAngle = deltaAngle;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public void activate(boolean activated) {
        isActivated = activated;
    }
}
