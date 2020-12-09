package com.company.pixeldrawers;

import com.company.utils.Idrawers.PixelDrawer;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BufferedImagePixelDrawer implements PixelDrawer {
    private BufferedImage bi;

    public BufferedImagePixelDrawer(BufferedImage bi) {
        this.bi = bi;
    }

    @Override
    public void setPixel(int x, int y, Color color) {
        if (x >= 0 && y >= 0 && x < bi.getWidth() & y < bi.getHeight()) {
            bi.setRGB(x, y, color.getRGB());
        }
    }
}
