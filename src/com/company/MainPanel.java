package com.company;

import com.company.figures.Arc;
import com.company.figures.BresenhamCircle;
import com.company.figures.Line;
import com.company.linedrawers.DDALineDrawer;
import com.company.pixeldrawers.BufferedImagePixelDrawer;
import com.company.points.RealPoint;
import com.company.points.ScreenPoint;
import com.company.utils.ScreenConverter;
import com.company.utils.LineDrawer;
import com.company.utils.PixelDrawer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class MainPanel extends JPanel implements MouseListener, MouseMotionListener, MouseWheelListener {
    private ArrayList<Line> lines = new ArrayList<>();
    private ScreenConverter scrConv = new ScreenConverter(-2, 2, 4, 4, 800, 600);
    /*private Line yAxis = new Line(0, (int) -screenConverter.getRealH() / 2, 0, (int) screenConverter.getRealH() / 2);
    private Line xAxis = new Line(-(int) screenConverter.getRealW() / 2, 0, (int) screenConverter.getRealW() / 2, 0);*/

    private Line yAxis = new Line(0, -2, 0, 2);
    private Line xAxis = new Line(-2, 0, 2, 0);
    private ScreenPoint prevPoint;
    private Line currentLine;

    private List<Arc> arcList = new ArrayList<>();
    private Arc currentArc;

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        int clicks = e.getWheelRotation();
        double scale = 1;
        double coef = clicks > 0 ? 0.9 : 1.1;
        for (int i = 0; i < Math.abs(clicks); i++) {
            scale *= coef;
        }
        scrConv.setRealW(scrConv.getRealW() * scale);
        scrConv.setRealH(scrConv.getRealH() * scale);
        repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        ScreenPoint current = new ScreenPoint(e.getX(), e.getY());
        if (prevPoint != null) {
            ScreenPoint delta = new ScreenPoint(current.getX() - prevPoint.getX(), current.getY() - prevPoint.getY());
            RealPoint deltaReal = scrConv.s2r(delta);
            RealPoint zeroReal = scrConv.s2r(new ScreenPoint(0, 0));
            RealPoint vector = new RealPoint(deltaReal.getX() - zeroReal.getX(), deltaReal.getY() - zeroReal.getY());
            scrConv.setCornerX(scrConv.getCornerX() - vector.getX());
            scrConv.setCornerY(scrConv.getCornerY() - vector.getY());
            prevPoint = current;
        }
        if (currentLine != null) {
            currentLine.setP2(scrConv.s2r(current));
        }
        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON3)
            prevPoint = new ScreenPoint(e.getX(), e.getY());
        else if (e.getButton() == MouseEvent.BUTTON1) {
            currentLine = new Line(scrConv.s2r(new ScreenPoint(e.getX(), e.getY())), scrConv.s2r(new ScreenPoint(e.getX(), e.getY())));
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
        scrConv.setScreenW(getWidth());
        scrConv.setScreenH(getHeight());
        Graphics bi_g = bi.getGraphics();
        bi_g.setColor(Color.WHITE);
        bi_g.fillRect(0, 0, getWidth(), getHeight());
        bi_g.setColor(Color.black);
        bi_g.fillOval(scrConv.r2s(new RealPoint(0.5,0.5)).getX(), scrConv.r2s(new RealPoint(0.5,0.5)).getY(), 150,150);
        bi_g.drawString("MotherFuckers", new ScreenPoint(150,150).getX(), new ScreenPoint(150,150).getY());
        bi_g.dispose();
        PixelDrawer pixelDrawer = new BufferedImagePixelDrawer(bi);
        LineDrawer ld = new DDALineDrawer(pixelDrawer);

        /*drawLine(ld, xAxis, Color.LIGHT_GRAY);
        drawLine(ld, yAxis, Color.LIGHT_GRAY);*/
        drawCoordinats(ld, xAxis, yAxis, (int) scrConv.getRealW(), (int) scrConv.getRealH(), bi_g);
        for (Line l : lines) {
            drawLine(ld, l, Color.BLACK);
        }
        if (currentLine != null)
            drawLine(ld, currentLine, Color.BLACK);

        Arc testArc = new Arc(pixelDrawer, scrConv.r2s(new RealPoint(0.3, 0.5)), 100);
        BresenhamCircle controlCircle = new BresenhamCircle(pixelDrawer,
                scrConv.r2s(new RealPoint(0.3, 0.5)).getX(),scrConv.r2s(new RealPoint(0.3, 0.5)).getY(),
                100, Color.YELLOW);
        controlCircle.draw();
        testArc.drawExpCircleArc(0,100, 100, 100);

        g.drawImage(bi, 0, 0, null);
    }

    private void drawLine(LineDrawer ld, Line l, Color color) {
        ld.drawLine(scrConv.r2s(l.getP1()), scrConv.r2s(l.getP2()), color);
    }

    private void drawCoordinats(LineDrawer lineDrawer, Line xAxis, Line yAxis, int w, int h, Graphics graphics) {
        Line xL = new Line(new RealPoint(xAxis.getP1().getX() - scrConv.getRealW() / 2, xAxis.getP1().getY()),
                new RealPoint(xAxis.getP2().getX() + scrConv.getRealW() / 2, xAxis.getP2().getY()));
        Line yL = new Line(new RealPoint(yAxis.getP1().getX(), yAxis.getP1().getY() - scrConv.getRealH() / 2),
                new RealPoint(yAxis.getP2().getX(), yAxis.getP2().getY() + scrConv.getRealH() / 2));
        //drawLine(lineDrawer, xAxis, Color.LIGHT_GRAY);
        //drawLine(lineDrawer, yAxis, Color.LIGHT_GRAY);
        drawLine(lineDrawer, xL, Color.LIGHT_GRAY);
        drawLine(lineDrawer, yL, Color.LIGHT_GRAY);
        for (int i = -w / 2; i < w / 2; i++) {
            RealPoint rP_x1 = new RealPoint(i, xAxis.getP1().getY() - 0.2);
            RealPoint rP_x2 = new RealPoint(i, xAxis.getP1().getY() + 0.2);
            Line xLine = new Line(rP_x1, rP_x2);
            drawLine(lineDrawer, xLine, Color.LIGHT_GRAY);

            //rP_x1.setY(xAxis.getP1().getY() - 0.4);
            ScreenPoint sP = scrConv.r2s(rP_x1);
            graphics.drawString(Integer.toString(i), sP.getX(), sP.getY());
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
