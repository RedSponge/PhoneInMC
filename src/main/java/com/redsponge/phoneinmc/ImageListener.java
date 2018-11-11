package com.redsponge.phoneinmc;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ImageListener {

    private ServerSocket socket;
    private Thread serverThread;
    public ImageListener(int port) {
        serverThread = new Thread(() -> listen(port));
        serverThread.start();
    }

    private void listen(int port) {
        try {
            socket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        PhoneInMC.INSTANCE.getLogger().info("Started server on port " + port);
        PhoneInMC.INSTANCE.getLogger().info("Listening!");
        while(true) {
            try {
                Socket in = socket.accept();
                PhoneInMC.INSTANCE.getLogger().info("Accepted Socket!");
                BufferedImage img = ImageIO.read(in.getInputStream());
                if(img == null) {
                    System.out.println("IMAGE WAS NULL!");
                } else {
                    processImage(img);
                    PhoneInMC.INSTANCE.getLogger().info("GOT IMAGE!");
                }
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    private void processImage(BufferedImage img) {
        PhoneInMC.INSTANCE.getLogger().info("Processing Image");


        int w = img.getWidth();
        int h = img.getHeight();
        BufferedImage after = resize(img, Constants.WIDTH, Constants.HEIGHT);

        PhoneInMC.INSTANCE.builder.updateImageSynced(PhoneInMC.INSTANCE.getServer().getWorlds().get(0), new MCImage(after));
        PhoneInMC.INSTANCE.getLogger().info("Done Processing!");
    }

    public void close() {
        try {
            socket.close();
            serverThread.join();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        PhoneInMC.INSTANCE.getLogger().info("Closed Server!");
    }

    public BufferedImage resize(BufferedImage img, int newW, int newH) {
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return dimg;
    }
}
