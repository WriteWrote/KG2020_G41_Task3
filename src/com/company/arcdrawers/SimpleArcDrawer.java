package com.company.arcdrawers;

import com.company.figures.Segment;
import com.company.points.ScreenPoint;
import com.company.utils.ScreenConverter;
import com.company.utils.Idrawers.PixelDrawer;

import java.awt.*;

public class SimpleArcDrawer implements com.company.utils.Idrawers.ArcDrawer {
    @Override
    public void draw(Segment segment, Color color, ScreenConverter screenConverter, PixelDrawer pixelDrawer) {
        ScreenPoint startPoint = screenConverter.r2s(segment.getPoint());
        int radius = screenConverter.value2s(segment.getRadius());
        double delta = Math.PI / 360;

        if (segment.getDeltaAngle() >= 0) {
            for (double i = segment.getStartAngle(); i <= segment.getDeltaAngle() + segment.getStartAngle(); i += delta) {
                int x = (int) (startPoint.getX() + radius * Math.cos(i));
                int y = (int) (startPoint.getY() + radius * Math.sin(i));
                pixelDrawer.setPixel(x, y, color);
            }
        } else {
            for (double i = segment.getStartAngle() + segment.getDeltaAngle(); i <= segment.getStartAngle(); i += delta) {
                int x = (int) (startPoint.getX() + radius * Math.cos(i));
                int y = (int) (startPoint.getY() + radius * Math.sin(i));
                pixelDrawer.setPixel(x, y, color);
            }
        }
    }

    @Override
    public void fill() {

    }
}
