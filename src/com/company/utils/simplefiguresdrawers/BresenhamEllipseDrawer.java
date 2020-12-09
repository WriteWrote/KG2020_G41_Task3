package com.company.utils.simplefiguresdrawers;

import com.company.figures.Ellipse;
import com.company.points.ScreenPoint;
import com.company.utils.Idrawers.EllipseDrawer;
import com.company.utils.Idrawers.PixelDrawer;
import com.company.utils.ScreenConverter;

import java.awt.*;

public class BresenhamEllipseDrawer implements EllipseDrawer {
    @Override
    public void draw(Ellipse ellipse, Color color, ScreenConverter screenConverter, PixelDrawer pixelDrawer) {
        ScreenPoint point = screenConverter.r2s(ellipse.getPoint());
        int x = point.getX();
        int y = point.getY();
        int _x = 0;
        int _y = screenConverter.value2s(ellipse.getB());
        // check this out later, this might cause troubles
        int a_sqr = screenConverter.value2s(ellipse.getA())*screenConverter.value2s(ellipse.getA()); // a^2, a - большая полуось
        int b_sqr = screenConverter.value2s(ellipse.getB())*screenConverter.value2s(ellipse.getB()); // b^2, b - малая полуось
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
    }

    @Override
    public void fill() {

    }
}
