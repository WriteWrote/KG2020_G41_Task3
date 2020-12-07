package com.company.circledrawers;

import com.company.figures.Circle;
import com.company.linedrawers.BresenhamLineDrawer;
import com.company.points.RealPoint;
import com.company.points.ScreenPoint;
import com.company.utils.ScreenConverter;
import com.company.utils.drawers.CircleDrawer;
import com.company.utils.drawers.PixelDrawer;

import java.awt.*;

public class BresenhamCircleDrawer implements CircleDrawer {
    @Override
    public void draw(Circle circle, Color color, ScreenConverter screenConverter, PixelDrawer pixelDrawer) {
        RealPoint point = circle.getPoint();
        ScreenPoint screenPoint = screenConverter.r2s(point);
        int x = screenPoint.getX();
        int y = screenPoint.getY();
        int _radius = screenConverter.value2s(circle.getRadius());
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
        if (circle.isActivated()) {
            circle.drawMarkers(new BresenhamLineDrawer(pixelDrawer), screenConverter);
        }
    }

    @Override
    public void fill() {

    }
}
