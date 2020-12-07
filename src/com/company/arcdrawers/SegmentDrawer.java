package com.company.arcdrawers;

import com.company.figures.Segment;
import com.company.linedrawers.BresenhamLineDrawer;
import com.company.points.ScreenPoint;
import com.company.utils.ScreenConverter;
import com.company.utils.drawers.ArcDrawer;
import com.company.utils.drawers.LineDrawer;
import com.company.utils.drawers.PixelDrawer;

import java.awt.*;

public class SegmentDrawer implements ArcDrawer {
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

        int x1 = (int) (startPoint.getX() + radius * Math.cos(segment.getStartAngle()));
        int y1 = (int) (startPoint.getY() + radius * Math.sin(segment.getStartAngle()));
        int x2 = (int) (startPoint.getX() + radius * Math.cos(segment.getStartAngle() + segment.getDeltaAngle()));
        int y2 = (int) (startPoint.getY() + radius * Math.sin(segment.getStartAngle() + segment.getDeltaAngle()));

        LineDrawer ld = new BresenhamLineDrawer(pixelDrawer);
        ld.drawLine(new ScreenPoint(x1, y1), new ScreenPoint(x2, y2), color);
    }

    @Override
    public void fill() {

    }
}
