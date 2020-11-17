package com.company.figures;

import com.company.points.RealPoint;
import com.company.points.ScreenPoint;
import com.company.utils.Figure;
import com.company.utils.PixelDrawer;
import com.company.utils.markers.ScaleMarker;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Arc implements Figure {
    private PixelDrawer pixelDrawer;
    private RealPoint point;
    private int radius;
    private Color color = Color.BLACK;
    private ScaleMarker marker_UL, marker_UR, marker_DL, marker_DR;

    public Arc(Graphics g, PixelDrawer pixelDrawer, RealPoint point, int radius) {
        this.pixelDrawer = pixelDrawer;
        this.point = point;
        this.radius = radius;
        marker_UL = new ScaleMarker(g, point);
        marker_DL = new ScaleMarker(g, new ScreenPoint(point.getX(), point.getY() + radius * 2));
        marker_UR = new ScaleMarker(g, new ScreenPoint(point.getX() + 2 * radius, point.getY()));
        marker_DR = new ScaleMarker(g, new ScreenPoint(point.getX() + 2 * radius, point.getY() + 2 * radius));
    }

    public Arc(PixelDrawer pixelDrawer, ScreenPoint point, int radius, Color color) {
        this.pixelDrawer = pixelDrawer;
        this.point = point;
        this.radius = radius;
        this.color = color;
    }

    public void drawExpCircleArc(int startX, int startY, int endX, int endY) {
        int x = point.getX();
        int y = point.getY();
        int _x = radius;
        int _y = 0;
        int radiusError = 1 - _x;
        while (_x >= _y) {
            pixelDrawer.setPixel(-_x + x + radius, -_y + y + radius, Color.BLUE);
            pixelDrawer.setPixel(-_y + x + radius, -_x + y + radius, Color.PINK);
            pixelDrawer.setPixel(_y + x + radius, -_x + y + radius, Color.LIGHT_GRAY);
            pixelDrawer.setPixel(_x + x + radius, -_y + y + radius, Color.ORANGE);
            pixelDrawer.setPixel(_x + x + radius, _y + y + radius, Color.CYAN);
            pixelDrawer.setPixel(_y + x + radius, _x + y + radius, Color.MAGENTA);
            pixelDrawer.setPixel(-_y + x + radius, _x + y + radius, Color.GREEN);
            pixelDrawer.setPixel(-_x + x + radius, _y + y + radius, Color.RED);

            if (startX <= -_x + radius && startY <= -_y + radius &&
                    -_x + radius <= endX && -_y + radius <= endY) {
                pixelDrawer.setPixel(-_x + x + radius, -_y + y + radius, Color.BLACK);
            }
            if (startX <= -_y + radius && startY <= -_x + radius &&
                    -_y + radius <= endX && -_x + radius <= endY) {
                pixelDrawer.setPixel(-_y + x + radius, -_x + y + radius, Color.BLACK);
            }
            if (startX <= _y + radius && startY <= -_x + radius
                    && _y + radius <= endX && -_x + radius <= endY) {
                pixelDrawer.setPixel(_y + x + radius, -_x + y + radius, Color.BLACK);
            }
            if (startX <= _x + radius && startY <= -_y + radius &&
                    _x + radius <= endX && -_y + radius <= endY) {
                pixelDrawer.setPixel(_x + x + radius, -_y + y + radius, Color.BLACK);
            }
            if (startX <= _x + radius && startY < _y + radius &&
                    _x + radius <= endX && _y + radius <= endY) {
                pixelDrawer.setPixel(_x + x + radius, _y + y + radius, Color.BLACK);
            }
            if (startX <= _y + radius && startY <= _x + radius &&
                    _y + radius <= endX && _x + radius <= endY) {
                pixelDrawer.setPixel(_y + x + radius, _x + y + radius, Color.BLACK);
            }
            if (startX <= -_y + radius && startY <= _x + radius &&
                    -_y + radius <= endX && _x + radius <= endY) {
                pixelDrawer.setPixel(-_y + x + radius, _x + y + radius, Color.BLACK);
            }
            if (startX <= -_x + radius && startY <= _y + radius &&
                    -_x + radius <= endX && _y + radius <= endY) {
                pixelDrawer.setPixel(-_x + x + radius, _y + y + radius, Color.BLACK);
            }

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
    public void draw() {

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
    public void moveMarkers(ScreenPoint start, ScreenPoint end) {
        int dx = start.getX() - end.getX();
        int dy = start.getY() - end.getY();
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
