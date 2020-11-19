package com.company.figures;

import com.company.points.RealPoint;
import com.company.points.ScreenPoint;
import com.company.utils.Figure;
import com.company.utils.PixelDrawer;
import java.awt.*;

public class BresenhamCircle implements Figure {
    PixelDrawer pixelDrawer;
    private RealPoint p;
    //private int x, y, radius;
    private int radius;
    private Color color;

    public BresenhamCircle(PixelDrawer pixelDrawer, int x, int y, int radius, Color color) {
        this.pixelDrawer = pixelDrawer;
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.color = color;
    }

    @Override
    public void draw() {
        int _x = radius;
        //int _x = x;
        int _y = 0;
        int radiusError = 1 - _x;
        while (_x >= _y) {
            pixelDrawer.setPixel(_x + x + radius, _y + y + radius, color);
            pixelDrawer.setPixel(_y + x + radius, _x + y + radius, color);
            pixelDrawer.setPixel(-_x + x + radius, _y + y + radius, color);
            pixelDrawer.setPixel(-_y + x + radius, _x + y + radius, color);
            pixelDrawer.setPixel(-_x + x + radius, -_y + y + radius, color);
            pixelDrawer.setPixel(-_y + x + radius, -_x + y + radius, color);
            pixelDrawer.setPixel(_x + x + radius, -_y + y + radius, color);
            pixelDrawer.setPixel(_y + x + radius, -_x + y + radius, color);
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

    }

    @Override
    public boolean hitCursor(ScreenPoint screenPoint) {
        return false;
    }

    @Override
    public void fill() {

    }
    public void setParams(int x, int y, int radius, Color color){
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.color = color;
    }
}
