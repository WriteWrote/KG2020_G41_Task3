package com.company.utils;

import com.company.linedrawers.BresenhamLineDrawer;

import java.awt.*;

public interface Marker {
    void draw(LineDrawer lineDrawer, ScreenConverter screenConverter);
}
