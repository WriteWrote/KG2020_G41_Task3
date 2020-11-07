package com.company;

import com.company.figures.Line;
import com.company.linedrawers.DDALineDrawer;
import com.company.pixeldrawers.BufferedImagePixelDrawer;
import com.company.points.RealPoint;
import com.company.points.ScreenPoint;
import com.company.utils.ScreenConverter;
import com.company.utils.figuresInterfaces.LineDrawer;
import com.company.utils.figuresInterfaces.PixelDrawer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class MainPanel extends JPanel implements MouseListener, MouseMotionListener, MouseWheelListener {
    private ArrayList<Line> lines = new ArrayList<>();
    private ScreenConverter screenConverter = new ScreenConverter(-2, 2, 8, 8, 800, 600);
    /*private Line yAxis = new Line(0, (int) -screenConverter.getRealH() / 2, 0, (int) screenConverter.getRealH() / 2);
    private Line xAxis = new Line(-(int) screenConverter.getRealW() / 2, 0, (int) screenConverter.getRealW() / 2, 0);*/

    private Line yAxis = new Line(0, -4, 0, 4);
    private Line xAxis = new Line(-4, 0, 4, 0);
    private ScreenPoint prevPoint;
    private Line currentLine;

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        int clicks = e.getWheelRotation();
        double scale = 1;
        double coef = clicks > 0 ? 0.9 : 1.1;
        for (int i = 0; i < Math.abs(clicks); i++) {
            scale *= coef;
        }
        screenConverter.setRealW(screenConverter.getRealW() * scale);
        screenConverter.setRealH(screenConverter.getRealH() * scale);
        repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        ScreenPoint current = new ScreenPoint(e.getX(), e.getY());
        if (prevPoint != null) {
            ScreenPoint delta = new ScreenPoint(current.getX() - prevPoint.getX(), current.getY() - prevPoint.getY());
            RealPoint deltaReal = screenConverter.s2r(delta);
            RealPoint zeroReal = screenConverter.s2r(new ScreenPoint(0, 0));
            RealPoint vector = new RealPoint(deltaReal.getX() - zeroReal.getX(), deltaReal.getY() - zeroReal.getY());
            screenConverter.setCornerX(screenConverter.getCornerX() - vector.getX());
            screenConverter.setCornerY(screenConverter.getCornerY() - vector.getY());
            prevPoint = current;
        }
        if (currentLine != null) {
            currentLine.setP2(screenConverter.s2r(current));
        }
        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON3)
            prevPoint = new ScreenPoint(e.getX(), e.getY());
        else if (e.getButton() == MouseEvent.BUTTON1) {
            currentLine = new Line(screenConverter.s2r(new ScreenPoint(e.getX(), e.getY())), screenConverter.s2r(new ScreenPoint(e.getX(), e.getY())));
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON3)
            prevPoint = null;
        else if (e.getButton() == MouseEvent.BUTTON1) {
            lines.add(currentLine);
            currentLine = null;
        }
        repaint();
    }

    public MainPanel() {
        this.addMouseMotionListener(this);
        this.addMouseListener(this);
        this.addMouseWheelListener(this);
    }

    @Override
    public void paint(Graphics g) {
        BufferedImage bi = new BufferedImage(
                getWidth(), getHeight(), BufferedImage.TYPE_3BYTE_BGR
        );
        screenConverter.setScreenW(getWidth());
        screenConverter.setScreenH(getHeight());
        Graphics bi_g = bi.getGraphics();
        bi_g.setColor(Color.WHITE);
        bi_g.fillRect(0, 0, getWidth(), getHeight());
        bi_g.dispose();
        PixelDrawer pd = new BufferedImagePixelDrawer(bi);
        LineDrawer ld = new DDALineDrawer(pd);

        /*drawLine(ld, xAxis, Color.LIGHT_GRAY);
        drawLine(ld, yAxis, Color.LIGHT_GRAY);*/
        drawCoordinats(ld, xAxis, yAxis, (int) screenConverter.getRealW(), (int) screenConverter.getRealH());
        for (Line l : lines) {
            drawLine(ld, l, Color.BLACK);
        }
        if (currentLine != null)
            drawLine(ld, currentLine, Color.BLACK);

        g.drawImage(bi, 0, 0, null);
    }

    private void drawLine(LineDrawer ld, Line l, Color color) {
        ld.drawLine(screenConverter.r2s(l.getP1()), screenConverter.r2s(l.getP2()), color);
    }

    private void drawCoordinats(LineDrawer lineDrawer, Line xAxis, Line yAxis, int w, int h) {
        Line xL = new Line(new RealPoint(xAxis.getP1().getX() - screenConverter.getRealW() / 2, xAxis.getP1().getY()),
                new RealPoint(xAxis.getP2().getX() + screenConverter.getRealW() / 2, xAxis.getP2().getY()));
        Line yL = new Line(new RealPoint(yAxis.getP1().getX(), yAxis.getP1().getY() - screenConverter.getRealH() / 2),
                new RealPoint(yAxis.getP2().getX(), yAxis.getP2().getY() + screenConverter.getRealH() / 2));
        //drawLine(lineDrawer, xAxis, Color.LIGHT_GRAY);
        //drawLine(lineDrawer, yAxis, Color.LIGHT_GRAY);
        drawLine(lineDrawer, xL, Color.LIGHT_GRAY);
        drawLine(lineDrawer, yL, Color.LIGHT_GRAY);
        for (int i = -w / 2; i < w / 2; i++) {
            RealPoint rP_x1 = new RealPoint(i, xAxis.getP1().getY() - 0.2);
            RealPoint rP_x2 = new RealPoint(i, xAxis.getP1().getY() + 0.2);
            Line xLine = new Line(rP_x1, rP_x2);
            drawLine(lineDrawer, xLine, Color.LIGHT_GRAY);
        }
        for (int i = -h / 2; i < h / 2; i++) {
            RealPoint rP_y1 = new RealPoint(yAxis.getP1().getX() - 0.2, i);
            RealPoint rP_y2 = new RealPoint(yAxis.getP1().getX() + 0.2, i);
            Line yLine = new Line(rP_y1, rP_y2);
            drawLine(lineDrawer, yLine, Color.LIGHT_GRAY);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
