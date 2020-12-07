package com.company.linedrawers;

import com.company.utils.drawers.LineDrawer;
import com.company.utils.drawers.PixelDrawer;
import com.company.points.ScreenPoint;

import java.awt.*;

public class WuLineDrawer implements LineDrawer {
    private PixelDrawer pixelDrawer;

    public WuLineDrawer(PixelDrawer pixelDrawer) {
        this.pixelDrawer = pixelDrawer;
    }

    @Override
    public void drawLine(ScreenPoint p1, ScreenPoint p2, Color color) {
        int x1 = p1.getX(); int x2 = p2.getX();
        int y1 = p1.getY(); int y2 = p2.getY();

        boolean isVertical = Math.abs(y2 - y1) > Math.abs(x2 - x1);

        if (isVertical) {
            int temp = x1;
            x1 = y1;
            y1 = temp;
            temp = x2;
            x2 = y2;
            y2 = temp;
        }
        if (x1 > x2) {
            int temp = x1;
            x1 = x2;
            x2 = temp;
            temp = y1;
            y1 = y2;
            y2 = temp;
        }

        int dx = x2 - x1;
        int dy = y2 - y1;
        double gr = (double) dy / dx;
        double realY = y1;
        if (isVertical) {
            for (int x = x1; x <= x2; x++) {
                setColouredPixel((int) (realY), x, gradient(realY), color);
                setColouredPixel((int) (realY) + 1, x, 1 - gradient(realY), color);
                realY += gr;
            }
        } else {
            for (int x = x1; x <= x2; x++) {
                setColouredPixel(x, (int) (realY) + 1, 1 - gradient(realY), color);
                setColouredPixel(x, (int) (realY), gradient(realY), color);
                realY += gr;
            }
        }
    }

    private double gradient(double f_x) {
        int pixel_f_x = (int) f_x;
        if (f_x > 0) return f_x - pixel_f_x;
        return f_x - pixel_f_x - 1;
    }

    private void setColouredPixel(int x, int y, double opacity, Color color) {
        pixelDrawer.setPixel(x, y, new Color(color.getRed(), color.getGreen(), color.getBlue(),
                (int) (255 * (1 - opacity))));
    }
}
