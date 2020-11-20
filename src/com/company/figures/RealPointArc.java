package com.company.figures;

import com.company.points.RealPoint;
import com.company.points.ScreenPoint;
import com.company.utils.Figure;
import com.company.utils.PixelDrawer;
import com.company.utils.ScreenConverter;
import com.company.utils.markers.RealPointScaleMarker;
import com.company.utils.markers.ScaleMarker;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class RealPointArc implements Figure {
    private RealPoint point;
    private int radius;
    private Color color = Color.BLACK;
    private RealPointScaleMarker marker_UL, marker_UR, marker_DL, marker_DR;


    public RealPointArc(RealPoint point, int radius, Color color) {
        this.point = point;
        this.radius = radius;
        this.color = color;
    }

    @Override
    public void draw(ScreenConverter screenConverter, PixelDrawer pixelDrawer) {

    }

    @Override
    public boolean hitCursor(ScreenPoint screenPoint) {
        if (screenPoint.getX() < marker_UR.getX() && screenPoint.getX() > marker_UL.getX() &&
                screenPoint.getY() > marker_DR.getY() && screenPoint.getY() < marker_UR.getY()) {
            return true;
        }
        return false;
    }

    @Override
    public void fill() {

    }

    @Override
    public void moveMarkers(RealPoint start, RealPoint end) {
        double dx = start.getX() - end.getX();
        double dy = start.getY() - end.getY();
        List<RealPointScaleMarker> list = new ArrayList<>();
        list.add(marker_DL);
        list.add(marker_DR);
        list.add(marker_UL);
        list.add(marker_UR);
        for (RealPointScaleMarker m :
                list) {
            m.setX(m.getX() + dx);
            m.setY(m.getY() + dy);
        }
    }
}
