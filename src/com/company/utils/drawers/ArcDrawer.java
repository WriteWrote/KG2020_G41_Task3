package com.company.utils.drawers;

import com.company.figures.Arc;
import com.company.utils.ScreenConverter;

import java.awt.*;

public interface ArcDrawer {
    void draw(Arc arc, Color color, ScreenConverter screenConverter, PixelDrawer pixelDrawer);
    void fill();
}
