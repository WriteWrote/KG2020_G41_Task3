package com.company.linedrawers;

import com.company.utils.Idrawers.LineDrawer;
import com.company.utils.Idrawers.PixelDrawer;
import com.company.points.ScreenPoint;

import java.awt.*;

public class BresenhamLineDrawer implements LineDrawer {
    private PixelDrawer pixelDrawer;

    public BresenhamLineDrawer(PixelDrawer pixelDrawer) {
        this.pixelDrawer = pixelDrawer;
    }

    @Override
    public void drawLine(ScreenPoint p1, ScreenPoint p2, Color color) {
        int x1 = p1.getX(); int x2 = p2.getX();
        int y1 = p1.getY(); int y2 = p2.getY();

        pixelDrawer.setPixel(x1, y1, color);
        pixelDrawer.setPixel(x2, y2, color);

        boolean isHorizontal = Math.abs(x2 - x1) > Math.abs(y2 - y1);

        if (isHorizontal) {        // horizontal
            if (x1 > x2) {      //isInverted
                int temp = x1;
                x1 = x2;
                x2 = temp;
                temp = y1;
                y1 = y2;
                y2 = temp;
            }
            int dx = x2 - x1;
            int dy = y2 - y1;
            int error = dy > 0 ? 1 : -1;
            int d = 2 * dy * error - dx;

            for (int x = x1, y = y1; x <= x2; x++) {
                pixelDrawer.setPixel(x, y, color);
                if (d > 0) {
                    d += 2 * dy * error - 2 * dx;
                    y += error;
                } else {
                    d += 2 * dy * error;
                }
            }
        } else {        // vertical
            if (y1 > y2) {     // isInverted
                int temp = y1;
                y1 = y2;
                y2 = temp;
                temp = x1;
                x1 = x2;
                x2 = temp;
            }
            int dx = x2 - x1;
            int dy = y2 - y1;
            int error = dx > 0 ? 1 : -1;
            int d = 2 * dx * error - dy;

            for (int x = x1, y = y1; y <= y2; y++) {
                pixelDrawer.setPixel(x, y, color);
                if (d > 0) {
                    d += 2 * dx * error - 2 * dy;
                    x += error;
                } else {
                    d += 2 * dx * error;
                }
            }
        }
    }
}
