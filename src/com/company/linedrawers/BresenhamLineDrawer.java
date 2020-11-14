package com.company.linedrawers;

import com.company.utils.LineDrawer;
import com.company.utils.PixelDrawer;
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

/*    public void drawBresenhamCircle(int x, int y, int radius, Color color) {
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
*/
    /*public void drawEllipse(int x, int y, int a, int b, Color color) {
        int _x = 0;
        int _y = b;
        int a_sqr = a * a; // a^2, a - большая полуось
        int b_sqr = b * b; // b^2, b - малая полуось
        int delta = 4 * b_sqr * ((_x + 1) * (_x + 1)) +
                a_sqr * ((2 * _y - 1) * (2 * _y - 1)) - 4 * a_sqr * b_sqr; // Функция координат точки (x+1, y-1/2)
        while (a_sqr * (2 * _y - 1) > 2 * b_sqr * (_x + 1)) // Первая часть дуги
        {
            pixelDrawer.setPixel(x + _x, y + _y, color);
            pixelDrawer.setPixel(x + _x, y - _y, color);
            pixelDrawer.setPixel(x - _x, y - _y, color);
            pixelDrawer.setPixel(x - _x, y + _y, color);
            if (delta < 0) // Переход по горизонтали
            {
                ++_x;
                delta += 4 * b_sqr * (2 * _x + 3);
            } else // Переход по диагонали
            {
                ++_x;
                delta = delta - 8 * a_sqr * (_y - 1) + 4 * b_sqr * (2 * _x + 3);
                --_y;
            }
        }
        delta = b_sqr * ((2 * _x + 1) * (2 * _x + 1)) +
                4 * a_sqr * ((_y + 1) * (_y + 1)) - 4 * a_sqr * b_sqr; // Функция координат точки (x+1/2, y-1)
        while (_y + 1 != 0) // Вторая часть дуги, если не выполняется условие первого цикла, значит выполняется a^2(2y - 1) <= 2b^2(x + 1)
        {
            pixelDrawer.setPixel(x + _x, y + _y, color);
            pixelDrawer.setPixel(x + _x, y - _y, color);
            pixelDrawer.setPixel(x - _x, y - _y, color);
            pixelDrawer.setPixel(x - _x, y + _y, color);
            if (delta < 0) // Переход по вертикали
            {
                --_y;
                delta += 4 * a_sqr * (2 * _y + 3);
            } else // Переход по диагонали
            {
                --_y;
                delta = delta - 8 * b_sqr * (_x + 1) + 4 * a_sqr * (2 * _y + 3);
                ++_x;
            }
        }
    }*/
}
