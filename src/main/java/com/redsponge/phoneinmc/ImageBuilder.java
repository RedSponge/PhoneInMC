package com.redsponge.phoneinmc;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class ImageBuilder {

    public void updateImageSynced(World world, MCImage image) {
        Bukkit.getScheduler().runTask(PhoneInMC.INSTANCE, () -> updateImage(world, image));
    }

    public void updateImage(World world, MCImage image) {
        for(int y = 0; y < Constants.HEIGHT; y++) {
            for(int x = 0; x < Constants.WIDTH; x++) {
                int blockX = -Constants.WIDTH/2+x;
                int blockZ = -Constants.HEIGHT/2+y;
                int blockY = 120;

                Location l = new Location(world, blockX, blockY, blockZ);
                l.getBlock().setType(image.getBlocks()[y][x]);
            }
        }
    }

}
