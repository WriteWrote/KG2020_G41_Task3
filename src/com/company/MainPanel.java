package com.company;


import com.company.arcdrawers.SegmentDrawer;
import com.company.circledrawers.BresenhamCircleDrawer;
import com.company.figures.Segment;
import com.company.figures.Circle;

import com.company.figures.FigureType;
import com.company.figures.Line;
import com.company.linedrawers.BresenhamLineDrawer;
import com.company.pixeldrawers.BufferedImagePixelDrawer;
import com.company.points.RealPoint;
import com.company.points.ScreenPoint;
import com.company.utils.drawers.PixelDrawer;
import com.company.utils.ScreenConverter;
import com.company.utils.drawers.LineDrawer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class MainPanel extends JPanel implements MouseListener, MouseMotionListener, MouseWheelListener, KeyListener {
    private ArrayList<Line> lines = new ArrayList<>();
    private ScreenConverter scrConv = new ScreenConverter(-4, 4, 8, 8, 400, 400);

    private Line yAxis = new Line(0, -2, 0, 2);
    private Line xAxis = new Line(-2, 0, 2, 0);

    private String activeItem = FigureType.Segment.toString();

    private ScreenPoint prevPoint;
    private Line currentLine;

    private ArrayList<Circle> circles = new ArrayList<>();
    private ArrayList<Segment> segments = new ArrayList<>();
    private Circle currentCircle;
    private Segment currentSegment;

    public void setActiveItem(String activeItem) {
        this.activeItem = activeItem;
    }

    public String getActiveItem() {
        return activeItem;
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        int clicks = e.getWheelRotation();
        double scale = 1;
        double coef = clicks < 0 ? 0.9 : 1.1;
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

        if (activeItem.equals("Line")) {
            if (currentLine != null) {
                currentLine.setP2(scrConv.s2r(current));
            }
        }

        if (activeItem.equals("Circle")) {
            if (currentCircle != null) {
                currentCircle.moveMarkers(currentCircle.getPoint(), scrConv.s2r(new ScreenPoint(e.getX(), e.getY())));
                currentCircle.setPoint(scrConv.s2r(new ScreenPoint(e.getX(), e.getY())));
            }
        }
        if (activeItem.equals("Segment")) {
            if (currentSegment != null) {
                currentSegment.moveMarkers(currentSegment.getPoint(), scrConv.s2r(new ScreenPoint(e.getX(), e.getY())));
                currentSegment.setPoint(scrConv.s2r(new ScreenPoint(e.getX(), e.getY())));
            }
        }
        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON3) {
            prevPoint = new ScreenPoint(e.getX(), e.getY());
        } else if (e.getButton() == MouseEvent.BUTTON1) {
            ScreenPoint currentPoint = new ScreenPoint(e.getX(), e.getY());
            if (activeItem.equals(FigureType.Circle.toString())) {
                for (Circle f : circles) {
                    if (f.hitCursor(scrConv.s2r(currentPoint))) {
                        currentCircle = f;
                        currentCircle.activate(true);
                    } else {
                        f.activate(false);
                    }
                }
                if (!circles.contains(currentCircle) || currentCircle == null) {
                    currentCircle = new Circle(scrConv.s2r(currentPoint), 1);
                }
            } else if (activeItem.equals(FigureType.Line.toString())) {
                if (e.getButton() == MouseEvent.BUTTON3)
                    prevPoint = new ScreenPoint(e.getX(), e.getY());
                else if (e.getButton() == MouseEvent.BUTTON1) {
                    currentLine = new Line(scrConv.s2r(new ScreenPoint(e.getX(), e.getY())), scrConv.s2r(new ScreenPoint(e.getX(), e.getY())));
                }
            } else if (activeItem.equals(FigureType.Segment.toString())) {
                for (Segment f : segments) {
                    if (f.hitCursor(scrConv.s2r(currentPoint))) {
                        currentSegment = f;
                        currentSegment.activate(true);
                    } else {
                        f.activate(false);
                    }
                }
                if (!segments.contains(currentSegment) || currentSegment == null) {
                    currentSegment = new Segment(scrConv.s2r(currentPoint), Math.PI / 2, 4 * Math.PI / 3, scrConv.value2r(100));
                }
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON3)
            prevPoint = null;
        else if (e.getButton() == MouseEvent.BUTTON1) {
            if (currentLine != null) {
                lines.add(currentLine);
                currentLine = null;
            }
        }
        //stretching = false;
        repaint();
    }

    public MainPanel() {
        this.addMouseMotionListener(this);
        this.addMouseListener(this);
        this.addMouseWheelListener(this);
        this.setFocusable(true);
        this.addKeyListener(this);

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

        PixelDrawer pixelDrawer = new BufferedImagePixelDrawer(bi);
        LineDrawer ld = new BresenhamLineDrawer(pixelDrawer);
        BresenhamCircleDrawer circleDrawer = new BresenhamCircleDrawer();
        SegmentDrawer arcDrawer = new SegmentDrawer();

        drawCoordinates(ld, xAxis, yAxis, (int) scrConv.getRealW(), (int) scrConv.getRealH(), bi_g);

        for (Line l : lines) {
            drawLine(ld, l, Color.BLACK);
        }
        if (currentLine != null)
            drawLine(ld, currentLine, new Color(0x73C8EC));

        for (Circle c :
                circles) {
            circleDrawer.draw(c, Color.BLACK, scrConv, pixelDrawer);
        }
        if (currentCircle != null) {
            circleDrawer.draw(currentCircle, new Color(0x73C8EC), scrConv, pixelDrawer);
            currentCircle.drawMarkers(ld, scrConv);
        }
        bi_g.setColor(Color.RED);
        for (Segment c :
                segments) {
            arcDrawer.draw(c, Color.BLACK, scrConv, pixelDrawer);
        }
        if (currentSegment != null) {
            arcDrawer.draw(currentSegment, new Color(0x73C8EC), scrConv, pixelDrawer);
            currentSegment.drawMarkers(ld, scrConv);
        }

        bi_g.dispose();
        g.drawImage(bi, 0, 0, null);
    }

    private void drawLine(LineDrawer ld, Line l, Color color) {
        ld.drawLine(scrConv.r2s(l.getP1()), scrConv.r2s(l.getP2()), color);
    }

    private void drawCoordinates(LineDrawer lineDrawer, Line xAxis, Line yAxis, int w, int h, Graphics graphics) {
        Line xL = new Line(new RealPoint(xAxis.getP1().getX() - scrConv.getRealW() / 2, xAxis.getP1().getY()),
                new RealPoint(xAxis.getP2().getX() + scrConv.getRealW() / 2, xAxis.getP2().getY()));
        Line yL = new Line(new RealPoint(yAxis.getP1().getX(), yAxis.getP1().getY() - scrConv.getRealH() / 2),
                new RealPoint(yAxis.getP2().getX(), yAxis.getP2().getY() + scrConv.getRealH() / 2));
        drawLine(lineDrawer, xL, Color.LIGHT_GRAY);
        drawLine(lineDrawer, yL, Color.LIGHT_GRAY);
        for (int i = -w / 2; i < w / 2; i++) {
            RealPoint rP_x1 = new RealPoint(i, xAxis.getP1().getY() - 0.2);
            RealPoint rP_x2 = new RealPoint(i, xAxis.getP1().getY() + 0.2);
            Line xLine = new Line(rP_x1, rP_x2);
            drawLine(lineDrawer, xLine, Color.LIGHT_GRAY);

            ScreenPoint sP = scrConv.r2s(rP_x1);
            graphics.drawString(Integer.toString(i), sP.getX(), sP.getY());
        }
        for (int i = -h / 2; i < h / 2; i++) {
            RealPoint rP_y1 = new RealPoint(yAxis.getP1().getX() - 0.2, i);
            RealPoint rP_y2 = new RealPoint(yAxis.getP1().getX() + 0.2, i);
            Line yLine = new Line(rP_y1, rP_y2);
            drawLine(lineDrawer, yLine, Color.LIGHT_GRAY);
            ScreenPoint sP = scrConv.r2s(rP_y2);
            graphics.drawString(Integer.toString(i), sP.getX(), sP.getY());
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

    @Override
    public void keyTyped(KeyEvent e) {
        if (e.getKeyChar() == 'v') {
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_A) {
            try {
                if (currentCircle != null)
                    circles.add(currentCircle);
                if (currentSegment != null)
                    segments.add(currentSegment);
                currentCircle = null;
                currentSegment = null;
                repaint();
            } catch (Exception exp) {
                JOptionPane.showMessageDialog(null, "You must firstly choose the coordinates by clicking left button of your mouse");
            }
        }
/*        if (e.getKeyCode() == KeyEvent.VK_C) {
            activeItem = "Circle";
            currentLine = null;
            repaint();
        }
            if (e.getKeyCode() == KeyEvent.VK_L) {
            activeItem = "Line";
            currentFigure = null;
            for (Figure f :
                    objects) {
                f.activate(false);
            }
            repaint();
        }
 */
        if (e.getKeyCode() == KeyEvent.VK_Z) {
            for (Circle f :
                    circles) {
                f.activate(false);
            }
            for (Segment s : segments) {
                s.activate(false);
            }
            currentCircle = null;
            currentLine = null;
            currentSegment = null;
            repaint();
        }
        if (e.getKeyCode() == KeyEvent.VK_D) {
            if (currentCircle != null)
                circles.remove(currentCircle);
            currentCircle = null;
            if (currentSegment != null)
                segments.remove(currentSegment);
            currentSegment = null;
            repaint();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
