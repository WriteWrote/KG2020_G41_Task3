package com.company.figures;

import com.company.linedrawers.BresenhamLineDrawer;
import com.company.points.RealPoint;
import com.company.points.ScreenPoint;
import com.company.utils.Figure;
import com.company.utils.LineDrawer;
import com.company.utils.PixelDrawer;
import com.company.utils.ScreenConverter;
import com.company.utils.markers.ScaleMarker;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BresenhamCircle implements Figure {
    private RealPoint point;
    private double radius;
    private Color color;
    private ScaleMarker marker_UL, marker_UR, marker_DL, marker_DR;

    public BresenhamCircle(RealPoint point, int radius, Color color) {
        this.point = point;
        this.radius = radius;
        this.color = color;
        marker_UL = new ScaleMarker(point);
        marker_DL = new ScaleMarker(new RealPoint(point.getX(), point.getY() - radius * 2));
        marker_UR = new ScaleMarker(new RealPoint(point.getX() + 2 * radius, point.getY()));
        marker_DR = new ScaleMarker(new RealPoint(point.getX() + 2 * radius, point.getY() - 2 * radius));
    }

    @Override
    public void draw(ScreenConverter screenConverter, PixelDrawer pixelDrawer) {
        ScreenPoint screenPoint = screenConverter.r2s(point);
        int x = screenPoint.getX();
        int y = screenPoint.getY();
        int _radius = screenConverter.value2s(radius);
        int _x = _radius;
        //int _x = x;
        int _y = 0;
        int radiusError = 1 - _x;
        while (_x >= _y) {
            pixelDrawer.setPixel(_x + x + _radius, _y + y + _radius, color);
            pixelDrawer.setPixel(_y + x + _radius, _x + y + _radius, color);
            pixelDrawer.setPixel(-_x + x + _radius, _y + y + _radius, color);
            pixelDrawer.setPixel(-_y + x + _radius, _x + y + _radius, color);
            pixelDrawer.setPixel(-_x + x + _radius, -_y + y + _radius, color);
            pixelDrawer.setPixel(-_y + x + _radius, -_x + y + _radius, color);
            pixelDrawer.setPixel(_x + x + _radius, -_y + y + _radius, color);
            pixelDrawer.setPixel(_y + x + _radius, -_x + y + _radius, color);
            _y++;
            if (radiusError < 0) {
                radiusError += 2 * _y + 1;
            } else {
                _x--;
                radiusError += 2 * (_y - _x + 1);
            }
        }
    }

    @Override
    public void moveMarkers(RealPoint start, RealPoint end) {
        double dx = start.getX() - end.getX();
        double dy = start.getY() - end.getY();
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

    @Override
    public boolean hitCursor(RealPoint currP) {
        if (currP.getX() < marker_UR.getX() && currP.getX() > marker_UL.getX() &&
                currP.getY() > marker_DR.getY() && currP.getY() < marker_UR.getY()) {
            return true;
        }
        return false;
    }

    @Override
    public void drawMarkers(LineDrawer g, ScreenConverter screenConverter) {
        marker_DR.draw(g, screenConverter);
        marker_UL.draw(g, screenConverter);
        marker_UR.draw(g, screenConverter);
        marker_DL.draw(g, screenConverter);
    }

    @Override
    public void fill() {

    }

    @Override
    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public void setPoint(RealPoint point) {
        this.point = point;
    }

    @Override
    public RealPoint getPoint() {
        return this.point;
    }

    public void setParams(int x, int y, int radius, Color color) {
        this.point.setX(x);
        this.point.setY(y);
        this.radius = radius;
        this.color = color;
    }
}
