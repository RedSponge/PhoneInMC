package com.redsponge.phoneinmc;

import org.bukkit.Material;
import org.bukkit.block.Block;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferInt;

public class MCImage {

    private Material[][] blocks;
    private Object[][] colors = {{Color.WHITE, Material.WHITE_WOOL}, {Color.RED, Material.RED_WOOL}, {Color.BLACK, Material.BLACK_WOOL},
            {Color.YELLOW, Material.YELLOW_WOOL}, {Color.GREEN, Material.GREEN_WOOL}, {Color.BLUE, Material.BLUE_WOOL},
            {Color.ORANGE, Material.ORANGE_WOOL}, {Color.LIGHT_GRAY, Material.LIGHT_GRAY_WOOL}, {Color.DARK_GRAY, Material.GRAY_WOOL},
            {new Color(141, 65, 0), Material.BROWN_WOOL}, {new Color(122, 26, 142), Material.PURPLE_WOOL},
            {Color.PINK, Material.PINK_WOOL}
    };
    public MCImage(BufferedImage img) {
        blocks = new Material[Constants.HEIGHT][Constants.WIDTH];

        int[] pixels = ((DataBufferInt)img.getRaster().getDataBuffer()).getData();

        for(int y = 0; y < Constants.HEIGHT; y++) {
            for(int x = 0; x < Constants.WIDTH; x++) {
                blocks[y][x] = getBlockByPixel(pixels[y * Constants.WIDTH + x]);
            }
        }
    }

    private Material getBlockByPixel(int pixel) {
        System.out.println(Integer.toHexString(pixel));
        int a = (pixel >> 24) & 0xff;
        int r = (pixel >> 16) & 0xff;
        int g = (pixel >> 8) & 0xff;
        int b = (pixel) & 0xff;
        System.out.println(r + " " + g + " " + b);
        Color c = new Color(pixel, true);
        int closest = 0;
        double lowestDist = Integer.MAX_VALUE;
        for(int i = 0; i < colors.length; i++) {
            double dist = AdistanceFromB(c, (Color)colors[i][0]);
            if(dist < lowestDist) {
                lowestDist = dist;
                closest = i;
            }
        }
        return (Material)colors[closest][1];
    }

    public double AdistanceFromB(Color a, Color b) {
        int r = a.getRed() - b.getRed();
        int g = a.getGreen() - b.getGreen();
        int bl = a.getBlue() - b.getBlue();
        return r*r+g*g+bl*bl;
    }

    public Material[][] getBlocks() {
        return blocks;
    }
}
