package com.company.utils.markers;

import com.company.MainPanel;
import com.company.points.RealPoint;
import com.company.utils.Idrawers.LineDrawer;
import com.company.utils.Idrawers.MarkerDrawer;
import com.company.utils.ScreenConverter;

import java.awt.*;

public class ScaleMarker implements MarkerDrawer {
    private final int width = 11;
    private final Color color = new Color(0x299735);

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
    public void draw(LineDrawer ld) {
        ScreenConverter scrConv = MainPanel.getScrConv();
        double x = point.getX();
        double y = point.getY();

        RealPoint buff = new RealPoint(x - scrConv.value2r(width / 2), y);

        ld.drawLine(scrConv.r2s(buff), scrConv.r2s(new RealPoint(buff.getX() + scrConv.value2r(width), buff.getY())), color);
        buff.setX(x);
        buff.setY(y - scrConv.value2r(width / 2));
        ld.drawLine(scrConv.r2s(buff), scrConv.r2s(new RealPoint(buff.getX(), buff.getY() + scrConv.value2r(width))), color);
    }

    @Override
    public boolean hitMarker(RealPoint checkPoint) {
        ScreenConverter scrConv = MainPanel.getScrConv();
        double w = scrConv.value2r(this.width);
        RealPoint u_l = new RealPoint(point.getX() - w, point.getY() + w);
        RealPoint d_r = new RealPoint(point.getX() + w, point.getY() - w);

        return checkPoint.getX() <= d_r.getX() && checkPoint.getX() >= u_l.getX() &&
                checkPoint.getY() <= u_l.getY() && checkPoint.getY() >= d_r.getY();
    }
}
