package com.company.utils.Idrawers;

import com.company.figures.Circle;
import com.company.utils.ScreenConverter;

import java.awt.*;

public interface CircleDrawer {
    void draw(Circle circle, Color color, ScreenConverter screenConverter, PixelDrawer pixelDrawer);

    void fill();
}
