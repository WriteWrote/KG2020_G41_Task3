package com.company.linedrawers;

import com.company.utils.Idrawers.LineDrawer;
import com.company.utils.Idrawers.PixelDrawer;
import com.company.points.ScreenPoint;

import java.awt.*;

public class DDALineDrawer implements LineDrawer {
    private PixelDrawer pd;

    public DDALineDrawer(PixelDrawer pd) {
        this.pd = pd;
    }

    @Override
    public void drawLine(ScreenPoint p1, ScreenPoint p2, Color color) {
        int x1 = p1.getX(); int x2 = p2.getX();
        int y1 = p1.getY(); int y2 = p2.getY();

        double dx = x2 - x1;
        double dy = y2 - y1;

        pd.setPixel(x1, y1, color);
        pd.setPixel(x2, y2, color);

        if (Math.abs(dx) > Math.abs(dy)) {
            if (x1 > x2) {
                int tmp = x1;
                x1 = x2;
                x2 = tmp;

                tmp = y1;
                y1 = y2;
                y2 = tmp;
            }
            double k = dy / dx;
            for (int j = x1+1; j < x2; j++) {
                double i = k * (j - x1) + y1;
                pd.setPixel(j, (int) i, color);
            }

        } else {
            if (y1 > y2) {
                int tmp = x1;
                x1 = x2;
                x2 = tmp;

                tmp = y1;
                y1 = y2;
                y2 = tmp;
            }
            double k = dx / dy;
            for (int i = y1+1; i < y2; i++) {
                double j = k * (i - y1) + x1;
                pd.setPixel((int) j, i, color);
            }
        }
    }
}
