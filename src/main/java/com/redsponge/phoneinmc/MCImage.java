package com.redsponge.phoneinmc;

import org.bukkit.Material;
import org.bukkit.block.Block;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferInt;

public class MCImage {

    private Material[][] blocks;
    private static Object[][] materials = {
            {Material.BLACK_WOOL, new Color(0, 0, 0)},
            {Material.YELLOW_WOOL, Color.YELLOW},
            {Material.BLUE_WOOL, Color.BLUE},
            {Material.RED_WOOL, Color.RED},
            {Material.LIGHT_GRAY_WOOL, Color.LIGHT_GRAY},
            {Material.GRAY_WOOL, Color.DARK_GRAY},
            {Material.OAK_WOOD, new Color(211, 176, 130)},
            {Material.LIME_WOOL, Color.GREEN},
            {Material.GREEN_WOOL, new Color(0, 148, 0)},
            {Material.WHITE_WOOL, Color.WHITE},
            {Material.PURPLE_WOOL, new Color(117, 12, 180)}
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


        byte bestMatchIndex = -1;
        int bestDiff = Integer.MAX_VALUE;
        for (byte i = 0; i < materials.length; i = (byte)(i + 1))
        {
            Color c = (Color)materials[i][1];
            int diff = Math.abs(c.getRed() - r) * Math.abs(c.getRed() - r) +
                    Math.abs(c.getGreen() - g) * Math.abs(c.getGreen() - g) +
                    Math.abs(c.getBlue() - b) * Math.abs(c.getBlue() - b);
            if (diff < bestDiff)
            {
                bestMatchIndex = i;
                bestDiff = diff;
            }
        }
        return (Material) materials[bestMatchIndex][0];
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
