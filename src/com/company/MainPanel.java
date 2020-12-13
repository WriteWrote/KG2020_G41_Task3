package com.company;


import com.company.arcdrawers.SegmentDrawer;
import com.company.utils.Idrawers.*;
import com.company.utils.simplefiguresdrawers.BresenhamCircleDrawer;
import com.company.figures.*;

import com.company.linedrawers.BresenhamLineDrawer;
import com.company.pixeldrawers.BufferedImagePixelDrawer;
import com.company.points.RealPoint;
import com.company.points.ScreenPoint;
import com.company.utils.ScreenConverter;
import com.company.utils.simplefiguresdrawers.BresenhamEllipseDrawer;
import com.company.utils.simplefiguresdrawers.BresenhamRectangleDrawer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class MainPanel extends JPanel implements MouseListener, MouseMotionListener, MouseWheelListener, KeyListener {
    private ArrayList<Line> lines = new ArrayList<>();
    private static ScreenConverter scrConv = new ScreenConverter(-4, 4, 8, 8, 400, 400);

    private Line yAxis = new Line(0, -2, 0, 2);
    private Line xAxis = new Line(-2, 0, 2, 0);

    private String activeItem = FigureType.Segment.toString();

    private ScreenPoint prevPoint;
    private Line currentLine;

    private ArrayList<Circle> circles = new ArrayList<>();
    private ArrayList<Segment> segments = new ArrayList<>();
    private ArrayList<Ellipse> ellipses = new ArrayList<>();
    private ArrayList<SimpleRectangle> simpleRectangles = new ArrayList<>();

    private Circle currentCircle;
    private Segment currentSegment;
    private SimpleRectangle currentRectangle;
    private Ellipse currentEllipse;
    private boolean scalingNow = false, startedScaling = false;
    private boolean startAngleChanging = false;
    private boolean endAngleChanging = false;

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

    private ScreenPoint prevDrawPoint;

    @Override
    public void mouseDragged(MouseEvent e) {
        ScreenPoint currentPoint = new ScreenPoint(e.getX(), e.getY());
        if (prevPoint != null) {
            ScreenPoint delta = new ScreenPoint(currentPoint.getX() - prevPoint.getX(), currentPoint.getY() - prevPoint.getY());
            RealPoint deltaReal = scrConv.s2r(delta);
            RealPoint zeroReal = scrConv.s2r(new ScreenPoint(0, 0));
            RealPoint vector = new RealPoint(deltaReal.getX() - zeroReal.getX(), deltaReal.getY() - zeroReal.getY());
            scrConv.setCornerX(scrConv.getCornerX() - vector.getX());
            scrConv.setCornerY(scrConv.getCornerY() - vector.getY());
            prevPoint = currentPoint;
        }

        int d_x = currentPoint.getX() - prevDrawPoint.getX();
        int d_y = currentPoint.getY() - prevDrawPoint.getY();

        if (activeItem.equals(FigureType.Line.toString())) {
            if (currentLine != null) {
                currentLine.setP2(scrConv.s2r(currentPoint));
            }
        }
        if (activeItem.equals(FigureType.Circle.toString())) {
            if (currentCircle != null) {
                currentCircle.moveMarkers(currentCircle.getPoint(), scrConv.s2r(new ScreenPoint(e.getX(), e.getY())));
                currentCircle.setPoint(scrConv.s2r(new ScreenPoint(e.getX(), e.getY())));
            }
        }
        if (activeItem.equals(FigureType.Segment.toString())) {
            if (currentSegment != null) {
                if (!scalingNow && !startAngleChanging && !endAngleChanging) {
                    ScreenPoint buff = scrConv.r2s(currentSegment.getPoint());
                    currentSegment.moveMarkers(currentSegment.getPoint(),
                            scrConv.s2r(new ScreenPoint(buff.getX() + d_x, buff.getY() + d_y)));
                    currentSegment.setPoint(scrConv.s2r(new ScreenPoint(buff.getX() + d_x, buff.getY() + d_y)));
                    prevDrawPoint = currentPoint;
                }
                if (startedScaling) {
                    currentSegment.setRadius(currentSegment.getRadius() + scrConv.value2r(d_x));
                    scalingNow = false;
                }
                if (endAngleChanging) {
                    if (d_x > 0 && d_y > 0)
                        currentSegment.setDeltaAngle(currentSegment.getDeltaAngle() + Math.atan((double) d_y / (double) d_x) / 40);
                    if (d_x < 0 && d_y < 0)
                        currentSegment.setDeltaAngle(currentSegment.getDeltaAngle() - Math.atan((double) d_y / (double) d_x) / 40);
                    currentSegment.moveMarkers();
                }
                if (startAngleChanging) {
                    if (d_x > 0 && d_y > 0) {
                        currentSegment.setStartAngle(currentSegment.getStartAngle() - Math.atan((double) d_y / (double) d_x) / 40);
                        currentSegment.setDeltaAngle(currentSegment.getDeltaAngle() + Math.atan((double) d_y / (double) d_x) / 40);
                    }
                    if (d_x < 0 && d_y < 0) {
                        currentSegment.setStartAngle(currentSegment.getStartAngle() + Math.atan((double) d_y / (double) d_x) / 40);
                        currentSegment.setDeltaAngle(currentSegment.getDeltaAngle() - Math.atan((double) d_y / (double) d_x) / 40);
                    }
                    currentSegment.moveMarkers();
                }
            }
        }
        if (activeItem.equals(FigureType.Rectangle.toString())) {
            if (currentRectangle != null) {
                if (!scalingNow) {
                    ScreenPoint buff = scrConv.r2s(currentRectangle.getPoint());
                    currentRectangle.moveMarkers(currentRectangle.getPoint(),
                            scrConv.s2r(new ScreenPoint(buff.getX() + d_x, buff.getY() + d_y)));
                    // this might be the cause of +30;+30 bug!
                    currentRectangle.setPoint(scrConv.s2r(new ScreenPoint(buff.getX() + d_x, buff.getY() + d_y)));
                    prevDrawPoint = currentPoint;// new ScreenPoint(e.getX(), e.getY());
                }
                if (startedScaling) {
                    currentRectangle.setHeight(currentRectangle.getHeight() + scrConv.value2r(d_y));
                    currentRectangle.setWidth(currentRectangle.getWidth() + scrConv.value2r(d_x));
                    scalingNow = false;
                }
            }
        }
        if (activeItem.equals(FigureType.Ellipse.toString())) {
            currentEllipse.moveMarkers(currentEllipse.getPoint(), scrConv.s2r(new ScreenPoint(e.getX(), e.getY())));
            currentEllipse.setPoint(scrConv.s2r(new ScreenPoint(e.getX(), e.getY())));
        }
        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON3) {
            prevPoint = new ScreenPoint(e.getX(), e.getY());
        } else if (e.getButton() == MouseEvent.BUTTON1) {
            ScreenPoint currentPoint = new ScreenPoint(e.getX(), e.getY());
            prevDrawPoint = currentPoint;

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
            }
            if (activeItem.equals(FigureType.Line.toString())) {
                currentLine = new Line(scrConv.s2r(new ScreenPoint(e.getX(), e.getY())), scrConv.s2r(new ScreenPoint(e.getX(), e.getY())));
            }
            if (activeItem.equals(FigureType.Segment.toString())) {
                for (Segment f : segments) {
                    if (f.hitCursor(scrConv.s2r(currentPoint))) {
                        currentSegment = f;
                        currentSegment.activate(true);
                        //prevDrawPoint = new ScreenPoint(e.getX(), e.getY());
                    } else {
                        f.activate(false);
                    }
                }
                if (currentSegment != null) {
                    if (currentSegment.hitScaleMarkers(scrConv.s2r(currentPoint))) {
                        scalingNow = true;
                        startedScaling = true;
                        //prevDrawPoint = currentPoint;
                    }
                    if (currentSegment.hitStartAngleMarker(scrConv.s2r(currentPoint))) {
                        startAngleChanging = true;
                        //prevDrawPoint = currentPoint;
                    }
                    if (currentSegment.hitEndAngleMarker(scrConv.s2r(currentPoint))) {
                        endAngleChanging = true;
                        //prevDrawPoint = currentPoint;
                    }
                }
                if (!segments.contains(currentSegment) || currentSegment == null) {
                    currentSegment = new Segment(scrConv.s2r(currentPoint), Math.PI / 2, 4 * Math.PI / 3, scrConv.value2r(70));
                    //prevDrawPoint = new ScreenPoint(currentPoint.getX(), currentPoint.getY());
                }
            }
            if (activeItem.equals(FigureType.Ellipse.toString())) {
                for (Ellipse el : ellipses) {
                    if (el.hitCursor(scrConv.s2r(currentPoint))) {
                        currentEllipse = el;
                        currentEllipse.activate(true);
                    } else {
                        el.activate(false);
                    }
                }
                if (!ellipses.contains(currentEllipse) || currentEllipse == null) {
                    currentEllipse = new Ellipse(scrConv.s2r(currentPoint), scrConv.value2r(100), scrConv.value2r(60));
                }
            }
            if (activeItem.equals(FigureType.Rectangle.toString())) {
                for (SimpleRectangle r : simpleRectangles) {
                    if (r.hitCursor(scrConv.s2r(currentPoint))) {
                        currentRectangle = r;
                        currentRectangle.activate(true);
                        //prevDrawPoint = new ScreenPoint(e.getX(), e.getY());
                    } else {
                        r.activate(false);
                    }
                }
                if (currentRectangle != null && currentRectangle.hitMarkers(scrConv.s2r(currentPoint))) {
                    scalingNow = true;
                    startedScaling = true;
                    //prevDrawPoint = new ScreenPoint(e.getX(), e.getY());
                }
                if (!simpleRectangles.contains(currentRectangle) || currentRectangle == null) {
                    currentRectangle = new SimpleRectangle(scrConv.s2r(new ScreenPoint(currentPoint.getX(), currentPoint.getY())), scrConv.value2r(70), scrConv.value2r(130));
                    //prevDrawPoint = new ScreenPoint(currentPoint.getX(), currentPoint.getY());

                }
            }
        }
    }

    public static ScreenConverter getScrConv() {
        return scrConv;
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
            startedScaling = false;
            startAngleChanging = false;
            endAngleChanging = false;
        }
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
        BresenhamRectangleDrawer rectangleDrawer = new BresenhamRectangleDrawer();
        BresenhamEllipseDrawer ellipseDrawer = new BresenhamEllipseDrawer();

        drawCoordinates(ld, xAxis, yAxis, (int) scrConv.getRealW(), (int) scrConv.getRealH(), bi_g);

        drawAll(pixelDrawer, ld, circleDrawer, arcDrawer, rectangleDrawer, ellipseDrawer);

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

    private void drawAll(PixelDrawer pixelDrawer, LineDrawer ld, CircleDrawer circleDrawer,
                         ArcDrawer arcDrawer, RectangleDrawer rectangleDrawer, EllipseDrawer ellipseDrawer) {
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
            currentCircle.drawMarkers(ld);
        }
        for (Segment c :
                segments) {
            arcDrawer.draw(c, Color.BLACK, scrConv, pixelDrawer);
        }
        if (currentSegment != null) {
            arcDrawer.draw(currentSegment, new Color(0x73C8EC), scrConv, pixelDrawer);
            currentSegment.drawMarkers(ld);
        }
        for (SimpleRectangle r :
                simpleRectangles) {
            rectangleDrawer.draw(r, Color.BLACK, scrConv, pixelDrawer);
        }
        if (currentRectangle != null) {
            rectangleDrawer.draw(currentRectangle, new Color(0x73C8EC), scrConv, pixelDrawer);
            currentRectangle.drawMarkers(ld);
        }
        for (Ellipse el :
                ellipses) {
            ellipseDrawer.draw(el, Color.BLACK, scrConv, pixelDrawer);
        }
        if (currentEllipse != null) {
            ellipseDrawer.draw(currentEllipse, new Color(0x73C8EC), scrConv, pixelDrawer);
            currentEllipse.drawMarkers(ld);
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
                if (currentRectangle != null)
                    simpleRectangles.add(currentRectangle);
                if (currentEllipse != null)
                    ellipses.add(currentEllipse);
                currentCircle = null;
                currentSegment = null;
                currentRectangle = null;
                currentEllipse = null;
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
            currentRectangle = null;
            currentEllipse = null;
            repaint();
        }
        if (e.getKeyCode() == KeyEvent.VK_D) {
            if (currentCircle != null) {
                circles.remove(currentCircle);
                currentCircle = null;
            }
            if (currentSegment != null) {
                segments.remove(currentSegment);
                currentSegment = null;
            }
            if (currentEllipse != null) {
                ellipses.remove(currentEllipse);
                currentEllipse = null;
            }
            if (currentRectangle != null) {
                simpleRectangles.remove(currentRectangle);
                currentRectangle = null;
            }

            repaint();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
