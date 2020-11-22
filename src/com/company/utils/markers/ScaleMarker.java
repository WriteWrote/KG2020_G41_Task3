package com.company.utils.markers;

import com.company.linedrawers.BresenhamLineDrawer;
import com.company.points.RealPoint;
import com.company.points.ScreenPoint;
import com.company.utils.LineDrawer;
import com.company.utils.Marker;
import com.company.utils.ScreenConverter;

import java.awt.*;

public class ScaleMarker implements Marker {
    private final int width = 11;
    private final Color color = Color.BLACK;

    private RealPoint point;

    public ScaleMarker(RealPoint point) {
        this.point = point;
    }

    public double getX() {
        return point.getX();
    }

    public double getY() {
        return point.getY();
    }

    public void setX(double x) {
        //this.point = new ScreenPoint(x, point.getY());
        this.point.setX(x);
    }

    public void setY(double y) {
        //this.point = new ScreenPoint(point.getX(), y);
        this.point.setY(y);
    }

    @Override
    public void draw(LineDrawer ld, ScreenConverter scrConv) {
        double x = point.getX();
        double y = point.getY();

        RealPoint buff = new RealPoint(x - scrConv.value2r(width / 2), y);

        ld.drawLine(scrConv.r2s(buff), scrConv.r2s(new RealPoint(buff.getX()+scrConv.value2r(width), buff.getY())), new Color(0x299735));
        buff.setX(x);
        buff.setY(y - scrConv.value2r(width / 2));
        ld.drawLine(scrConv.r2s(buff), scrConv.r2s(new RealPoint(buff.getX(), buff.getY()+scrConv.value2r(width))), new Color(0x299735));
    }
}
