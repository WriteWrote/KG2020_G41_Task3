package com.company.figures;

import com.company.points.ScreenPoint;
import com.company.utils.FigureDrawer;
import com.company.utils.PixelDrawer;
import com.company.utils.markers.ScaleMarker;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Arc implements FigureDrawer {
    private PixelDrawer pixelDrawer;
    private ScreenPoint point;
    private int radius;
    private Color color = Color.BLACK;
    private ScaleMarker[] scaleMarkers;

    public Arc(PixelDrawer pixelDrawer, ScreenPoint point, int radius) {
        this.pixelDrawer = pixelDrawer;
        this.point = point;
        this.radius = radius;
        scaleMarkers = new ScaleMarker[4];
        // like in a watch
        //scaleMarkers[0];
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
        return false;
    }

    @Override
    public void fill() {

    }

    @Override
    public void moveMarkers(ScreenPoint start, ScreenPoint end) {

    }
}
