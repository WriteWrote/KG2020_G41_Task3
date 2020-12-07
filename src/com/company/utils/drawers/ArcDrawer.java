package com.company.utils.drawers;

import com.company.figures.Segment;
import com.company.utils.ScreenConverter;

import java.awt.*;

public interface ArcDrawer {
    void draw(Segment segment, Color color, ScreenConverter screenConverter, PixelDrawer pixelDrawer);
    void fill();
}
