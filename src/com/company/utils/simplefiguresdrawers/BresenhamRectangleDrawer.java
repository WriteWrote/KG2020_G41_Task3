package com.company.utils.simplefiguresdrawers;

import com.company.figures.SimpleRectangle;
import com.company.linedrawers.BresenhamLineDrawer;
import com.company.points.ScreenPoint;
import com.company.utils.ScreenConverter;
import com.company.utils.Idrawers.PixelDrawer;
import com.company.utils.Idrawers.RectangleDrawer;

import java.awt.*;

public class BresenhamRectangleDrawer implements RectangleDrawer {
    @Override
    public void draw(SimpleRectangle simpleRectangle, Color color, ScreenConverter screenConverter, PixelDrawer pixelDrawer) {
        ScreenPoint point = screenConverter.r2s(simpleRectangle.getPoint());
        int width = screenConverter.value2s(simpleRectangle.getWidth());
        int height = screenConverter.value2s(simpleRectangle.getHeight());
        BresenhamLineDrawer lineDrawer = new BresenhamLineDrawer(pixelDrawer);
        lineDrawer.drawLine(point, new ScreenPoint(point.getX() + width, point.getY()), color);
        lineDrawer.drawLine(point, new ScreenPoint(point.getX(), point.getY() + height), color);
        lineDrawer.drawLine(new ScreenPoint(point.getX() + width, point.getY() + height),
                new ScreenPoint(point.getX() + width, point.getY()), color);
        lineDrawer.drawLine(new ScreenPoint(point.getX() + width, point.getY() + height),
                new ScreenPoint(point.getX(), point.getY() + height), color);
    }

    @Override
    public void fill(SimpleRectangle simpleRectangle, Color color, ScreenConverter screenConverter, PixelDrawer pixelDrawer) {

    }
}
