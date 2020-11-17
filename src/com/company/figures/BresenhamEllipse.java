package com.company.figures;

import com.company.points.ScreenPoint;
import com.company.utils.Figure;
import com.company.utils.PixelDrawer;

import java.awt.*;

public class BresenhamEllipse implements Figure {
    PixelDrawer pixelDrawer;
    private int x, y, a, b;
    private Color color;

    public BresenhamEllipse(PixelDrawer pixelDrawer, int x, int y, int a, int b, Color color) {
        this.pixelDrawer = pixelDrawer;
        this.x = x;
        this.y = y;
        this.a = a;
        this.b = b;
        this.color = color;
    }

    @Override
    public void draw() {
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
    }

    @Override
    public void moveMarkers(ScreenPoint start, ScreenPoint end) {

    }

    @Override
    public boolean hitCursor(ScreenPoint screenPoint) {
        return false;
    }

    @Override
    public void fill() {

    }
    public void setParams(int x, int y, int a, int b, Color color){
        this.x = x;
        this.y = y;
        this.a = a;
        this.b = b;
        this.color = color;
    }
}
