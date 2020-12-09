package com.company.utils.Idrawers;

import com.company.figures.Circle;
import com.company.figures.Ellipse;
import com.company.utils.ScreenConverter;

import java.awt.*;

public interface EllipseDrawer {
    void draw(Ellipse ellipse, Color color, ScreenConverter screenConverter, PixelDrawer pixelDrawer);

    void fill();
}
