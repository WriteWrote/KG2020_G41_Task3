package com.company.utils.Idrawers;

import com.company.figures.SimpleRectangle;
import com.company.utils.ScreenConverter;

import java.awt.*;

public interface RectangleDrawer {
    void draw(SimpleRectangle simpleRectangle, Color color, ScreenConverter screenConverter, PixelDrawer pixelDrawer);
    void fill(SimpleRectangle simpleRectangle, Color color, ScreenConverter screenConverter, PixelDrawer pixelDrawer);
}
