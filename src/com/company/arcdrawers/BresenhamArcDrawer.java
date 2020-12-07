package com.company.arcdrawers;

import com.company.figures.Arc;
import com.company.linedrawers.BresenhamLineDrawer;
import com.company.points.RealPoint;
import com.company.points.ScreenPoint;
import com.company.utils.ScreenConverter;
import com.company.utils.drawers.ArcDrawer;
import com.company.utils.drawers.LineDrawer;
import com.company.utils.drawers.PixelDrawer;

import java.awt.*;

public class BresenhamArcDrawer implements ArcDrawer {
    @Override
    public void draw(Arc arc, Color color, ScreenConverter screenConverter, PixelDrawer pixelDrawer) {
        ///*
        ScreenPoint startPoint = screenConverter.r2s(arc.getPoint());
        int rH = screenConverter.value2s(arc.getRadius());
        int rV = screenConverter.value2s(arc.getRadius());
        double startAngle = arc.getStartAngle();
        double deltaAngle = arc.getDeltaAngle();

        double delta = Math.PI/360;
        if (deltaAngle >= 0) {
            for (double i = startAngle; i < startAngle + deltaAngle; i += delta) {
                int x = (int) (startPoint.getX() + rV * Math.cos(i));
                int y = (int) (startPoint.getY() + rH * Math.sin(i));
                pixelDrawer.setPixel(x, y, Color.black);
            }
        } else {
            for (double i = startAngle + deltaAngle; i < startAngle; i += delta) {
                int x = (int) (startPoint.getX() + rV * Math.cos(i));
                int y = (int) (startPoint.getY() + rH * Math.sin(i));
                pixelDrawer.setPixel(x, y, Color.black);
            }
        }


        int x1 = (int) (startPoint.getX() + rV * Math.cos(startAngle));
        int y1 = (int) (startPoint.getY() + rH * Math.sin(startAngle));
        int x2 = (int) (startPoint.getX() + rV * Math.cos(startAngle + deltaAngle));
        int y2 = (int) (startPoint.getY() + rH * Math.sin(startAngle + deltaAngle));
        ScreenPoint sp1 = new ScreenPoint(x1, y1);
        ScreenPoint sp2 = new ScreenPoint(x2, y2);
        LineDrawer ld = new BresenhamLineDrawer(pixelDrawer);
        ld.drawLine(sp1, sp2, Color.RED);

        //*/
      /*
      int x = screenConverter.r2s(arc.getPoint()).getX();
        int y = screenConverter.r2s(arc.getPoint()).getY();
        int _x = screenConverter.value2s(arc.getRadius());
        int _y = 0;
        int radius = screenConverter.value2s(arc.getRadius());
        int radiusError = 1 - _x;


        while (_x >= _y) {
            //isAboveLine(new RealPoint(Math.sin(arc.getStartAngle()) * radius, Math.cos(arc.getStartAngle()) * radius),
                    //new RealPoint(-Math.sin(arc.getStartAngle() + arc.getDeltaAngle()) * radius, Math.cos(arc.getStartAngle() + arc.getDeltaAngle())),
                  //  -_x + x + radius, -_y + y + radius, pixelDrawer);
            //isAboveLine(new RealPoint(Math.sin(arc.getStartAngle()) * radius, Math.cos(arc.getStartAngle()) * radius),
              //      new RealPoint(-Math.sin(arc.getStartAngle() + arc.getDeltaAngle()) * radius, Math.cos(arc.getStartAngle() + arc.getDeltaAngle())),
                //    -_y + x + radius, -_x + y + radius, pixelDrawer);
            pixelDrawer.setPixel(-_x + x + radius, -_y + y + radius, Color.BLUE);
            pixelDrawer.setPixel(-_y + x + radius, -_x + y + radius, Color.PINK);
            pixelDrawer.setPixel(_y + x + radius, -_x + y + radius, Color.LIGHT_GRAY);
            pixelDrawer.setPixel(_x + x + radius, -_y + y + radius, Color.ORANGE);
            pixelDrawer.setPixel(_x + x + radius, _y + y + radius, Color.CYAN);
            pixelDrawer.setPixel(_y + x + radius, _x + y + radius, Color.MAGENTA);
            pixelDrawer.setPixel(-_y + x + radius, _x + y + radius, Color.GREEN);
            pixelDrawer.setPixel(-_x + x + radius, _y + y + radius, Color.RED);

            _y++;
            if (radiusError < 0) {
                radiusError += 2 * _y + 1;
            } else {
                _x--;
                radiusError += 2 * (_y - _x + 1);
            }
        }

       */
    }

        /*int x = screenConverter.r2s(arc.getPoint()).getX();
        int y = screenConverter.r2s(arc.getPoint()).getY();
        int _x = screenConverter.value2s(arc.getRadius());
        int _y = 0;
        int radius = screenConverter.value2s(arc.getRadius());
        int radiusError = 1 - _x;


        while (_x >= _y) {
            isAboveLine(new RealPoint(Math.sin(arc.getStartAngle()) * radius, Math.cos(arc.getStartAngle()) * radius),
                    new RealPoint(-Math.sin(arc.getStartAngle() + arc.getDeltaAngle()) * radius, Math.cos(arc.getStartAngle() + arc.getDeltaAngle())),
                    -_x + x + radius, -_y + y + radius, pixelDrawer);
            isAboveLine(new RealPoint(Math.sin(arc.getStartAngle()) * radius, Math.cos(arc.getStartAngle()) * radius),
                    new RealPoint(-Math.sin(arc.getStartAngle() + arc.getDeltaAngle()) * radius, Math.cos(arc.getStartAngle() + arc.getDeltaAngle())),
                    -_y + x + radius, -_x + y + radius, pixelDrawer);
            /*pixelDrawer.setPixel(-_x + x + radius, -_y + y + radius, Color.BLUE);
            pixelDrawer.setPixel(-_y + x + radius, -_x + y + radius, Color.PINK);
            pixelDrawer.setPixel(_y + x + radius, -_x + y + radius, Color.LIGHT_GRAY);
            pixelDrawer.setPixel(_x + x + radius, -_y + y + radius, Color.ORANGE);
            pixelDrawer.setPixel(_x + x + radius, _y + y + radius, Color.CYAN);
            pixelDrawer.setPixel(_y + x + radius, _x + y + radius, Color.MAGENTA);
            pixelDrawer.setPixel(-_y + x + radius, _x + y + radius, Color.GREEN);
            pixelDrawer.setPixel(-_x + x + radius, _y + y + radius, Color.RED);

            _y++;
            if (radiusError < 0) {
                radiusError += 2 * _y + 1;
            } else {
                _x--;
                radiusError += 2 * (_y - _x + 1);
            }
        }*/

    /*    private void setPointI_II(int x, int y, double startAngle, double endAngle, PixelDrawer pixelDrawer, Color color) {
            double atan = Math.atan((double) y / (double) x);
            if (endAngle <= atan && atan <= startAngle) {
                pixelDrawer.setPixel(x, y, color);
            }
        }

        private void setPointIII_IV(int x, int y, double startAngle, double endAngle, PixelDrawer pixelDrawer, Color color) {
            double atan = Math.atan((double) y / (double) x);
            if (Math.toDegrees(startAngle) <= Math.toDegrees(atan) && Math.toDegrees(atan) <= Math.toDegrees(endAngle)) {
                pixelDrawer.setPixel(x, y, color);
            }
        }
    */
        private void isAboveLine (RealPoint point1, RealPoint point2,int x, int y, PixelDrawer pixelDrawer){
            double line = (point1.getY() - point2.getY()) * x +
                    (point2.getX() - point1.getX()) * y +
                    point1.getX() * point2.getY() - point2.getX() * point1.getY();
            if (line >= 0) {
                pixelDrawer.setPixel(x, y, Color.BLACK);
            }
        }

        @Override
        public void fill () {

        }
    }
